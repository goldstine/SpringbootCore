package com.atguigu.cache.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class Employee implements Serializable {
    private Integer id;
    private String lastName;
    private String email;
    private Integer gender;
    private Integer dId;

}
