package com.labshigh.aicfo.internal.api.config;


import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.extern.log4j.Log4j2;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
  @Value("${jasypt.props.file-path}")
  private String propsPath;

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

    String result = null;
    try {

      result = String.join("", Files.readAllLines(Paths.get(propsPath)));

//      result = new BufferedReader(new InputStreamReader(resource.getInputStream(),
//          StandardCharsets.UTF_8)).lines().collect(Collectors.joining(""));

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return result;
  }

}


