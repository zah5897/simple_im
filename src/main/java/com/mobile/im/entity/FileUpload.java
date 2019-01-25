package com.mobile.im.entity;

import com.mobile.im.annotation.Id;
import com.mobile.im.annotation.Strategy;
import com.mobile.im.enums.FileStatus;
import com.mobile.im.enums.FileType;
import com.sun.xml.internal.bind.v2.model.core.ID;

import java.util.Date;

/**
 * Created by zah on 2018/6/19.
 */

public class FileUpload {
    @Id(strategy = Strategy.UUID)
    private String id;
    private String name;
    private Date upload_time;
    private String owner;
    private long len;
    private FileType type = FileType.IMG;
    private FileStatus status = FileStatus.DEFAULT;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(Date upload_time) {
        this.upload_time = upload_time;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    public FileStatus getStatus() {
        return status;
    }

    public void setStatus(FileStatus status) {
        this.status = status;
    }

    public long getLen() {
        return len;
    }

    public void setLen(long len) {
        this.len = len;
    }
}
