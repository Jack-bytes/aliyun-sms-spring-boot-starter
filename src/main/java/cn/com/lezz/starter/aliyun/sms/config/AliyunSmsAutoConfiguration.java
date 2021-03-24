package cn.com.lezz.starter.aliyun.sms.config;

import cn.com.lezz.starter.aliyun.sms.core.AliyunSms;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass
@ConditionalOnMissingBean
@EnableConfigurationProperties(AliyunSmsProperties.class)
public class AliyunSmsAutoConfiguration {

    @Autowired
    AliyunSmsProperties properties;

    @Bean
    public AliyunSms aliyunSms() throws Exception {

        AliyunSmsProperties.Sms sms = properties.getSms();
        //templateCodes 和 templateParams 需要一一对应
        if (sms.getTemplateParams() != null && sms.getTemplateCodes().size() != sms.getTemplateParams().size()) {
            throw new RuntimeException("templateCodes 和 templateParams 数量配置不一致, 未一一对应!");
        }

        Config config = new Config()
                .setAccessKeyId(properties.getAccessKeyId())
                .setAccessKeySecret(properties.getAccessKeySecret())
                .setEndpoint(sms.getEndpoint());

        return new AliyunSms(new Client(config), properties);
    }
}
