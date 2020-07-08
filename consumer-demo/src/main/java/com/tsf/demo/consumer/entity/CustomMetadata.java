package com.tsf.demo.consumer.entity;

/**
 * 用户自定义 Metadata
 */
public class CustomMetadata {

    private String name;
    private String value;

    public CustomMetadata(String name, String value) {
        this.name = name;
        this.value = value;
    }
}