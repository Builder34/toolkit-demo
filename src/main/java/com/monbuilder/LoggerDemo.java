package com.monbuilder;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;

/**
 * LoggerDemo 日志打印示例
 *
 * @author <a href="mailto:lcbiao34@gmail.com">Builder34</a>
 * @date 2019-01-18 14:35:49
 */
@Slf4j
public class LoggerDemo {

    public static void main(String[] args) {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        // print logback's internal status
        StatusPrinter.print(lc);
        log.info("logback start...");
    }
}
