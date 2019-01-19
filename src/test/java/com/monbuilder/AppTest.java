package com.monbuilder;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.monbuilder.bean.CompanyBean;
import com.monbuilder.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AppTest
 *
 * @author <a href="mailto:lcbiao34@gmail.com">Builder34</a>
 * @date 2019-01-18 16:01:30
 */
@Slf4j
public class AppTest {
    @Test
    public void stringUtils(){
        String a = "  ";
        String b = null;
        //判断字符对象是否为空以及内容是否为空串(有空格则认为不是空串)
        log.info("StringUtils.isEmpty(a): {}", StringUtils.isEmpty(a));
        //判断字符对象是否为空以及内容是否为空串(有空格也会认为是空串)
        log.info("StringUtils.isBlank(a): {}", StringUtils.isBlank(a));
        //当b=null时，如果b.trim()则会报空指针异常，使用StringUtils.trim(b)可以避免
        log.info("StringUtils.trim(d): {}", StringUtils.trim(b));

        String num = "12.3";
        //当b=null时，如果b.trim()则会报空指针异常，使用StringUtils.trim(b)可以避免
        log.info("org.apache.commons.lang3.StringUtils.isNumericSpace(): {} isNumber: {}", num, StringUtils.isNumericSpace(b));
        log.info("com.alibaba.druid.util.StringUtils.isNumber(): {} isNumber: {}", num, com.alibaba.druid.util.StringUtils.isNumber(num));
    }
    @Test
    public void dateFormatUtils() throws Exception{
        String pattern = "yyyy-MM-dd HH:mm:ss";
        String timeStr = DateFormatUtils.format(new Date(), pattern);
        long timestamp = DateUtils.parseDate(timeStr, pattern).getTime();
        log.info("==> current time: {}", timeStr);
        log.info("==> current time timestamp: {}", timestamp);
    }

    @Test
    public void beanUtils() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        CompanyBean bean = new CompanyBean();
        bean.setId(1);
        bean.setName("中国移动广州分公司");
        bean.setAddress("广州市天河区中山大道1号");
        bean.setTel("020-10086000");
        CompanyBean destObj = new CompanyBean();
        //复制bean之间复制内容, 新对象destObj需要先实例化
        BeanUtils.copyProperties(destObj, bean);
        //ToStringBuilder类来自commons-lang3库：将对象内容转换成字符串输出，方便于日志输出
        log.info("destObj from BeanUtils.copyProperties: {}", ToStringBuilder.reflectionToString(destObj));

        Map<String, Object> map = new HashMap<>();
        map.put("id", 2);
        map.put("name", "中国联通广州分公司");
        map.put("address", "广州市天河区中山大道2号");
        map.put("tel", "020-10000110");
        //将map(key,value)映射成bean
        BeanUtils.populate(destObj, map);
        log.info("destObj from BeanUtils.populate: {}", ToStringBuilder.reflectionToString(destObj));
        //复制对象，与copyProperties()方法比较，这里新对象可以不先实例化
        CompanyBean cloneBean = (CompanyBean)BeanUtils.cloneBean(destObj);
        log.info("cloneBean from BeanUtils.cloneBean: {}", ToStringBuilder.reflectionToString(cloneBean));
        //将JavaBean转换成Map
        Map newMap = BeanUtils.describe(cloneBean);
        log.info("newMap from BeanUtils.describe: {}", new Gson().toJson(newMap));
    }

    @Test
    public void ioUtils() throws IOException {
        InputStream io = this.getClass().getClassLoader().getResourceAsStream("README.md");
        BufferedReader br = new BufferedReader(new InputStreamReader(io));
        log.info("==> IOUtils.toString(br): {}", IOUtils.toString(br));
        IOUtils.closeQuietly(br);
        IOUtils.closeQuietly(io);
    }
    @Test
    public void collectionUtils(){
        List<String> list = Lists.newArrayList();
        log.info("==> CollectionUtils.isNotEmpty(list): {}", CollectionUtils.isNotEmpty(list));
    }

    @Test
    public void jsonUtils(){
        CompanyBean bean = new CompanyBean();
        bean.setId(1);
        bean.setName("中国移动广州分公司");
        bean.setAddress("广州市天河区中山大道1号");
        bean.setTel("020-10086000");
        log.info("==>  JacksonUtil.toJsonString(bean): {}", JacksonUtil.toJsonString(bean));
        String json = JacksonUtil.toJsonString(bean);
        log.info("==>  JacksonUtil.toJavaObject: {}", ToStringBuilder.reflectionToString(JacksonUtil.toJavaObject(json, CompanyBean.class)));

    }
}
