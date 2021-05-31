package cn.coonu.starter.aliyun.sms.config;

import cn.coonu.starter.aliyun.sms.core.AliyunSms;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// 指定类存在这个自动配置类才生效. 这个注解在这里是没必要的, 因为业务核心和自动配置在一个jar,
// 但是一般starter的自动配置和业务核心是分开的项目, 如果业务核心的jar包没有引入, 则这个自动配置类是不生效的;
@ConditionalOnClass(AliyunSms.class)
@EnableConfigurationProperties(AliyunSmsProperties.class) // 将properties类交给spring管理的快捷方法;
public class AliyunSmsAutoConfiguration {

    private final static Logger LOG = LoggerFactory.getLogger(AliyunSmsAutoConfiguration.class);

    @Autowired
    AliyunSmsProperties properties;

    @Bean
    // 此注解作用是为了给使用这个starter的开发者自定义Bean的入口;
    // 仅当spring中没有指定的Bean存在时, 此配置方法才会执行并往spring中注入一个Bean给其管理, 这也是自动配置精髓所在, 如果开发者自定义了, 则用开发者的, 如果没有则用starter的;
    @ConditionalOnMissingBean(AliyunSms.class)
    public AliyunSms aliyunSms() throws Exception {

        AliyunSmsProperties.Sms sms = properties.getSms();

        if (sms.getTemplateParamKeys() != null && sms.getTemplateCodes().size() != sms.getTemplateParamKeys().size()) {
            LOG.error("templateCodes 和 templateParamKeys 数量配置不一致, 未一一对应!");
            throw new RuntimeException("templateCodes 和 templateParamKeys 数量配置不一致, 未一一对应!");
        }

        Config config = new Config()
                .setAccessKeyId(properties.getAccessKeyId())
                .setAccessKeySecret(properties.getAccessKeySecret())
                .setEndpoint(sms.getEndpoint());

        return new AliyunSms(new Client(config), properties);
    }
}
