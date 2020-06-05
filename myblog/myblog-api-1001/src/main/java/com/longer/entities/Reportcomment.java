package com.longer.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Reportcomment {
    private int id;
    private int commentId;
    private String reportAccount;
    private String reportContent;
    private int state;
    private String createTime;
    private String commentContent;
    private String commentAccount;
    private int count;
}
