package com.tay.securitylearn.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author huohuo
 * @createDate 2023-03-25
 * @email gang95@email.cn
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsernamePasswordAuthenticationRequest {
    private String username;
    private String password;

}
