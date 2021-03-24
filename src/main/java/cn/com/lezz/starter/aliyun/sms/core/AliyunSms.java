package cn.com.lezz.starter.aliyun.sms.core;

import cn.com.lezz.starter.aliyun.sms.config.AliyunSmsProperties;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AliyunSms {

    private final static Logger LOG = LoggerFactory.getLogger(AliyunSms.class);

    private final static Gson GSON = new Gson();

    private final Client client;

    private final AliyunSmsProperties properties;

    public AliyunSms(Client client, AliyunSmsProperties properties) {
        this.client = client;
        this.properties = properties;
    }

    /**
     * 使用配置中第一个signName和templateCode发送短信, 第一个即是index为0;
     *
     * @param phoneNumbers        手机号码, 多个用","分隔;
     * @param templateParamValues 和模板信息中参数名对应的参数值, 需要和配置的参数名一一对应, 按顺序加入list;
     * @return 是否成功发送
     */
    public boolean sendSms(String phoneNumbers, List<String> templateParamValues) {
        return sendSms(phoneNumbers, 0, 0, templateParamValues);
    }

    /**
     * 发送短信
     *
     * @param phoneNumbers        手机号码, 多个用","分隔;
     * @param signNameIndex       签名下标index
     * @param templateCodeIndex   模板代码下标index
     * @param templateParamValues 和模板信息中参数名对应的参数值, 需要和配置的参数名一一对应, 按顺序加入list;
     * @return 是否成功发送
     */
    public boolean sendSms(String phoneNumbers, int signNameIndex, int templateCodeIndex, List<String> templateParamValues) {

        SendSmsRequest request = new SendSmsRequest()
                .setPhoneNumbers(phoneNumbers)
                .setSignName(properties.getSms().getSignNames().get(signNameIndex))
                .setTemplateCode(properties.getSms().getTemplateCodes().get(templateCodeIndex));

        List<List<String>> templateParamKeys = properties.getSms().getTemplateParamKeys();

        Map<String, String> templateParam = new HashMap<>();
        if (templateParamKeys != null && templateParamKeys.get(templateCodeIndex).size() != 0) {

            List<String> keys = templateParamKeys.get(templateCodeIndex);
            if (templateParamValues == null || keys.size() > templateParamValues.size()) {
                LOG.error("短信发送失败, 模板参数名与模板参数值数量不一致!");
                return false;
            }
            //处理params的key和value
            for (int i = 0; i < keys.size(); i++) {
                templateParam.put(keys.get(i), templateParamValues.get(i));
            }
            request.setTemplateParam(GSON.toJson(templateParam));
        }

        SendSmsResponse response;
        try {
            response = client.sendSms(request);
        } catch (Exception e) {
            LOG.error("短信发送失败, 未知错误, 手机号: {}!", phoneNumbers);//测试下什么情况下会抛异常??????????????????????????????????????
            e.printStackTrace();
            return false;
        }

        String code = response.getBody().getCode();
        if (!"OK".equals(code)) {
            LOG.error("短信发送失败, 手机号: {}, 错误码: {}, 错误信息请参照: https://help.aliyun.com/document_detail/101346.html", phoneNumbers, code);
            return false;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("短信发送成功, 手机号码: {}, 发送内容: {}", phoneNumbers, templateParam.size() == 0 ? "无" : GSON.toJson(templateParam));
        }
        return true;
    }

}
