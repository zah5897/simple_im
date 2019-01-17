package com.mobile.im.util;

import com.mobile.im.Config;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileSaveUtils {

    // 头像压缩 按此宽度
    public static final int PRESS_AVATAR_WIDTH = 120;
    // 用户上传按此宽度
    public static final int PRESS_IMAGE_WIDTH = 320;

    private static String FILE_ROOT_PATH;


    //语音文件保存路径
    public static final String FILE_ROOT_MSG_AUDIO = "/msg/voice/";

    //图片文件保存路径
    public static final String FILE_ROOT_MSG_IMG_THUMB = "/msg/imgs/thumb/";
    public static final String FILE_ROOT_MSG_IMG_ORIGIN = "/msg/imgs/origin/";

    private static String getRootPath() {
        if (FILE_ROOT_PATH == null) {
            FILE_ROOT_PATH = ((Config) SpringContextUtil.getBean("config")).getIM_UPLOAD_FILE_CFG();
        }
        return FILE_ROOT_PATH;
    }

    public static String getAudioPath() {
        return getRootPath() + FILE_ROOT_MSG_AUDIO;
    }

    public static String getMsgThumbPath() {
        return getRootPath() + FILE_ROOT_MSG_IMG_THUMB;
    }

    public static String getMsgOriginPath() {
        return getRootPath() + FILE_ROOT_MSG_IMG_ORIGIN;
    }


    public static String saveImg(MultipartFile file)
            throws IllegalStateException, IOException {
        String filePath = getMsgOriginPath();
        new File(filePath).mkdirs();
        String shortName = file.getOriginalFilename();
        if (!TextUtils.isEmpty(shortName)) {
            String fileShortName = null;
            if (shortName.contains(".")) {
                fileShortName = UUID.randomUUID() + "." + shortName.split("\\.")[1];
            } else {
                fileShortName = UUID.randomUUID().toString();
            }
            File uploadFile = new File(filePath + fileShortName);
            file.transferTo(uploadFile);// 保存到一个目标文件中。

            String thumbFile = getMsgThumbPath() + fileShortName;
            pressImageByWidth(uploadFile.getAbsolutePath(), PRESS_AVATAR_WIDTH, thumbFile);
            return fileShortName;
        }
        return null;
    }

    public static long getMsgImgOriginSize(String name) {
        return new File(getMsgOriginPath() + name).length();
    }

    public static String saveVoice(MultipartFile file)
            throws IllegalStateException, IOException {
        String filePath = getAudioPath();
        new File(filePath).mkdirs();
        String shortName = file.getOriginalFilename();
        if (!TextUtils.isEmpty(shortName)) {
            String fileShortName = null;
            if (shortName.contains(".")) {
                fileShortName = UUID.randomUUID() + "." + shortName.split("\\.")[1];
            } else {
                fileShortName = UUID.randomUUID().toString();
            }
            File uploadFile = new File(filePath + fileShortName);
            file.transferTo(uploadFile);// 保存到一个目标文件中。
            return fileShortName;
        }
        return null;
    }

    public static void removeImg(String oldFileName) {
        // 删除大图
        if (TextUtils.isEmpty(oldFileName)) {
            return;
        }
        String filePath = getMsgOriginPath();
        File uploadFile = new File(filePath + oldFileName);
        if (uploadFile.exists()) {
            uploadFile.delete();
        }
        // 删除小图
        String smallPath = getMsgThumbPath();
        File uploadSmallFile = new File(smallPath + oldFileName);
        if (uploadSmallFile.exists()) {
            uploadSmallFile.delete();
        }
    }


    public static void removeAudio(String oldFileName) {
        // 删除大图

        String filePath = getAudioPath();
        File uploadFile = new File(filePath + oldFileName);
        if (uploadFile.exists()) {
            uploadFile.delete();
        }
    }

    public static void pressImageByWidth(String origin, int minWidth, String thumb) throws IOException {
        ImageCompressUtil.resizeByWidth(origin, minWidth, thumb);
    }
}
