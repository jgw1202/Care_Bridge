package org.example.carebridge.domain.user.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.example.carebridge.domain.user.entity.DoctorPortfolioImage;
import org.example.carebridge.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DoctorPortfolioUploadService extends UserProfileUploadService {


    public DoctorPortfolioUploadService(AmazonS3 amazonS3) {
        super(amazonS3);
    }

    public DoctorPortfolioImage DoctorUploadAndSaveMetaData(User user, MultipartFile file) throws IOException {
        validFile(file);

        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.isEmpty()) {
            throw new IllegalArgumentException("파일 이름이 없음");
        }

        String uniqueFileName = generateUniqueFileName(originalFileName);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        amazonS3.putObject(bucket, uniqueFileName, file.getInputStream(), metadata);
        String fileUrl = amazonS3.getUrl(bucket, uniqueFileName).toString();

        return new DoctorPortfolioImage(
                user,
                fileUrl,
                file.getSize(),
                getExtension(originalFileName),
                originalFileName
        );

    }
}
