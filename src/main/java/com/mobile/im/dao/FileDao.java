package com.mobile.im.dao;

import com.mobile.im.entity.FileUpload;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zah on 2018/4/25.
 */

@Repository
public class FileDao extends BaseDao<FileUpload> {

    public List<FileUpload> listAll() {
        return jdbcTemplate.query("select *from file_upload  ", new Object[]{}, new BeanPropertyRowMapper<FileUpload>(FileUpload.class));
    }
}
