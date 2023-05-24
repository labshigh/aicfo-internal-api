package com.labshigh.aicfo;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.internal.AWSS3V4Signer;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;

///**
// * Created by nogah on 2023. 2. 10
// */
public class AwsS3Tests {

    public static void main(String[] args) throws Exception {
        AmazonS3 s3Client;
        String accessKey = "AKIA2VA4NRAZCXV6ZD6C"; // 액세스키
        String secretkey = "up+NjbL6qssoyNzdbSDqWJP6jlVEz2d825g7kWW/"; // 스크릿 엑세스 키

        Regions clientRegion = Regions.AP_SOUTHEAST_1; // 시드니
        String bucket = "aicfo-bucket"; // 버킷 명
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretkey);
        s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
          .withRegion(clientRegion).build();


        File file = new File("/Users/nogah/1.png");
        PutObjectRequest putObjectRequest = new PutObjectRequest( bucket, "/member/123.png", file);
        s3Client.putObject(putObjectRequest);
        System.out.println(String.format("[%s] upload complete", putObjectRequest.getKey()));

    }

    static private AWSS3V4Signer instance = null;

    public static AWSS3V4Signer getInstance() {
        if (instance == null) {
            return new AWSS3V4Signer();
        } else {
            return instance;
        }
    }
}
