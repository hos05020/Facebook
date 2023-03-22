package com.example.facebook.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.example.facebook.aws.S3Client;
import com.example.facebook.security.Jwt;
import com.example.facebook.utils.MessageUtils;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;

@Configuration
public class ServiceConfig {
    @Bean
    public MessageSourceAccessor messageSourceAccessor(MessageSource messageSource) {
        MessageSourceAccessor messageSourceAccessor = new MessageSourceAccessor(messageSource);
        MessageUtils.setMessageSourceAccessor(messageSourceAccessor);
        return messageSourceAccessor;
    }

    @Bean
    public StringEncryptor jasyptStringEncryptor(JasyptConfigure jasyptConfigure) {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(jasyptConfigure.getPassword());
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations(1000);
        config.setPoolSize(1);
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(config);
        return encryptor;
    }




    @Bean
    public AmazonS3 amazonS3Client(AwsConfigure awsConfigure) {
        return AmazonS3ClientBuilder.standard()
            .withRegion(Regions.fromName(awsConfigure.getRegion()))
            .withCredentials(
                new AWSStaticCredentialsProvider(
                    new BasicAWSCredentials(
                        awsConfigure.getAccessKey(),
                        awsConfigure.getSecretKey())
                )
            )
            .build();
    }

    @Bean
    public Jwt jwt(JwtTokenConfigure jwtTokenConfigure) {
        return new Jwt(jwtTokenConfigure.getIssuer(), jwtTokenConfigure.getClientSecret(), jwtTokenConfigure.getExpirySeconds());
    }


    @Bean
    public S3Client s3Client(AmazonS3 amazonS3, AwsConfigure awsConfigure) {
        return new S3Client(amazonS3, awsConfigure.getUrl(), awsConfigure.getBucketName());
    }
}
