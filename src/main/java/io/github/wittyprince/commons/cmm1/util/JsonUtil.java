package io.github.wittyprince.commons.cmm1.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * json util
 *
 * @author WangChen
 * Created on 2023/10/11
 * @since 1.0.1
 */
public class JsonUtil {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    public static ObjectMapper getMapper() {
        return mapper;
    }

    /**
     * object to json string
     */
    public static String toJsonString(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == String.class) {
            return obj.toString();
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("json序列化出错：" + obj, e);
            return null;
        }
    }

    public static <T> T parseObject(String json, Class<T> clazz) {
        if (json == null || "".equals(json)) {
            return null;
        }
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }

    public static <T> T parseObject(String json, TypeReference<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }

    public static <E> List<E> parseList(String json, Class<E> clazz) {
        if (json == null || "".equals(json)) {
            return null;
        }
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }

    public static <K, V> Map<K, V> parseMap(String json, Class<K> kClass, Class<V> vClass) {
        if (json == null || "".equals(json)) {
            return null;
        }
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }
}
