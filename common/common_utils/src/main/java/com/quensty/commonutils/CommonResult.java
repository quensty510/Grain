package com.quensty.commonutils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回结果
 * @version 1.0
 * @author quensty
 * @date 2021/10/29 16:01
 */
@Data
public class CommonResult {
    /**
     * 私有化构造方法
     */
     private CommonResult(){}
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    public static CommonResult success(){
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(ResultCode.SUCCESS);
        commonResult.setSuccess(true);
        commonResult.setMessage("成功");
        return commonResult;
    }
    public static CommonResult failed(){
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(ResultCode.FAILED);
        commonResult.setSuccess(false);
        commonResult.setMessage("失败");
        return commonResult;
    }
    public static CommonResult error(){
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(ResultCode.ERROR);
        commonResult.setSuccess(false);
        commonResult.setMessage("服务器出错");
        return commonResult;
    }
    public CommonResult success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public CommonResult message(String message){
        this.setMessage(message);
        return this;
    }

    public CommonResult code(Integer code){
        this.setCode(code);
        return this;
    }

    public CommonResult data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public CommonResult data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
