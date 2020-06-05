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
public class Comment implements Serializable {
    private Integer id;
    private String content;
    private String commentAccount;
    private int articleid;
    private Integer state;
    private String commentTime;
    private String commentNickname;
    private String commentAvatarUrl;
    private String replyNickName;
    private String replyTime;
    private String replyContent;
    private String replyAvatarUrl;
    private String toNickName;
    private String toAvatarUrl;
    private String repplyAccount;
    private String repplyId;

}
