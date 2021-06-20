package cn.coonu.starter.aliyun.sms.utils;

public class AliyunSMSUtil {

    /**
     * 生成6位随机验证码
     *
     * @return 6位数字验证码
     */
    public static String generateCode() {
        int code = (int) (Math.random() * 1000000);
        if (code < 100000) {
            return generateCode();
        }
        return Integer.toString(code);
    }

}
