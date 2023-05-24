package com.labshigh.aicfo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * PBKDF2 salted password hashing.
 * Author: havoc AT defuse.ca
 * www: http://crackstation.net/hashing-security.htm
 */
public class PasswordEncoderTest {

  public static void main(String[] args) throws Exception {


    // 패스워드 인코더
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 패스워드
    String password         = "choiys7440";
    System.out.println("패스워드: "+ password);

    // 패스워드 인코딩
    String encodePassword   = passwordEncoder.encode(password);
    System.out.println("인코딩 패스워드: "+ encodePassword);

    // 패스워드 확인
    boolean isMatche        = passwordEncoder.matches(password, encodePassword);
    System.out.println("인코딩 확인: "+ isMatche);
  }
}