package com.mobile.im.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mobile.im.annotation.IgnoreColumn;
import jdk.nashorn.internal.ir.annotations.Ignore;

import java.util.Date;

/**
 * Created by zah on 2018/6/22.
 */
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class User {
    @IgnoreColumn
    private int id;
    private String username;

    @Ignore
    private String password;
    @Ignore
    private Date create_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
