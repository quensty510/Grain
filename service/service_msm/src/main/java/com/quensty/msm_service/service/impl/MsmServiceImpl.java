package com.quensty.msm_service.service.impl;

import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.quensty.msm_service.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Set;

/**
 * @author quensty
 * @version 1.0
 * @date 2021/11/28 14:37
 */
@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(HashMap<String, Object> param, String phone) {
        if (StringUtils.isEmpty(phone)){
            return false;
        }
//        DefaultProfile profile = DefaultProfile.getProfile("default","LTAI5tC13CSVHJeNjtuTtJuZ","cZa4gbRigJQr8F1b5H8O1rAgv5h7Nv");
//        IAcsClient client = new DefaultAcsClient(profile);
//
//        CommonRequest request = new CommonRequest();
////        request.setProtocol(ProtocolType.HTTPS);
//        request.setMethod(MethodType.POST);
//        request.setDomain("dysmsapi.aliyuncs.com");
//        request.setVersion("2017-05-25");
//        request.setAction("SendSms");
//
//        request.putQueryParameter("PhoneNumbers",phone);
//        //短信服务签名名称
//        request.putQueryParameter("SignName", "GULIOnLine");
//        //短信服务模板CODE
//        request.putQueryParameter("TemplateCode", "SMS_228850910");
//        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));
//
//        try {
//            CommonResponse response = client.getCommonResponse(request);
//            return response.getHttpResponse().isSuccess();
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
        String serverIp = "app.cloopen.com";
        //请求端口
        String serverPort = "8883";
        //主账号,登陆云通讯网站后,可在控制台首页看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN
        String accountSId = "8aaf07087d55e4d9017d6b9ac823042b";
        String accountToken = "ef7e8a26740b47a596610954a0ef467f";
        //请使用管理控制台中已创建应用的APPID
        String appId = "8aaf07087d55e4d9017d6b9ac91c0431";
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(serverIp, serverPort);
        sdk.setAccount(accountSId, accountToken);
        sdk.setAppId(appId);
        sdk.setBodyType(BodyType.Type_JSON);
        String to = phone;
        String templateId= "1";
        String[] datas = {(String) param.get("code"),"5"};
        HashMap<String, Object> result = sdk.sendTemplateSMS(to,templateId,datas);
        if("000000".equals(result.get("statusCode"))){
            //正常返回输出data包体信息（map）
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for(String key:keySet){
                Object object = data.get(key);
                System.out.println(key +" = "+object);
            }
            return true;
        }else{
            //异常返回输出错误码和错误信息
            System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
        }

        return false;
    }

    private String read(InputStream is) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = br.readLine()) != null) {
            line = new String(line.getBytes(), "utf-8");
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
}
