package com.mobile.im.controller;

import com.mobile.im.exception.ERROR;
import com.mobile.im.service.UserService;
import com.mobile.im.util.HttpResult;
import com.mobile.im.util.MD5Util;
import com.mobile.im.util.TextUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

/**
 * Created by zah on 2018/6/19.
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/regist")
    @ApiOperation(value = "注册userId")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "username", value = "注册用户Id", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "password", value = "密码", required = true)})
    public ModelMap regist(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            return HttpResult.getResultMap(ERROR.ERR_FAILED, "userId不能为空");
        }
        if (TextUtils.isEmpty(password)) {
            return HttpResult.getResultMap(ERROR.ERR_FAILED, "password不能为空");
        }

        String md5 = "";
        try {
            md5 = MD5Util.getMd5(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return HttpResult.getResultMap(ERROR.ERR_FAILED, "password处理异常");
        }
        int code = userService.regist(username, md5);
        return HttpResult.getResultMap(ERROR.values()[code]);
    }
}
