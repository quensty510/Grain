package com.quensty.ucenter.controller;

import com.google.gson.Gson;
import com.quensty.commonutils.JwtUtils;
import com.quensty.serviceBase.handler.GuLiException;
import com.quensty.ucenter.entity.UcenterMember;
import com.quensty.ucenter.service.MemberService;
import com.quensty.ucenter.utils.ConstantWxUtils;
import com.quensty.ucenter.utils.HttpClientUtils;
import com.sun.xml.internal.fastinfoset.Encoder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/12/5 14:59
 */
//@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx")
@Api(tags = "微信登录接口")
public class WxApiController {

    @Autowired
    MemberService memberService;

    @ApiOperation("生成微信登录二维码")
    @GetMapping("login")
    public String getWxQRCode(){
        // 微信开放平台授权baseUrl %s 占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //对redirect_url进行URLEncoder编码
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, Encoder.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置%s值
        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "quensty");

        //重定向到微信请求地址
        return "redirect:" + url;
    }

    @ApiOperation("获取扫描者信息，添加数据")
    @GetMapping("/callback")
    public String callback(String code, String state) {

        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        String accessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                ConstantWxUtils.WX_OPEN_APP_SECRET,
                code);

        //使用httpClient请求拼接好的地址，得到access_token和openId
        try {
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            //将accessTokenInfo转换成map，获取数据
            Gson gson = new Gson();
            HashMap<String,String> map = gson.fromJson(accessTokenInfo, HashMap.class);
            String accessToken = map.get("access_token");
            String openid = map.get("openid");

            //将扫描人信息添加到数据库
            //判断数据表里是否有相同微信信息
            UcenterMember ucenterMember = memberService.getMemberByOpenId(openid);
            if (ucenterMember == null){
                //使用access_token和openid请求微信提供的地址，获取到扫描者的信息
                //访问微信资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
                String userInfo = HttpClientUtils.get(userInfoUrl);
                HashMap<String,String> userMap = gson.fromJson(userInfo, HashMap.class);
                String nickName = userMap.get("nickname");
                String headImgUrl = userMap.get("headimgurl");

                ucenterMember = new UcenterMember();
                ucenterMember.setNickname(nickName);
                ucenterMember.setOpenid(openid);
                ucenterMember.setAvatar(headImgUrl);
                memberService.save(ucenterMember);

            }
            // 生成jwt
            String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
            return "redirect:http://localhost:3000?token=" + jwtToken;
        } catch (Exception e) {
            throw new GuLiException(20001,"登录失败");
        }
    }
}
