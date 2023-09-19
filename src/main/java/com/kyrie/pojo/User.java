package com.kyrie.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("test_user")
public class User {

    @TableId
    private Integer id;
    private String name;
    private String company;
    private String addr;
    private String phone;
}
