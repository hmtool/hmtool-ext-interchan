package tech.mhuang.ext.interchan.wechat.wechat.common.pool.service;

/**
 * 执行接口..可自行拓展
 *
 * @author mhuang
 * @since 1.0.0
 */
public interface ExecuteService {

    @Deprecated
    void share(String usrId, String status, String type,
               String shareName, String uuid);

    void subscribe(String openId, String status);

    void scan(String openId, String eventKey);

    void subscribeOtherEvent(String openId, String status, String eventKey);

    void saveOpTextSend(String openId, String content);
}
