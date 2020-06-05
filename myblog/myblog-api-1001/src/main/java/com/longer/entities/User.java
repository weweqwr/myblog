package com.longer.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {
    private Integer id;
    private String account;
    private String password;
    private String nickname;
    private String name;
    private String province;
    private String city;
    private String district;
    private Integer gender;
    private String avatarUrl;
    private String databaseName;
    private Integer state;
    private String maxim;
    private String introduct;
    private String createTime;
    private int count;
}
