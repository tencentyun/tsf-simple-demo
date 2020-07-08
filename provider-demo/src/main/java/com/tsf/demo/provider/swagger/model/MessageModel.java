package com.tsf.demo.provider.swagger.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "消息", value = "messageModel")
public class MessageModel {

    @ApiModelProperty(name = "id", value = "消息ID", required = true, notes = "消息ID notes", position = 0)
    private String msgId;

    @ApiModelProperty(value = "消息内容", required = false, position = 1)
    private String msgContent;

    @ApiModelProperty(value = "消息发送者", required = true, position = 3)
    private MessageUser sendUser;

    @ApiModelProperty(value = "消息接收者", required = true, position = 2)
    private List<MessageUser> receiveUsers;

    @ApiModelProperty(value = "消息发送时间", required = true, position = 4)
    private long sendTime;

    @ApiModelProperty(value = "消息投递箱", required = false, position = 5)
    private MessageBox messageBox;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public MessageUser getSendUser() {
        return sendUser;
    }

    public void setSendUser(MessageUser sendUser) {
        this.sendUser = sendUser;
    }

    public List<MessageUser> getReceiveUsers() {
        return receiveUsers;
    }

    public void setReceiveUsers(List<MessageUser> receiveUsers) {
        this.receiveUsers = receiveUsers;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public MessageBox getMessageBox() {
        return messageBox;
    }

    public void setMessageBox(MessageBox messageBox) {
        this.messageBox = messageBox;
    }
}
