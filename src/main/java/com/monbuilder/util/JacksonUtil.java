package com.monbuilder.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import java.text.SimpleDateFormat;
/***
 * JSON转换工具类
 * @author <a href="mailto:lcbiao34@gmail.com">Builder34</a>
 * @date 2018-11-01 11:14:26
 * */
@Slf4j
public class JacksonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 设置输入时忽略JSON字符串中存在而Java对象实际没有的属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        //设置不输出值为 null 的属性
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
    /**
     * 将JSON字符串根据指定的Class反序列化成Java对象(转换过程中出现异常则返回null对象)
     *
     * @param json      JSON字符串
     * @param objClass JavaBean对象Class
     * @return 反序列化生成的Java对象
     * */
    public static <T> T toJavaObject(String json, Class<T> objClass) {
        try {
            return objectMapper.readValue(json, objClass);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }
    /**
     * 将Java对象序列化成JSON字符串(转换过程中出现异常则返回空对象json串"{}")
     *
     * @param obj 待序列化生成JSON字符串的Java对象
     * @return JSON字符串
     */
    public static String toJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("",e);
        }
        return "{}";
    }
}
