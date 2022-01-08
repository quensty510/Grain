package com.quensty.serviceBase.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/10/29 20:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuLiException extends RuntimeException{
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 异常信息
     */
    private String message;
}
