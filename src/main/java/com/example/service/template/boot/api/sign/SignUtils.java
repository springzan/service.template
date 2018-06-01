package com.example.service.template.boot.api.sign;

import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ExecutionError;

import com.alibaba.fastjson.JSON;
import com.example.service.template.boot.api.APIConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

public class SignUtils {
    private static final Logger logger = LoggerFactory.getLogger(SignUtils.class);

    public static final int SIGN_EXPIRE_SECONDS = 10;
    static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final Map<String, String> SIGN_KEY_MAP = ImmutableMap.<String, String>builder()
            .put("v1.0", "4S44-SS-4446544")
            .build();

    /**
     * 从请求中解析AuthorizationInfo
     */
    public static AuthorizationInfo parseAuthInfo(HttpServletRequest request) throws SignException {
        String authorization = Optional.fromNullable(request.getHeader(APIConstants.Headers.EBT_Authorization))
                .or(Optional.fromNullable(request.getHeader(APIConstants.Headers.Authorization)))
                .orNull();
        AuthorizationInfo authInfo = null;
        try {
            if (authorization != null) {
                authInfo = JSON.parseObject(authorization, AuthorizationInfo.class);
            }
        } catch (ExecutionError e) {
            logger.error("parseAuthInfo {}", authorization, e);
        }
        return authInfo;
    }

    /**
     * 验证签名是否过期;
     *
     * @return 如果过期, 返回true
     */
    public static boolean checkTimeout(AuthorizationInfo authInfo) {
        if (authInfo == null) {
            return false;
        }
        try {
            long signTimestamp = authInfo.getSignTimestamp();
            long tenSecondAgo = LocalDateTime.now().minusSeconds(SIGN_EXPIRE_SECONDS).toEpochSecond(ZoneOffset.ofHours(8)) * 1000;
            return signTimestamp < tenSecondAgo;
        } catch (Exception e) {
            logger.error("checkTimeout", e);
        }
        return false;
    }


    /**
     * 验证签名是否合法;
     *
     * @return 如果合法, 返回true
     */
    public static boolean checkSign(AuthorizationInfo authInfo) {
        if (authInfo == null) {
            return false;
        }
        String signVersion = authInfo.getSignVersion();
        String originKey = getOriginKeyBySignVersion(signVersion);
        if (originKey == null) {
            return false;
        }
        String sign = authInfo.getSign();
        String deviceId = authInfo.getDeviceId();
        long signTimestamp = authInfo.getSignTimestamp();
        String requestKey = md5(deviceId + originKey);
        String serverSign = md5(deviceId + signTimestamp + requestKey);
        return serverSign.equals(sign);
    }

    private static String md5(String string) {
        return DigestUtils.md5DigestAsHex(string.getBytes(Charsets.UTF_8));
    }

    private static String getOriginKeyBySignVersion(String signVersion) {
        return SIGN_KEY_MAP.get(signVersion);
    }

    public static void main(String[] args) {
        printOriginKey();
    }

    private static void printOriginKey() {
        String androidVersion = "xhybt-iOS-1.0.0";
        String iosVersion = "xhybt-Android-1.0.0";
        String androidKey = genRandomOriginKey(androidVersion);
        String iosKey = genRandomOriginKey(iosVersion);
        System.out.println(androidVersion + "|" + androidKey);
        System.out.println(iosVersion + "|" + iosKey);
        System.out.println(String.format(".put(\"%s\", \"%s\")", androidVersion, androidKey));
        System.out.println(String.format(".put(\"%s\", \"%s\")", iosVersion, iosKey));
    }

    public static String genRandomOriginKey(String version) {
        Random random = new Random();
        int ran = random
                .nextInt(10000);
        char[] chars = (version + ran).toCharArray();
        for (int i = 0; i < chars.length; i++) {
            ran = random.nextInt(chars.length);
            chars[i] = chars[ran];
        }
        return new String(chars);
    }

    public static String sign(String deviceId, String originKey, long signTime) {
        String requestKey = md5(deviceId + originKey);
        return md5(deviceId + signTime + requestKey);
    }
}
