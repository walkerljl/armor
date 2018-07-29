package org.walkerljl.armor.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * JSONUtils
 *
 * @author xingxun
 */
public class JSONUtils {

    /**
     * fastjson 统一配置
     */
    private static final SerializerFeature[] FASTJSON_CONFIG = new SerializerFeature[] {
            SerializerFeature.DisableCircularReferenceDetect,
            SerializerFeature.WriteDateUseDateFormat,
            SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullStringAsEmpty
    };
    //,SerializerFeature.PrettyFormat};

    /**
     * 将对象转成JSON字符串
     *
     * @param object
     * @return
     */
    public static String toJSONString(Object object) {
        return JSON.toJSONString(object, FASTJSON_CONFIG);
    }

    /**
     * 将Map<String, Object>转换成Map<String, String>即将Map的value序列化成JSON字符串
     *
     * @param dataMap
     * @return
     */
    public static Map<String, String> toJSON(Map<String, Object> dataMap) {
        if (dataMap == null || dataMap.isEmpty()) {
            return null;
        }
        Map<String, String> result = new HashMap<String, String>();
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            result.put(entry.getKey(), JSONUtils.toJSONString(entry.getValue()));
        }
        return result;
    }

    /**
     * 将JSON字符串解析成JSONObject对象
     *
     * @param jsonString JSON字符串
     * @return JSONObject
     */
    public static JSONObject parseObject(String jsonString) {
        if (jsonString == null || "".equals(jsonString.trim())) {
            return null;
        }
        return JSON.parseObject(jsonString);
    }

    /**
     * 解析成Map
     * @param jsonString JSON字符串
     * @return
     */
    public static Map<String, String> parseMap(String jsonString) {

        if (jsonString == null || "".equals(jsonString.trim())) {
            return null;
        }

        return JSON.parseObject(jsonString, Map.class);
    }

    /**
     * 将JSON字符串解析成Java对象
     *
     * @param jsonString
     * @param clazz
     * @return
     */
    public static <T> T parseObject(String jsonString, Class<T> clazz) {
        if (jsonString == null || "".equals(jsonString.trim())) {
            return null;
        }
        if (clazz == null || clazz == String.class) {
            return (T) jsonString;
        }
        return JSON.parseObject(jsonString, clazz);
    }

    /**
     * 解析成List对象
     *
     * @param jsonString
     * @param clazz
     * @return
     */
    public static <T> List<T> parseList(String jsonString, Class<T> clazz) {
        if (jsonString == null || "".equals(jsonString.trim())) {
            return null;
        }
        return JSONObject.parseArray(jsonString, clazz);
    }

    /**
     * 解析成List对象
     *
     * @param jsonStringList
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> parseList(Collection<String> jsonStringList, Class<T> clazz) {
        if (jsonStringList == null || jsonStringList.isEmpty()) {
            return null;
        }
        if (clazz == null || clazz == String.class) {
            return (List<T>) jsonStringList;
        }
        List<T> result = new ArrayList<T>();
        for (String jsonString : jsonStringList) {
            if (jsonString == null || "".equals(jsonString.trim())) {
                continue;
            }
            T obj = JSONUtils.parseObject(jsonString, clazz);
            if (obj != null) {
                result.add(obj);
            }
        }
        return result;
    }

    /**
     * 解析成List集合对象,解析对象为Map<String, String>的value
     *
     * @param jsonStringMap
     * @param clazz
     * @return
     */
    public static <T> List<T> parseList(Map<String, String> jsonStringMap, Class<T> clazz) {
        if (jsonStringMap == null || jsonStringMap.isEmpty()) {
            return null;
        }
        List<T> result = new ArrayList<T>();
        for (Map.Entry<String, String> entry : jsonStringMap.entrySet()) {
            T object = JSONUtils.parseObject(entry.getValue(), clazz);
            if (object != null) {
                result.add(object);
            }
        }
        return result;
    }
}