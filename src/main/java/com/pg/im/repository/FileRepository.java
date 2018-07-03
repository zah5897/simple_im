package com.pg.im.repository;

import com.pg.im.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by zah on 2018/4/25.
 */
public interface FileRepository extends JpaRepository<UploadFile, String> {
    @Query("select f from UploadFile f")
    List<UploadFile> listAll();
}
