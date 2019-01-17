package com.mobile.im.service;

import com.mobile.im.dao.FileDao;
import com.mobile.im.entity.FileUpload;
import com.mobile.im.enums.FileStatus;
import com.mobile.im.enums.FileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by zah on 2018/6/19.
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class FileService {
    @Autowired
    FileDao fileDao;

    public String saveFile(String owner, String fileName, long len, FileType type) {
        FileUpload uploadFile = new FileUpload();
        uploadFile.setName(fileName);
        uploadFile.setOwner(owner);
        uploadFile.setType(type);
        uploadFile.setLen(len);
        uploadFile.setStatus(FileStatus.DEFAULT);
        uploadFile.setUpload_time(new Date());
        fileDao.insertObj(uploadFile);
        return uploadFile.getId();
    }

}
