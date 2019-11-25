package tech.mhuang.ext.interchan.wechat.wechat.common.pool;

import lombok.Getter;
import lombok.Setter;
import tech.mhuang.core.pool.BaseExecutor;
import tech.mhuang.core.reflect.DefaultReflectInvoke;
import tech.mhuang.ext.interchan.wechat.wechat.common.pool.service.ExecuteService;
import tech.mhuang.ext.interchan.wechat.wechat.common.pool.thread.ShareThread;
import tech.mhuang.ext.interchan.wechat.wechat.common.pool.thread.SubscribeThread;
import tech.mhuang.ext.interchan.wechat.wechat.common.pool.thread.TextThread;
import tech.mhuang.ext.spring.reflect.SpringReflectInvoke;

import java.lang.reflect.InvocationTargetException;

/**
 * 微信执行事件
 *
 * @author mhuang
 * @since 1.0.0
 */
public class ExecutorEventWechat {

    @Setter
    @Getter
    private BaseExecutor eService;

    public void subscribe(String openId, String status, ExecuteService weChatService) {
        eService.execute(new SubscribeThread(openId, status, weChatService));
    }

    public void subscribeEventOther(String openId, String status, String eventKey, ExecuteService weChatService) {
        eService.execute(new SubscribeThread(openId, status, eventKey, weChatService));
    }

    public void textMsg(String openId, String content, ExecuteService weChatService) {
        eService.execute(new TextThread(openId, content, weChatService));
    }

    public void share(String usrId, String status, String type, String shareName, String uuid, ExecuteService weChatService) {
        eService.execute(new ShareThread(usrId, status, type, shareName, uuid, weChatService));
    }

    /**
     * 其他方式调用线程池处理（异步）
     *
     * @param clazz  需要调用的class（支持直接传class，采用jdk反射调用方式，传入beanName 代表使用spring代码方式）
     *               spring代理方式支持service（dao 自动引入）方式 。 JDK 需要自己做处理
     * @param method 调用的方法
     */
    public void other(final Object clazz, final String method) {
        eService.execute(() -> localCall(clazz, method));
    }

    /**
     * 其他方式调用线程池处理（异步）
     *
     * @param clazz  需要调用的class（支持直接传class，采用jdk反射调用方式，传入beanName 代表使用spring代码方式）
     *               spring代理方式支持service（dao 自动引入）方式 。 JDK 需要自己做处理
     * @param method 调用的方法
     * @param param  传输的值
     */
    public void other(final Object clazz, final String method, final Object param) {
        eService.execute(() -> localCall(clazz, method, param));
    }

    private void localCall(Object clazz, String method, Object... param) {
        if (clazz instanceof Class<?>) {
            try {
                new DefaultReflectInvoke().getMethodToValue((Class<?>) clazz, method, param);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        } else {
            try {
                new SpringReflectInvoke().getMethodToValue((String) clazz, method, param);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 其他方式调用线程池处理（异步）
     *
     * @param clazz  需要调用的class（支持直接传class，采用jdk反射调用方式，传入beanName 代表使用spring代码方式）
     *               spring代理方式支持service（dao 自动引入）方式 。 JDK 需要自己做处理
     * @param method 调用的方法
     * @param params 传输的值
     */
    public void other(final Object clazz, final String method, final Object... params) {
        eService.execute(() -> localCall(clazz, method, params));
    }
}
