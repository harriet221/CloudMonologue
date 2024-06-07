package com.ysj.cloudmonologue.global.app;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Getter
    public static String jwtSecretKey;

    @Value("${custom.jwt.secretKey}")
    public void setJwtSecretKey(String jwtSecretKey) {
        AppConfig.jwtSecretKey = jwtSecretKey;
    }

    private static String activeProfile;

    @Value("${spring.profiles.active}")
    public void setActiveProfile(String activeProfile) {
        AppConfig.activeProfile = activeProfile;
    }

    public static boolean isProd() {
        return activeProfile.equals("prod");
    }

    @Getter
    private static long accessTokenExpirationSec;

    @Value("${custom.accessToken.expirationSec}")
    public void setJwtSecretKey(long accessTokenExpirationSec) {
        AppConfig.accessTokenExpirationSec = accessTokenExpirationSec;
    }

    @Getter
    private static String siteFrontUrl;

    @Value("${custom.site.frontUrl}")
    public void setSiteFrontUrl(String siteFrontUrl) {
        AppConfig.siteFrontUrl = siteFrontUrl;
    }

    @Getter
    private static String siteBackUrl;

    @Value("${custom.site.backUrl}")
    public void setSiteBackUrl(String siteBackUrl) {
        AppConfig.siteBackUrl = siteBackUrl;
    }

    @Getter
    private static String siteCookieDomain;

    @Value("${custom.site.cookieDomain}")
    public void setSiteCookieDomain(String siteCookieDomain) {
        AppConfig.siteCookieDomain = siteCookieDomain;
    }

    @Getter
    public static String genFileDirPath;

    @Value("${custom.genFile.dirPath}")
    public void setGenFileDirPath(String genFileDirPath) {
        AppConfig.genFileDirPath = genFileDirPath;
    }

    @Getter
    public static ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        AppConfig.objectMapper = objectMapper;
    }

}