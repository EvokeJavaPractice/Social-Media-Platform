package spring.angular.social.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class AWSStorageService {

    @Value("${aws.s3.bucket}")
    private String myBucket;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.accessKey}")
    private String accessKey;

    private AmazonS3 s3Client;

    @Autowired
    public AWSStorageService(@Value("${aws.accessKey}") String accessKey,
                             @Value("${aws.secretKey}") String secretKey,
                             @Value("${aws.region}") String region) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.region = region;
        this.s3Client = buildS3Client();
    }

    private AmazonS3 buildS3Client() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

    public String save(MultipartFile file) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = file.getOriginalFilename();
        s3Client.putObject(myBucket, fileName, fileObj);
        return "https://" + myBucket + ".s3.amazonaws.com/" + fileName;
    }

    public void deleteImage(String imageUrl) {
        String key = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        s3Client.deleteObject(new DeleteObjectRequest(myBucket, key));
    }

    public File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (OutputStream os = new FileOutputStream(convertedFile)) {
            os.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertedFile;
    }
}

//
//@Service
//public class AWSStorageService {
//
//    @Value("${aws.s3.bucket}")
//    private String myBucket;
//
//    @Autowired
//    private AmazonS3 s3Client;
//
//    public String save(MultipartFile file) {
//        File fileObj = convertMultiPartFileToFile(file);
//        String fileName = file.getOriginalFilename();
//        s3Client.putObject(new PutObjectRequest(myBucket, fileName, fileObj));
//        return "https://" + myBucket + ".s3.amazonaws.com/" + fileName;
//    }
//
//    public void deleteImage(String imageUrl) {
//        String key = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
//        s3Client.deleteObject(myBucket, key);
//    }
//
//    public File convertMultiPartFileToFile(MultipartFile file) {
//        File convertedFile = new File(file.getOriginalFilename());
//        try (OutputStream os = new FileOutputStream(convertedFile)) {
//            os.write(file.getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return convertedFile;
//    }
//}
//
