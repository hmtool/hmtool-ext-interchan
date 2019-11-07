package tech.mhuang.ext.interchan.core.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;
import tech.mhuang.core.date.DateTimeUtil;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * 单机动态任务
 *
 * @author mhuang
 * @since 1.0.0
 */
@Component
public class SingleDymanicTask implements ISingleDymanicTask {

    @Qualifier(SingleJobTaskConst.TASK_POOL_BEAN_NAME)
    @Autowired(required = false)
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private Map<String, SingleInterjob> interJobMap = new ConcurrentHashMap<>();

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public Boolean startJob(String jobName, Runnable run, String conn) {
        return startJob(jobName, run, conn, true);
    }

    @Override
    public Boolean startJobOnly(String jobName, Runnable run, Date date) {
        logger.debug("正在启动定时任务:{}，时间是:{}", jobName, date);
        Boolean result = false;
        if (!interJobMap.containsKey(jobName)) {
            logger.debug("执行定时任务:{}，表达式时间是:{}", jobName, date);
            ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(run, date);
            interJobMap.put(jobName, new SingleInterjob(future, run, null, null));
            result = true;
        }
        logger.debug("启动定时任务:{}，时间是:{}完成", jobName, date);
        return result;
    }

    @Override
    public Boolean startJobOnly(String jobName, Runnable run, String cron) {
        CronSequenceGenerator generator = new CronSequenceGenerator(cron);
        Date date = generator.next(DateTimeUtil.currentDate());
        return startJobOnly(jobName, run, date);
    }

    @Override
    public Boolean startJobOnly(String jobName, Runnable run, Long peroid) {
        Long currentTime = System.currentTimeMillis();
        currentTime = currentTime + peroid;
        Date date = new Date(currentTime);
        return startJobOnly(jobName, run, date);
    }

    @Override
    public Boolean startJob(String jobName, Runnable run, String conn, Boolean real) {
        logger.debug("正在启动定时任务:{}，表达式时间是:{}", jobName, conn);
        Boolean result = false;
        if (!interJobMap.containsKey(jobName)) {
            logger.debug("执行定时任务:{}，表达式时间是:{}", jobName, conn);
            ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(run, (triggerContext) -> {
                SingleInterjob interJob = interJobMap.get(jobName);
                String nextCron = conn;
                if (interJob != null) {
                    nextCron = interJob.getCron();
                }
                Date nextTime;
                if (triggerContext.lastCompletionTime() == null && !real) {
                    CronSequenceGenerator generator = new CronSequenceGenerator(nextCron);
                    nextTime = generator.next(DateTimeUtil.currentDate());
                } else {
                    nextTime = new CronTrigger(nextCron).nextExecutionTime(triggerContext);
                }

                logger.debug("任务名称:{},下次执行定时任务时间:{}", jobName, nextTime);
                return nextTime;
            });
            interJobMap.put(jobName, new SingleInterjob(future, run, conn, null));
            result = true;
        }
        logger.debug("启动定时任务:{}，表达式时间是:{}完成", jobName, conn);
        return result;
    }

    @Override
    public Boolean startJob(String jobName, Runnable run, Long period, Boolean real) {
        logger.debug("正在启动定时任务:{}，下次执行的秒数是:{}", jobName, period);
        Boolean result = false;
        if (!interJobMap.containsKey(jobName)) {
            logger.debug("执行定时任务:{}，下次执行的秒数是:{}", jobName, period);

            ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(run, (triggerContext) -> {
                SingleInterjob interJob = interJobMap.get(jobName);
                Long nexPeriod = period;
                if (interJob != null) {
                    nexPeriod = interJob.getPeriod();
                }
                Date nextTime;
                if (triggerContext.lastCompletionTime() == null && !real) {
                    nextTime = new Date(System.currentTimeMillis() + nexPeriod);
                } else {
                    nextTime = new PeriodicTrigger(nexPeriod).nextExecutionTime(triggerContext);
                }
                logger.debug("任务名称:{},下次执行定时任务时间:{}", jobName, nextTime);
                return nextTime;
            });

            interJobMap.put(jobName, new SingleInterjob(future, run, null, period));
            result = true;
        }
        logger.debug("启动定时任务:{}，下次执行的秒数是:{}完成", jobName, period);
        return result;
    }

    @Override
    public Boolean startJob(String jobName, Runnable run, Long period) {
        return startJob(jobName, run, period, true);
    }

    @Override
    public Boolean stopJob(String jobName) {
        logger.debug("正在停止定时任务:{}", jobName);
        Boolean result = false;
        if (interJobMap.containsKey(jobName)) {
            logger.debug("正在执行停止定时任务:{}", jobName);
            SingleInterjob job = interJobMap.remove(jobName);
            job.getFuture().cancel(true);
            interJobMap.remove(jobName);
            result = true;
        }
        logger.debug("停止定时任务:{}完成", jobName);
        return result;
    }

    @Override
    public Boolean updateCronLazy(String jobName, String cron) {
        logger.debug("正在执行修改任务时间,定时任务:{}，表达式时间是:{}", jobName, cron);
        Boolean result = false;
        if (interJobMap.containsKey(jobName)) {
            SingleInterjob interJob = interJobMap.get(jobName);
            logger.debug("找到任务：{}，执行时间覆盖覆盖，旧的时间为：{}", jobName, interJob.getCron());
            interJob.setCron(cron);
            interJobMap.put(jobName, interJob);
            result = true;
            logger.info("task update cron success,cron:{}", cron);
        }
        logger.debug("执行修改任务时间,定时任务:{}，表达式时间是:{}成功", jobName, cron);
        return result;
    }

    @Override
    public Boolean updateSecordsLazy(String jobName, Long period) {
        logger.debug("正在执行修改任务时间,定时任务:{}，下次执行的秒数是:{}", jobName, period);
        Boolean result = false;
        if (interJobMap.containsKey(jobName)) {
            SingleInterjob interJob = interJobMap.get(jobName);
            logger.debug("找到任务：{}，执行时间覆盖覆盖，旧的时间为：{}", jobName, interJob.getPeriod());
            interJob.setPeriod(period);
            interJobMap.put(jobName, interJob);
            result = true;
            logger.info("task update secord success,secord:{}", period);
        }
        logger.debug("执行修改任务时间,定时任务:{}，表达式时间是:{}成功", jobName, period);
        return result;
    }

    @Override
    public Boolean updateJob(String jobName, String cron) {
        Boolean result = false;
        if (interJobMap.containsKey(jobName)) {
            result = updateJob(jobName, interJobMap.get(jobName).getRunnable(), cron);
        }
        return result;
    }

    @Override
    public Boolean updateJob(String jobName, Long secord) {
        Boolean result = false;
        if (interJobMap.containsKey(jobName)) {
            result = updateJob(jobName, interJobMap.get(jobName).getRunnable(), secord);
        }
        return result;
    }

    @Override
    public Boolean updateJob(String jobName, Runnable run) {
        Boolean result = false;
        if (interJobMap.containsKey(jobName)) {
            result = updateJob(jobName, run, interJobMap.get(jobName).getCron());
        }
        return result;
    }

    /**
     * 修改任务
     *
     * @param jobName 任务名称
     * @param run     修改的处理
     * @param conn    修改的时间
     * @return Boolean
     */
    @Override
    public Boolean updateJob(String jobName, Runnable run, String conn) {
        logger.debug("正在执行修改定时任务:{}，表达式时间是:{}", jobName, conn);
        Boolean result = stopJob(jobName);
        // 先停止，在开启.
        if (result) {
            result = startJob(jobName, run, conn, false);
        }
        logger.debug("执行修改定时任务:{}，表达式时间是:{}完成", jobName, conn);
        return result;
    }

    /**
     * 修改任务
     *
     * @param jobName 任务名称
     * @param run     修改的处理
     * @param secord  修改的时间
     * @return Boolean
     */
    @Override
    public Boolean updateJob(String jobName, Runnable run, Long secord) {
        logger.debug("正在执行修改定时任务:{}，下次执行的秒数是:{}", jobName, secord);
        Boolean result = stopJob(jobName);
        // 先停止，在开启.
        if (result) {
            result = startJob(jobName, run, secord, false);
        }
        logger.debug("执行修改定时任务:{}，下次执行的秒数是:{}完成", jobName, secord);
        return result;
    }
}