package com.longer.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Article implements Serializable {
    private Integer id;
    private String title;
    private String content;
    private String author;
    private Integer state;
    private String createTime;
    private Integer browse;
    private String textContent;
    private String coverImage;
    private String nickname;
    private String avatarUrl;
    private int count;
    private int commentCount;
}
