package com.tsf.demo.provider.swagger.controller;

import com.google.common.collect.Lists;
import com.tsf.demo.provider.swagger.model.MessageBox;
import com.tsf.demo.provider.swagger.model.MessageModel;
import com.tsf.demo.provider.swagger.model.MessageUser;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("/swagger")
@Api(description = "swagger 测试", value = "swaggerValue1", tags = "swaggerTag1", basePath = "/swagger")
public class SwaggerApiController {

    @RequestMapping(value = "/swagger/findMessages",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(httpMethod = "POST",
            value = "根据任务ID查询任务列表",
            notes = "根据任务ID查询任务列表Note",
            protocols = "http,https",
            produces="application/json;charset=utf-8")
    public List<MessageModel> findMessages(@RequestBody
         @ApiParam(name = "msgIds", value = "消息ID列表") List<String> msgIds) {
        List<MessageModel> messageModels = new ArrayList<>();
        MessageModel messageModel = new MessageModel();
        messageModel.setMsgContent("test1");
        messageModel.setMsgId("1");
        messageModel.setSendTime(System.currentTimeMillis());
        MessageUser messageSender = new MessageUser();
        messageSender.setEmail("abc@xxxx.com");
        messageSender.setName("特朗普");
        messageSender.setOfficeAddress("华盛顿白宫");
        messageSender.setPhoneNum("911911911");
        messageModel.setSendUser(messageSender);
        MessageUser messageReceiver = new MessageUser();
        messageReceiver.setEmail("abc@xxxx.com");
        messageReceiver.setName("拜登");
        messageReceiver.setOfficeAddress("华盛顿白宫");
        messageReceiver.setPhoneNum("911911911");
        messageModel.setReceiveUsers(Lists.newArrayList(messageReceiver));
        messageModels.add(messageModel);
        return messageModels;
    }

    //虽然这些@ExampleProperty和@Example属性已经在Swagger中实现，但Springfox还没有支持它们。问题仍然存在：
    //https://github.com/springfox/springfox/issues/853
    //https://github.com/springfox/springfox/issues/1536

    @ApiOperation(value = "获取消息内容",httpMethod = "GET", notes = "获取消息内容Note" )
    @ApiResponse(code = 200, message = "abcdef", response = String.class)
    @RequestMapping(value = "/swagger/getMessageContent")
    public String getMessageContent(@RequestParam(name = "id")
        @ApiParam(value = "消息ID", name = "id") String msgId) {
        return "abcdefg";
    }

    @ApiOperation(value = "获取消息详情",httpMethod = "GET", notes = "获取消息内容Note" )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "abcdef", response = MessageModel.class)})
    @RequestMapping(value = "/swagger/getMessageDetail",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageModel getMessageDetail(@RequestParam(name = "id")
        @ApiParam(value = "消息ID", name = "id") String msgId) {
        MessageModel messageModel = new MessageModel();
        messageModel.setMsgContent("test1");
        messageModel.setMsgId("1");
        messageModel.setSendTime(System.currentTimeMillis());
        MessageUser messageSender = new MessageUser();
        messageSender.setEmail("abc@xxxx.com");
        messageSender.setName("特朗普");
        messageSender.setOfficeAddress("华盛顿白宫");
        messageSender.setPhoneNum("911911911");
        messageModel.setSendUser(messageSender);
        MessageUser messageReceiver = new MessageUser();
        messageReceiver.setEmail("abc@xxxx.com");
        messageReceiver.setName("拜登");
        messageReceiver.setOfficeAddress("华盛顿白宫");
        messageReceiver.setPhoneNum("911911911");
        messageModel.setReceiveUsers(Lists.newArrayList(messageReceiver));
        return messageModel;
    }

    @ApiOperation(value = "获取投递箱详情",httpMethod = "GET", notes = "" )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取投递箱成功", response = MessageBox.class)})
    @RequestMapping(value = "/swagger/getMessageBox",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageBox getMessageBox(@RequestParam @ApiParam(required = true, value = "投递箱ID") String boxId,
                                    @RequestParam @ApiParam(name = "sizeLimit",defaultValue = "10",value = "投递箱最大投递数" )int maxSizeLimit) {
        return new MessageBox();
    }

    @ApiOperation(value = "获取投递箱详情V2",httpMethod = "POST", notes = "" )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取投递箱成功", response = MessageBox.class)})
    @RequestMapping(value = "/swagger/v2/getMessageBox",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageBox getMessageBoxV2(@RequestBody
        @ApiParam(required = true, name = "messageBox", value = "投递箱信息") MessageBox messageBox) {
        return new MessageBox();
    }

    @ApiOperation(value = "获取投递箱详情V3",httpMethod = "POST", notes = "" )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "获取投递箱成功", response = Map.class)})
    @RequestMapping(value = "/swagger/v3/getMessageBox",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, Object> queryMessageBoxV3(@RequestBody
       @ApiParam(required = true, name = "messageBox", value = "投递箱信息") MessageBox messageBox) {

        return new HashMap<>();
    }


    @ApiOperation(value = "获取投递箱地址",httpMethod = "GET", notes = "" )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "投递箱地址", response = String.class)})
    @RequestMapping(value = "/swagger/getMessageBoxAddress",produces = MediaType.TEXT_PLAIN_VALUE)
    public String queryMessageBoxAddress(@RequestParam(name = "boxId")
        @ApiParam(value = "投递箱ID", name = "boxId", defaultValue = "box-qvp9htm5", required = true) String id) {
        return "华盛顿白宫";

    }



}
