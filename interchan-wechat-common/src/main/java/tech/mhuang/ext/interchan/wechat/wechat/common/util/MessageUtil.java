package tech.mhuang.ext.interchan.wechat.wechat.common.utils;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import lombok.Getter;
import lombok.Setter;
import tech.mhuang.core.util.StringUtil;
import tech.mhuang.ext.interchan.wechat.wechat.common.consts.WechatConsts;
import tech.mhuang.ext.interchan.wechat.wechat.common.model.message.OtherResMessage;

import java.io.Writer;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 消息工具类
 *
 * @author mhuang
 * @since 1.0.0
 */
public class MessageUtil<T> {


    /**
     * 对象转JSON
     *
     * @param obj 对象
     * @return json字符串
     */
    public String fromObjectToJson(T obj) {
        SerializeWriter sw = new SerializeWriter();
        JSONSerializer serializer = new JSONSerializer(sw);

        //名字重定义
        NameFilter nameFilter = (source, name, value) -> {
            String msgType = "";
            if (StringUtil.equals(name, WechatConsts.MSGTYPE)) {
                msgType = value.toString();
            } else if (StringUtil.equals(name, WechatConsts.OTHERMESSAGE)) {
                name = msgType;
            }
            return name;
        };
        serializer.getNameFilters().add(nameFilter);
        serializer.write(obj);
        return sw.toString();
    }

    /**
     * 对象转xml
     *
     * @param obj 对象
     * @return xml字符串
     */
    public synchronized String fromObjectToXml(T obj) {
        return fromObjectToXml(obj, true);
    }

    /**
     * 对象转XML
     *
     * @param obj   转换对象
     * @param retUp 是否首字母转大写
     * @return xml字符串
     */
    @SuppressWarnings("rawtypes")
    public synchronized String fromObjectToXml(T obj, boolean retUp) {
        boolean applyRetUp = upret;
        upret = retUp;
        xstream.autodetectAnnotations(true);
        xstream.aliasSystemAttribute(null, WechatConsts.CLASS);
        if (obj instanceof OtherResMessage) {
            xstream.aliasField(((OtherResMessage) obj).getMsgType(),
                    OtherResMessage.class, WechatConsts.OTHERMESSAGE);// 冲定义字段
        } else if (obj instanceof Map) {
            xstream.registerConverter(new MapEntryConverter());
        }
        xstream.alias("xml", obj.getClass());

        String retStr = xstream.toXML(obj);
        upret = applyRetUp;
        return retStr;
    }

    @Setter
    @Getter
    private static boolean upret = true;

    /**
     * 扩展xstream，使其支持CDATA块
     */
    private static XStream xstream = new XStream(new XppDriver() {

        @Override
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = false;
                ;

                @Override
                @SuppressWarnings("rawtypes")
                public void startNode(String name, Class clazz) {
                    if (clazz.equals(String.class)) {
                        cdata = true;
                    }
                    if (upret) {
                        if (!StringUtil.equals(name, WechatConsts.XML)
                                && !StringUtil.equals(name, WechatConsts.ITEM)) { // 判断开头是否是xml！如果是不是转更改首字母为大写
                            name = StringUtil.toUpperCaseFirstOne(name);
                        }
                    }

                    super.startNode(name, clazz); // 首字母转大写
                }

                @Override
                public String encodeNode(String name) {
                    return name;
                }

                @Override
                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                        cdata = false;
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

    /**
     * Map转换器
     *
     * @author mhuang
     * @since 1.0.0
     */
    public static class MapEntryConverter implements Converter {
        @Override
        @SuppressWarnings("rawtypes")
        public boolean canConvert(Class clazz) {
            return AbstractMap.class.isAssignableFrom(clazz);
        }

        @Override
        @SuppressWarnings("rawtypes")
        public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
            AbstractMap map = (AbstractMap) value;
            for (Object obj : map.entrySet()) {
                Entry entry = (Entry) obj;
                writer.startNode(entry.getKey().toString());
                if (entry.getValue() instanceof String) {
                    writer.setValue("<![CDATA[" + entry.getValue() + "]]>");
                } else {
                    writer.setValue(entry.getValue().toString());
                }

                writer.endNode();
            }
        }

        @Override
        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
            Map<String, String> map = new HashMap<>();
            while (reader.hasMoreChildren()) {
                reader.moveDown();
                map.put(reader.getNodeName(), reader.getValue());
                reader.moveUp();
            }
            return map;
        }
    }
}
