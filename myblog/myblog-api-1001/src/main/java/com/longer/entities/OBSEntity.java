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
public class OBSEntity implements Serializable {
    String endPoint = "obs.cn-south-1.myhuaweicloud.com";
    String ak = "BLT3FQKF02NW5BA0PYWE";
    String sk = "kCFwHRccrbAmouJFERKYRv3LsON9zqDJTqmss2SF";
}
