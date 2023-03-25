package com.tay.securitylearn.jwt;

import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

/**
 * @author huohuo
 * @createDate 2023-03-25
 * @email gang95@email.cn
 */
@ConfigurationProperties(prefix = "application.jwt")
@Setter
@Getter
@NoArgsConstructor
public class JwtConfig {
    private String secretKey;

    private String tokenPrefix;

    private Integer tokenExpirationAfterDays;


    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }


}
