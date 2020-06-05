package com.longer.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Reportreply {
    private int id;
    private int replyId;
    private String reportAccount;
    private String reportContent;
    private int state;
    private String createTime;
    private String replyContent;
    private String repplyAccount;
    private int count;
}
