package com.mobile.im.controller;

import com.mobile.im.enums.FileType;
import com.mobile.im.exception.ERROR;
import com.mobile.im.service.FileService;
import com.mobile.im.util.FileSaveUtils;
import com.mobile.im.util.HttpResult;
import com.mobile.im.util.TextUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zah on 2018/6/19.
 */
@RestController
@RequestMapping("/upload")
@Api(value = "文件", tags = {"文件上传"})
public class FileController {

    @Autowired
    FileService fileService;

    @RequestMapping("/image/{username}")
    @ApiOperation(value = "文件上传")
    public ModelMap image(@PathVariable String username, MultipartFile file) {
        if (file == null) {
            return HttpResult.getResultMap(ERROR.ERR_FILE_UPLOAD);
        }
        String fileName = file.getOriginalFilename();
        if (TextUtils.isEmpty(fileName)) {
            return HttpResult.getResultMap(ERROR.ERR_FILE_UPLOAD);
        }
        try {
            String localName = FileSaveUtils.saveImg(file);
            long len = FileSaveUtils.getMsgImgOriginSize(localName);
            String id = fileService.saveFile(username, localName, len, FileType.IMG);
            Map<String, String> data = new HashMap<>();
            data.put("id", id);
            data.put("name", localName);
            return HttpResult.getResultOKMap(data);
        } catch (IOException e) {
            return HttpResult.getResultMap(ERROR.ERR_FILE_UPLOAD, e.getMessage());
        }
    }


    @RequestMapping("/voice/{username}")
    @ApiOperation(value = "文件上传")
    public ModelMap voice(@PathVariable String username, MultipartFile file, int voiceTimeLen) {
        if (file == null) {
            return HttpResult.getResultMap(ERROR.ERR_FILE_UPLOAD);
        }
        String fileName = file.getOriginalFilename();
        if (TextUtils.isEmpty(fileName)) {
            return HttpResult.getResultMap(ERROR.ERR_FILE_UPLOAD);
        }
        try {
            String localName = FileSaveUtils.saveVoice(file);
            String id = fileService.saveFile(username, localName, voiceTimeLen, FileType.AUDIO);
            Map<String, String> data = new HashMap<>();
            data.put("id", id);
            data.put("name", localName);
            data.put("voiceTimeLen", String.valueOf(voiceTimeLen));
            return HttpResult.getResultOKMap(data);
        } catch (IOException e) {
            return HttpResult.getResultMap(ERROR.ERR_FILE_UPLOAD, e.getMessage());
        }
    }
}
