package tech.mhuang.ext.interchan.core.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.ScheduledFuture;

/**
 * 单机版本的任务实体
 *
 * @author mhuang
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleInterjob {

    /**
     * 回调的信息
     */
    private ScheduledFuture<?> future;

    /**
     * 执行的接口
     */
    private Runnable runnable;

    /**
     * cron表达式
     */
    private String cron;

    /**
     * 间隔秒数
     */
    private Long period;
}
