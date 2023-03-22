package com.cjx.utils;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SmsUtil {
//    @Value("${sms.accesskeyId}")
    @Value("${sms.accesskeyId}")
    private String accesskeyId;//key
    @Value("${sms.secret}")
    private String secret;  // secret
    @Value("${sms.signName}")
    private String signName; //短信签名
    @Value("${sms.templateCode}")
    private String templateCode; //短信模板
    @Value("${sms.regionId}")
    private String regionId;     //短信服务器区域
    public void sendMsg(String phone, String code) {
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accesskeyId, secret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", regionId);
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            log.info("阿里云短信响应信息: " + response.getData());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("提示: 短信发送失败!");
        }
    }
}
