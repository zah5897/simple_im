package com.mobile.im;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by zah on 2018/7/2.
 */
@Component
public class Config {

    //处理文件上传
    @Value("${im.upload_file}")
    private String IM_UPLOAD_FILE_CFG;

    public String getIM_UPLOAD_FILE_CFG() {
        return IM_UPLOAD_FILE_CFG;
    }
}
