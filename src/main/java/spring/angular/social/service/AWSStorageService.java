package spring.angular.social.service;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

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

    @Autowired
    private AmazonS3 s3Client;


    public String save(MultipartFile file){
      File fileObj = convertMultiPartFileToFile(file);
      String fileName = file.getOriginalFilename();
      s3Client.putObject(new com.amazonaws.services.s3.model.PutObjectRequest(myBucket, fileName, fileObj));
      return "https://" + myBucket + ".s3.amazonaws.com/" + fileName;

  }
   public void deleteImage(String imageUrl){
       String key = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
       DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
               .bucket(myBucket)
               .key(key)
               .build();
       AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKey,secretKey);
       S3Client s3Client = S3Client.builder()
               .region(Region.of(region))
               .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
               .build();
       s3Client.deleteObject(deleteObjectRequest);
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