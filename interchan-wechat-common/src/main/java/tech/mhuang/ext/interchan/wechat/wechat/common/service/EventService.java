package tech.mhuang.ext.interchan.wechat.wechat.common.service;

public abstract class EventService {


    /**
     * 关注
     *
     * @param openId 关注的openId
     * @param appId  关注的公众号
     * @return 返回应答的数据
     */
    public abstract String subscribe(String openId, String appId);


    /**
     * 带事件的关注（比如扫码关注）
     *
     * @param openId   关注的openId
     * @param appId    关注的公众号
     * @param eventKey 关注的事件Key
     * @return 返回应答的数据
     */
    public abstract String subscribe(String openId, String appId, String eventKey);

    /**
     * 取消关注
     *
     * @param openId 取消的openId
     * @param appId  取消的公众号
     * @return 此应答暂时无用、微信不支持
     */
    public abstract String unSubscribe(String openId, String appId);

    /**
     * 点击菜单事件
     *
     * @param openId   点击的openId
     * @param appId    点击的公众号
     * @param eventKey 点击的事件key
     * @return 应答的消息内容
     */
    public abstract String click(String openId, String appId, String eventKey);


    /**
     * 打开网页事件
     *
     * @param openId   打开的openId
     * @param appId    打开的appId
     * @param eventKey 打开的事件
     * @return 应答的消息内容
     */
    public abstract String view(String openId, String appId, String eventKey);

    /**
     * 扫码事件
     *
     * @param openId   扫码的openId
     * @param appId    扫码的公众号
     * @param eventKey 二维码的事件key
     * @return 应答的消息内容
     */
    public abstract String scan(String openId, String appId, String eventKey);

    /**
     * 文本消息
     *
     * @param openId 发送文本消息的openId
     * @param appId  公众号
     * @param msg    消息
     * @return 应答的消息内容
     */
    public abstract String textMsg(String openId, String appId, String msg);

    /**
     * 图片消息，后续扩展
     *
     * @param openId 发送的图片消息的openId
     * @param appId  公众号
     * @return 应答的消息内容
     */
    public abstract String imageMsg(String openId, String appId);
}
