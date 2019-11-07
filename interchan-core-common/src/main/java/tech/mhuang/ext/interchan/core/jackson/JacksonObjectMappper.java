package tech.mhuang.ext.interchan.core.jackson;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Map;
import java.util.TimeZone;

/**
 * jackson 封装
 *
 * @author mhuang
 * @since 1.0.0
 */
public class JacksonObjectMappper extends ObjectMapper {
    private static final long serialVersionUID = 1L;

    public JacksonObjectMappper() {
        findAndRegisterModules();
        configOverride(Map.class);
        setSerializationInclusion(Include.NON_NULL);
        configure(MapperFeature.INFER_PROPERTY_MUTATORS, false);
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        setTimeZone(TimeZone.getTimeZone("GMT+8"));
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, true);
    }
}
