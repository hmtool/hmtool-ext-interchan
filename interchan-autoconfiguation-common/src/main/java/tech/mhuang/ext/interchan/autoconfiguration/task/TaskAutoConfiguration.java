package tech.mhuang.ext.interchan.autoconfiguration.task;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import tech.mhuang.ext.interchan.core.task.ISingleDymanicTask;
import tech.mhuang.ext.interchan.core.task.SingleDymanicTask;
import tech.mhuang.ext.interchan.core.task.SingleJobTaskConst;

/**
 * 任务自动注入类
 *
 * @author mhuang
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass(ISingleDymanicTask.class)
@ConditionalOnProperty(prefix = "mhuang.interchan.task", name = "enable", havingValue = "true")
@EnableConfigurationProperties(TaskProperties.class)
public class TaskAutoConfiguration {

    public final TaskProperties properties;

    public TaskAutoConfiguration(TaskProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean(name = SingleJobTaskConst.TASK_POOL_BEAN_NAME)
    public ThreadPoolTaskScheduler singlePoolTask() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(properties.getPoolSize());
        scheduler.setBeanName(properties.getName());
        scheduler.initialize();
        return scheduler;
    }

    @Bean
    @ConditionalOnBean(name = SingleJobTaskConst.TASK_POOL_BEAN_NAME)
    @ConditionalOnMissingBean(name = SingleJobTaskConst.TASK_SINGLE_JOB_NAME)
    public ISingleDymanicTask singleJob(ThreadPoolTaskScheduler singlePoolTask) {
        ISingleDymanicTask singleDymanicTask = new SingleDymanicTask();
        singleDymanicTask.addThreadPoolTaskScheduler(singlePoolTask);
        return singleDymanicTask;
    }
}
