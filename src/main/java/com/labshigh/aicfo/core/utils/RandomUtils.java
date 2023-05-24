package com.labshigh.aicfo.core.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomUtils {

  public static String randomCode(int targetStringLength) {
    int leftLimit = 97; // letter 'a'
    int rightLimit = 122; // letter 'z'
    Random random = new Random();
    StringBuilder buffer = new StringBuilder(targetStringLength);
    for (int i = 0; i < targetStringLength; i++) {
      int randomLimitedInt = leftLimit + (int)
        (random.nextFloat() * (rightLimit - leftLimit + 1));
      buffer.append((char) randomLimitedInt);
    }
    return buffer.toString();
  }

  public static String randomReferrerCode(int targetStringLength) {
    char[] charaters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9'};

    StringBuffer sb = new StringBuffer();

    Random rn = new Random();

    for( int i = 0 ; i < targetStringLength ; i++ ){

      sb.append( charaters[ rn.nextInt( charaters.length ) ] );

    }

    return sb.toString();
  }
  public static String randomPassword(int targetStringLength) {
    char[] charaters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9','!','@','#','$','%','^','&','*'};

    StringBuffer sb = new StringBuffer();

    Random rn = new Random();

    for( int i = 0 ; i < targetStringLength ; i++ ){

      sb.append( charaters[ rn.nextInt( charaters.length ) ] );

    }

    return sb.toString();
  }
  public static String randomCodeNum(int targetStringLength) {
    Random random = new Random();		//랜덤 함수 선언
    int createNum = 0;  			      //1자리 난수
    String ranNum = ""; 			      //1자리 난수 형변환 변수
    String resultNum = "";  		    //결과 난수

    for (int i=0; i<targetStringLength; i++) {

      createNum = random.nextInt(9);		//0부터 9까지 올 수 있는 1자리 난수 생성
      ranNum =  Integer.toString(createNum);  //1자리 난수를 String으로 형변환
      resultNum += ranNum;			              //생성된 난수(문자열)을 원하는 수(letter)만큼 더하며 나열
    }
    return resultNum.toString();
  }

}