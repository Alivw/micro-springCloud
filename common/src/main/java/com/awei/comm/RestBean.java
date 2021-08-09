package com.awei.comm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 公共返回对象
 * @Author: Awei
 * @Create: 2021-08-09 14:25
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestBean implements Serializable {
    private String msg;
    private int status;
    private Object obj;
}
