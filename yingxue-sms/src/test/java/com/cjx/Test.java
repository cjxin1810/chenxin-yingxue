package com.cjx;

import com.cjx.utils.MD5Utils;
import com.cjx.utils.SmsUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = SmsApplication.class)
@RunWith(SpringRunner.class)
public class Test {
    @Autowired
    private SmsUtil smsUtil;
    @org.junit.Test
    public void test() {
        System.out.println(smsUtil);
        smsUtil.sendMsg("18364062019","100868");
    }
    @org.junit.Test
    public void test2() {
        /*String salt = MD5Utils.getSalt();
        String password = MD5Utils.generate("chenxin", salt);
        System.out.println(password);
        boolean chenxin = MD5Utils.verify("chenxin", password);
        System.out.println(chenxin);*/
        String salt = "chenxin";
        String s = DigestUtils.md5Hex("123456" + salt);
        System.out.println(s);
    }
}
