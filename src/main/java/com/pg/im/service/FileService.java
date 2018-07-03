package com.pg.im.service;

import com.pg.im.comm.FileStatus;
import com.pg.im.comm.FileType;
import com.pg.im.entity.UploadFile;
import com.pg.im.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by zah on 2018/6/19.
 */
@Service
public class FileService {
    @Autowired
    FileRepository repository;

    public String saveFile(String owner, String fileName, FileType type) {
        UploadFile uploadFile = new UploadFile();
        uploadFile.setName(fileName);
        uploadFile.set_from(owner);
        uploadFile.setType(type);
        uploadFile.setStatus(FileStatus.DEFAULT);
        uploadFile.setUpload_time(new Date());
        repository.save(uploadFile);
        return uploadFile.getId();
    }

}
