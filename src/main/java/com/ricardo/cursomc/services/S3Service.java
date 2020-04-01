package com.ricardo.cursomc.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ricardo.cursomc.services.exceptions.FileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class S3Service {

    private Logger log = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private AmazonS3 s3client;

    @Value("${s3.bucket}")
    private String bucketName;

    public URI uploadFile(MultipartFile multipartFile){
        try {
            String filename = multipartFile.getOriginalFilename();
            InputStream input = multipartFile.getInputStream();
            String contentType = multipartFile.getContentType();
            return uploadFile(input, filename, contentType);
        }catch(Exception e){
            throw new FileException("Erro de IO" + e.getMessage());
        }
    }

    public URI uploadFile(InputStream is, String fileName, String contentType){
        try {
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType(contentType);
            s3client.putObject(bucketName, fileName, is, meta);
            log.info("Finalizando upload ");

            return s3client.getUrl(bucketName, fileName).toURI();
        } catch (URISyntaxException e) {
            throw new FileException("Erro ao converter URL para URI");
        }

    }
}
