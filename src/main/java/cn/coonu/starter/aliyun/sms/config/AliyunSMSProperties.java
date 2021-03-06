package cn.coonu.starter.aliyun.sms.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "coonu.aliyun")
@Getter
@Setter
public class AliyunSMSProperties {

    /**
     * 阿里云key
     */
    private String accessKeyId;

    /**
     * 阿里云secret
     */
    private String accessKeySecret;

    /**
     * sms API专有参数
     */
    private SMS sms;

    @Getter
    @Setter
    public static class SMS {

        /**
         * 节点URL, 国内除了北京都是这个URL;
         */
        private String endpoint = "dysmsapi.aliyuncs.com";

        /**
         * 签名, 阿里云控制台申请获取, 可以配置多个, 默认使用第一个, 可以用index指定使用哪个, index从0开始;
         *
         * application.yml中的配置方式:
         *
         *   sign-names(此种方式在自动配置的条件配置时可能导致不能正确自动配置问题):
         *     - xx科技
         *     - xx公司
         *     - 阿里云
         *
         * 或者
         *   sign-names: xx科技, xx公司, 阿里云
         *
         * 或者(推荐)
         *   sign-names: [xx科技, xx公司, 阿里云]
         *
         */
        private List<String> signNames;

        /**
         * 短信模板代码, 需要从阿里云控制台获取, 可以配置多个, 默认用第一个, 可以用index指定使用哪个, index从0开始;
         *
         * application.yml中配置方式同上(看上面推荐方式);
         *   template-codes:
         *     - SMS_46546546548
         *     - SMS_64813535551
         *     - SMS_99311111544
         *
         */
        private List<String> templateCodes;

        /**
         * 和模板代码对应的模板信息中参数名(key), 必须和模板代码一一对应, 没有的用[]占位;
         *
         * application.yml中配置方式(看上面推荐方式):
         *   template-param-keys:
         *     - [name, code]
         *     - []
         *     - [name, code]
         */
        private List<List<String>> templateParamKeys;

    }

}
