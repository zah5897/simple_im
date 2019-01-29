package com.mobile.im.controller;

import com.mobile.im.service.LocationService;
import com.mobile.im.service.MsgService;
import com.mobile.im.util.HttpResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zah on 2018/6/28.
 */
@RestController
@RequestMapping("/location")
@Api(tags = "位置")
public class LocationController {

    @Autowired
    LocationService locationService;

    @GetMapping("/publish")
    @ApiOperation(value = "获取以发布的位置数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "username", value = "当前用户", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "lastId", value = "上一页最顶部消息id"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "pageSize", value = "每页数量")})
    public ModelMap loadPublish(String username, Integer lastId, Integer pageSize) {
        return HttpResult.getResultOKMap(locationService.loadPublish(username, lastId, pageSize));
    }

}
