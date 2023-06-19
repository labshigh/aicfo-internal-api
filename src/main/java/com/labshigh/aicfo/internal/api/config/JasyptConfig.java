package com.labshigh.aicfo.internal.api.config;


import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableEncryptableProperties
@Log4j2
public class JasyptConfig {

  @Value("${jasypt.encryptor.algorithm}")
  private String algorithm;
  @Value("${jasypt.encryptor.pool-size}")
  private int poolSize;
  @Value("${jasypt.encryptor.stringOutputType}")
  private String stringOutputType;
  @Value("${jasypt.encryptor.key-obtention-iterations}")
  private int keyObtentionIterations;
  
  @Bean
  public StringEncryptor jasyptStringEncryptor() {
    PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
    encryptor.setPoolSize(poolSize);
    encryptor.setAlgorithm(algorithm);
    encryptor.setPassword(getJasyptPassword());
    encryptor.setStringOutputType(stringOutputType);
    encryptor.setKeyObtentionIterations(keyObtentionIterations);

    return encryptor;
  }

  private String getJasyptPassword() {

    ClassPathResource resource = new ClassPathResource("jasypt.props");
    String result = null;
    try {
      result = new BufferedReader(new InputStreamReader(resource.getInputStream(),
          StandardCharsets.UTF_8)).lines().collect(Collectors.joining(""));


    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return result;
  }

}


