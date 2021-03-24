package cn.com.lezz.starter.aliyun.sms.core;

import cn.com.lezz.starter.aliyun.sms.config.AliyunSmsProperties;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;

import java.util.List;

public class AliyunSms {

    private Client client;

    private AliyunSmsProperties properties;

    public AliyunSms(Client client, AliyunSmsProperties properties){
        this.client = client;
        this.properties = properties;
    }

    //多个号码, 一个签名,
    public boolean sendSms(String phoneNumbers, int signNameIndex, int codeIndex, List<String> params) {

        SendSmsRequest request = new SendSmsRequest().setPhoneNumbers("").setSignName("").setTemplateCode("");
//        if () {
//
//        }
        return false;
    }
}
