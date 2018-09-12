package com.warrior.demo.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("tuser")
public class User implements Serializable {

	private static final long serialVersionUID = 668164431643262100L;

	private Long id;
    private String name;
    private Integer age;
    private String email;
}
