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
public class Reply implements Serializable {
    private Integer id;
    private String content;
    private String repplyAccount;
    private String toRepplyAccount;
    private int commentid;
    private String replyTime;
    private Integer state;
}
