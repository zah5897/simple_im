package com.pg.im.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pg.im.comm.FileStatus;
import com.pg.im.comm.FileType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zah on 2018/6/19.
 */

@Entity
@Table(name = "upload_files")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class UploadFile {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    @Column(length = 64)
    private String name;
    private Date upload_time;
    @Column(length = 32)
    private String _from;

    @Enumerated(EnumType.ORDINAL)
    private FileType type = FileType.IMG;
    @Enumerated(EnumType.ORDINAL)
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

    public String get_from() {
        return _from;
    }

    public void set_from(String _from) {
        this._from = _from;
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
}
