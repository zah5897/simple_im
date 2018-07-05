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
@Api(tags="用户管理")
public class MsgController {

    @Autowired
    MsgService msgService;


    @PostMapping("/history/{target}")
    @ApiOperation(value = "获取历史消息")
    @ApiImplicitParams({ @ApiImplicitParam(dataType = "String", name = "target", value = "目标用户", required = true),
                         @ApiImplicitParam(paramType = "query", dataType = "String", name = "userId", value = "当前用户",required = true),
                         @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "lastId", value = "上一页最顶部消息id"),
                         @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "pageSize", value = "每页数量") })
    public ModelMap listHistoryMsg(@PathVariable String target,String userId,Long lastId,Integer pageSize) {
        return HttpResult.getResultOKMap(msgService.getHistoryMsg(target, userId, lastId == null ? Long.MAX_VALUE : lastId, pageSize == null ? 10 : pageSize));
    }

    @PostMapping("/offline")
    @ApiOperation(value = "获取离线消息")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "query", dataType = "String", name = "userId", value = "当前用户", required = true),
                         @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "pageSize", value = "每页数量") })
    public ModelMap listOfflineMsg( String userId,Integer pageSize) {
        return HttpResult.getResultOKMap(msgService.getOfflineMsg(userId, pageSize == null ? 10 : pageSize));
    }

    @GetMapping("/offlineCount")
    @ApiOperation(value = "获取离线消息个数")
    @ApiImplicitParam(paramType = "query", dataType = "String", name = "userId", value = "当前用户", required = true)
    public ModelMap offlineCount(String userId) {
        return HttpResult.getResultOKMap(msgService.getOfflineMsgCount(userId));
    }

    @PostMapping("/markOfflineMsg")
    @ApiOperation(value = "客户端提交确定离线消息被成功接收到")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "query", dataType = "String", name = "userId", value = "当前用户", required = true),
                         @ApiImplicitParam(paramType = "query", dataType = "String", name = "ids", value = "被确认已经获取的消息ids，逗号分割提交多个", required = true) })
    public ModelMap markOfflineMsg( String userId,String ids) {
        String[] id = ids.split(",");
        msgService.markOfflineToNormal(userId, id);
        return HttpResult.getResultOKMap();
    }



//    @ApiOperation("用户列表")
//    @GetMapping("/users")
//    public List<User> list(@ApiParam("查看第几页") @RequestParam int pageIndex,
//                           @ApiParam("每页多少条") @RequestParam int pageSize) {
//        List<User> result = new ArrayList<>();
//        result.add(new User("aaa", 50, "北京", "aaa@ccc.com"));
//        result.add(new User("bbb", 21, "广州", "aaa@ddd.com"));
//        return result;
//    }
//
//    @ApiIgnore
//    @DeleteMapping("/users/{id}")
//    public String deleteById(@PathVariable Long id) {
//        return "delete user : " + id;
//    }

}
