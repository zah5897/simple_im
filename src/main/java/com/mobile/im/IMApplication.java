package com.mobile.im;

import com.mobile.im.start.ServerLauncherImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class IMApplication extends SpringBootServletInitializer {

    public static int imPort;

    public static void main(String[] args) {
        SpringApplication.run(IMApplication.class, args);
    }


    private static void startIM() {
        try {
            while (true) {
                if (imPort > 0) {
                    ServerLauncherImpl.startIM(imPort);
                    break;
                } else {
                    Thread.sleep(1000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(IMApplication.class);
    }

    @Value("${im.port}")
    public void setImPort(int imPort) {
        IMApplication.imPort = imPort;
        new Thread() {
            @Override
            public void run() {
                startIM();
            }
        }.start();
    }

//    @Bean
//    public MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        // 设置文件大小限制 ,超出设置页面会抛出异常信息，
//        // 这样在文件上传的地方就需要进行异常信息的处理了;
//        factory.setMaxFileSize("4MB"); // KB,MB
//        /// 设置总上传数据总大小
//        factory.setMaxRequestSize("100MB");
//        // Sets the directory location where files will be stored.
//        // factory.setLocation("路径地址");
//        return factory.createMultipartConfig();
//    }
}
