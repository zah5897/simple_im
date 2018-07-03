package com.pg.im.controller;

import com.pg.im.service.MsgService;
import com.pg.im.util.HttpResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zah on 2018/6/28.
 */
@RestController
@RequestMapping("/msg")
@Api(value = "消息", tags = {"消息相关接口"})
public class MsgController {

    @Autowired
    MsgService msgService;


    @PostMapping("/history/{target}")
    @ApiOperation(value = "获取历史消息")
    public ModelMap listHistoryMsg(@ApiParam(value = "对方", required = true) @PathVariable String target,
                                   @ApiParam(value = "当前用户", required = true) String userId,
                                   @ApiParam(value = "此id以前的历史消息，默认从最近的数据开始")Long lastId,
                                   @ApiParam(value = "每页数量，默认10") Integer pageSize) {
          return HttpResult.getResultOKMap(msgService.getHistoryMsg(target,userId,lastId==null?Long.MAX_VALUE:lastId,pageSize==null?10:pageSize));
    }

    @PostMapping("/offline")
    @ApiOperation(value = "获取离线消息", notes = "offline")
    public ModelMap listOfflineMsg(@ApiParam(value = "当前用户", required = true)String userId,
                                    @ApiParam(value = "每页数量，默认10") Integer pageSize) {
        return HttpResult.getResultOKMap(msgService.getOfflineMsg(userId,pageSize==null?10:pageSize));
    }

    @GetMapping("/offlineCount")
    @ApiOperation(value = "获取离线消息个数", notes = "offlineCount")
   // @ApiImplicitParams(@ApiImplicitParam(name = "userId",value = "当前用户",required = true,dataType ="string",paramType = "query"))
    public ModelMap offlineCount(String userId) {
        return HttpResult.getResultOKMap(msgService.getOfflineMsgCount(userId));
    }

    @PostMapping("/markOfflineMsg")
    @ApiOperation(value = "客户端提交确定离线消息被成功接收到", notes = "markOfflineMsg")
    public ModelMap markOfflineMsg(@ApiParam(value = "当前用户", required = true)String userId,
                                    @ApiParam(value = "被确认已经获取的消息ids，逗号分割提交多个", required = true)String ids) {
        String[] id=ids.split(",");
        msgService.markOfflineToNormal(userId,id);
        return HttpResult.getResultOKMap();
    }
}
