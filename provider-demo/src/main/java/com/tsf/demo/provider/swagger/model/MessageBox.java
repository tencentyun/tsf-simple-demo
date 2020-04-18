package com.tsf.demo.provider.swagger.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "消息投递箱")
public class MessageBox {

    @ApiModelProperty(value = "默认失效天数", required = false, position = 0)
    private int expiredDays;

    @ApiModelProperty(value = "最大失效天数", required = false, position = 1)
    private Integer maxExpiredDays;

    @ApiModelProperty(value = "容量大小", required = false, position = 2)
    private Float capacity;

    @ApiModelProperty(value = "最大容量大小", required = false, position = 3)
    private float maxCapacity;

    @ApiModelProperty(value = "接受的信息数量", required = false, position = 4)
    private Double size;

    @ApiModelProperty(value = "最大接受的信息数量", required = false, position = 5)
    private double maxSize;

    @ApiModelProperty(value = "消息(循环测试嵌套对象)", required = false, position = 6)
    private MessageModel messageModel;

    public int getExpiredDays() {
        return expiredDays;
    }

    public void setExpiredDays(int expiredDays) {
        this.expiredDays = expiredDays;
    }

    public Integer getMaxExpiredDays() {
        return maxExpiredDays;
    }

    public void setMaxExpiredDays(Integer maxExpiredDays) {
        this.maxExpiredDays = maxExpiredDays;
    }

    public Float getCapacity() {
        return capacity;
    }

    public void setCapacity(Float capacity) {
        this.capacity = capacity;
    }

    public float getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(float maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public double getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(double maxSize) {
        this.maxSize = maxSize;
    }

    public MessageModel getMessageModel() {
        return messageModel;
    }

    public void setMessageModel(MessageModel messageModel) {
        this.messageModel = messageModel;
    }
}
