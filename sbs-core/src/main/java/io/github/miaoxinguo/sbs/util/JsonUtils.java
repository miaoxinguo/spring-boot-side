package io.github.miaoxinguo.sbs.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import org.apache.commons.lang3.StringUtils;


import java.util.List;

/**
 * 按约定封装后台返回前台的数据
 */
public final class JsonUtils {

    /*
     *Fastjson的SerializerFeature序列化属性:
     *
     *  QuoteFieldNames———-输出key时是否使用双引号,默认为true
     *  WriteMapNullValue——–是否输出值为null的字段,默认为false
     *  WriteNullNumberAsZero—-数值字段如果为null,输出为0,而非null
     *  WriteNullListAsEmpty—–List字段如果为null,输出为[],而非null
     *  WriteNullStringAsEmpty—字符类型字段如果为null,输出为”“,而非null
     *  WriteNullBooleanAsFalse–Boolean字段如果为null,输出为false,而非null
     */
    private JsonUtils() {
    }

    /**
     * 通用方法
     */
    public static String toJsonString(Object object) {
        return JSON.toJSONString(object,SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * 配合属性filter的转json方法
     */
    public static String toJsonString(Object object, SimplePropertyPreFilter filter) {
        return JSON.toJSONString(object, filter);
    }

    /**
     * 通用方法
     */
    public static JSONObject toJsonObject(String text) {
        return JSON.parseObject(text);
    }

    /**
     * 将JsonObject转换为对应的对象
     */
    public static <T> T parseObject(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    /**
     * 将JsonArray转换为对应的List
     */
    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

    /**
     * 返回成功标识和包含null作为空串的信息
     */
    public static String toSuccessJsonObjectIncludeNull(Object obj) {
        return "{\"success\":true,\"msg\":" + toJsonStringIncludeNull(obj) + "}";
    }

    /**
     * null值作为空串
     */
    public static String toJsonStringIncludeNull(Object object) {
        SerializerFeature[] sf = {
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullListAsEmpty
        };
        return JSON.toJSONString(object, sf);
    }

    /**
     * 只包含指定属性
     *
     * @param properties 属性名数组
     */
    public static String toJsonStringWithIncludes(Object object, String... properties) {
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        for (String p : properties) {
            filter.getIncludes().add(p);
        }
        return toJsonString(object, filter);
    }

    /**
     * 只包含指定属性
     *
     * @param properties 属性名集合
     */
    public static String toJsonStringWithIncludes(Object object, List<String> properties) {
        return toJsonStringWithIncludes(object, properties.toArray(new String[0]));
    }

    /**
     * 忽略object中的指定属性符合规范的json串
     *
     * @param properties 属性名
     */
    public static String toSuccessJsonStringWithExcludes(Object object, String... properties) {
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        for (String p : properties) {
            filter.getExcludes().add(p);
        }
        return "{\"success\":true,\"msg\":" + toJsonString(object, filter) + "}";
    }
    /**
     * 只包含指定属性符合规范的json串
     *
     * @param properties 属性名数组
     */
    public static String toSuccessJsonObjectWithIncludes(Object object, String... properties) {
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        for (String p : properties) {
            filter.getIncludes().add(p);
        }
        return "{\"success\":true,\"msg\":" + toJsonString(object, filter) + "}";
    }


    /**
     * 忽略object中的指定属性
     *
     * @param properties 属性名
     */
    public static String toJsonStringWithExcludes(Object object, String... properties) {
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        for (String p : properties) {
            filter.getExcludes().add(p);
        }
        return toJsonString(object, filter);
    }

    /**
     * 忽略object中的指定属性
     *
     * @param properties 属性名
     */
    public static String toJsonStringWithExcludes(Object object, List<String> properties) {
        return toJsonStringWithExcludes(object, properties.toArray(new String[0]));
    }

    /**
     * 返回成功标识
     */
    public static String toSuccessJson() {
        return "{\"success\":true}";
    }

    /**
     * 返回成功标识和信息
     */
    public static String toSuccessJson(final String msg) {
        if (StringUtils.isBlank(msg)) {
            return toSuccessJson();
        }
        return "{\"success\":true,\"msg\":\"" + msg + "\"}";
    }

    /**
     * 返回成功标识和信息
     */
    public static String toSuccessJsonObject(Object obj) {
        return "{\"success\":true,\"msg\":" + toJsonString(obj) + "}";
    }

    /**
     * 返回成功标识和信息
     *
     * @param msg 必须是Json字符串 {}或[]结构
     */
    public static String toSuccessJsonObject(final String msg) {
        if (StringUtils.isBlank(msg)) {
            return toSuccessJson();
        }
        return "{\"success\":true,\"msg\":" + msg + "}";
    }

    /**
     * 返回失败标识和信息
     */
    public static String toFailureJson() {
        return toFailureJson("");
    }

    /**
     * 返回失败标识和信息
     */
    public static String toFailureJson(final String msg) {
        return toFailureJson(0, msg);
    }

    /**
     * 返回失败标识、响应码、失败信息
     */
    public static String toFailureJson(final int code, final String msg) {
        return "{\"success\":false,\"code\":" + code + ",\"msg\":\"" + msg + "\",\"body\":\"" + msg + "\"}";
    }

}
