package com.quensty.ucenter.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/11/28 18:48
 */
@Data
@ApiModel(value="登录对象", description="登录对象")
public class LoginVo {
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;
}
