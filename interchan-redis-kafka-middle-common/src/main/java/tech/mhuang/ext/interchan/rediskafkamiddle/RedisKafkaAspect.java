package tech.mhuang.ext.interchan.rediskafkamiddle;


import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import tech.mhuang.core.util.StringUtil;
import tech.mhuang.ext.interchan.redis.commands.RedisExtCommands;
import tech.mhuang.ext.interchan.redis.lock.DistributedLockHandler;
import tech.mhuang.ext.interchan.redis.lock.Lock;
import tech.mhuang.ext.interchan.rediskafkamiddle.annaotion.RedisKafka;
import tech.mhuang.ext.kafka.global.exception.JkafkaException;
import tech.mhuang.ext.kafka.producer.bean.KafkaMsg;

import java.lang.reflect.Method;

/**
 * 拦截Kafka进行处理
 *
 * @author mhuang
 * @since 1.0.0
 */
@Component
@Aspect
@Order(100)
public class RedisKafkaAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());


    private final String CONSUMER = "-consumer";

    private final String LOCK = "-lock-";

    @Autowired
    private RedisExtCommands redisExtCommands;

    @Autowired
    private DistributedLockHandler distributedLockHandler;

    /**
     * redis存放的DB
     */
    @Value("${redisKafkaDataBase:0}")
    private int redisKafkaDataBase;

    @Pointcut("@annotation(tech.mhuang.ext.interchan.rediskafkamiddle.annaotion.RedisKafka)")
    private void kafkaMsgProcess() {
    }

    public static Boolean getMethodByRemark(MethodSignature methodSignature) {
        Method method0 = methodSignature.getMethod();
        RedisKafka rediksKafka = method0.getAnnotation(RedisKafka.class);
        return rediksKafka.notRepeat();
    }

    @Before("kafkaMsgProcess()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        logger.debug("---正在拦截---kafka---");
        Object[] obj = joinPoint.getArgs();
        KafkaMsg kafkaMsg = (KafkaMsg) obj[0];
        Lock lock = new Lock();
        try {
            Boolean notRepeat = getMethodByRemark((MethodSignature) joinPoint.getSignature());
            lock.setName(kafkaMsg.getTopic() + LOCK + kafkaMsg.getOffset());
            lock.setValue(kafkaMsg.getMsg().toString());
            if (distributedLockHandler.tryLock(lock, notRepeat)) {
                String value = redisExtCommands.hget(redisKafkaDataBase, kafkaMsg.getTopic() + CONSUMER, "" + kafkaMsg.getOffset());
                if (StringUtil.isEmpty(value)) {
                    redisExtCommands.hset(redisKafkaDataBase, kafkaMsg.getTopic() + CONSUMER, "" + kafkaMsg.getOffset(), kafkaMsg.getMsg());
                } else {
                    logger.error("数据为：{}", JSON.toJSONString(kafkaMsg));
                    throw new JkafkaException("kafka这条消息已经处理过了！");
                }
            }
        } catch (Exception e) {
            if (!(e instanceof JkafkaException)) {
                redisExtCommands.hdel(redisKafkaDataBase, kafkaMsg.getTopic() + CONSUMER, "" + kafkaMsg.getOffset());
            }
        } finally {
            distributedLockHandler.releaseLock(lock);
        }
    }
}
