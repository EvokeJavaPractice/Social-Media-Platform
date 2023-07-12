package spring.angular.social.service;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3control.model.S3ObjectMetadata;
import spring.angular.social.entity.Profile;
import spring.angular.social.entity.ProfileImage;
import spring.angular.social.exception.ProfileNotFoundException;
import spring.angular.social.repository.ImageUploadRepository;
import spring.angular.social.repository.ProfileRepository;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.*;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;


@Service
public class ProfileService {

	
	@Value("${aws.s3.bucket}")
	private String myBucket;
	@Value("${aws.region}")
	private String region;
	@Value("$aws.secretKey")
	private String secretKey;
	@Value("$aws.accessKey")
	private String accessKey;
	
   @Autowired
   private AmazonS3 s3Client;
   
   @Autowired
   private ImageUploadRepository imageRepository;

	private final ProfileRepository profileRepository;

	public ProfileService(ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}

	public Profile getProfile(Long profileId) {
		return profileRepository.findById(profileId)
				.orElseThrow(() -> new ProfileNotFoundException("Profile not found with ID: " + profileId));
	}

	public Profile createProfile(Profile profile) {
		return profileRepository.save(profile);
	}

	public Profile updateProfile(Long profileId, Profile updatedProfile) {
		Profile existingProfile = profileRepository.findById(profileId)
				.orElseThrow(() -> new ProfileNotFoundException("Profile not found with ID: " + profileId));

		existingProfile.setFullName(updatedProfile.getFullName());
		existingProfile.setBio(updatedProfile.getBio());

		return profileRepository.save(existingProfile);
	}

	public void deleteProfile(Long profileId) {
		Profile profile = profileRepository.findById(profileId)
				.orElseThrow(() -> new ProfileNotFoundException("Profile not found with ID: " + profileId));

		profileRepository.delete(profile);
	}

	public Profile getProfileByUserId(Long userId) {
		// Assuming you have a repository for the Profile entity, e.g., ProfileRepository
		Optional<Profile> optionalProfile = profileRepository.findByUserId(userId);

		if (optionalProfile.isPresent()) {
			return optionalProfile.get();
		} else {
			throw new RuntimeException("Profile not found for user ID: " + userId);
		}
	}

	
	
	public String uploadImage(Long profileId,MultipartFile file) {
		 File fileObj = convertMultiPartFileToFile(file);
		    String fileName = file.getOriginalFilename();

		   s3Client.putObject(new com.amazonaws.services.s3.model.PutObjectRequest(myBucket, fileName, fileObj));

			Long senderId = profileId;
			String imageUrl = "https://" + myBucket + ".s3.amazonaws.com/" + fileName; 
			ProfileImage profileImage = new ProfileImage();
			profileImage.setSenderId(senderId);
			profileImage.setImageUrl(imageUrl);

			imageRepository.save(profileImage);

		return "Image Uploaded In S3 Bucket  :"+imageUrl;
		
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
	
	public String getProfileImageUrl(Long senderId) {
		Optional<ProfileImage> imageUrl = imageRepository.findBySenderId(senderId);
		return imageUrl.get().getImageUrl();
		
	}
	public void deleteImage(Long profileId) {
	    System.out.println("entered here " + profileId);
	    
	    Optional<ProfileImage> imageUrlOptional = imageRepository.findBySenderId(profileId);
	    
	    if (imageUrlOptional.isPresent()) {
	        String imageUrl = imageUrlOptional.get().getImageUrl();
	      
	        Long imageId=imageUrlOptional.get().getId();
	       
	        String key = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
	        System.out.println("this is the key: " + key);
	        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
	                .bucket(myBucket)
	                .key(key)
	                .build();
	        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(secretKey, accessKey);
	        S3Client s3Client = S3Client.builder()
	                .region(Region.AP_SOUTH_1)
	                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
	                .build();
	        s3Client.deleteObject(deleteObjectRequest);
	        imageRepository.deleteById(imageId);
	    }
	}
}
