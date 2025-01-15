package org.example.carebridge.domain.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.board.entity.Board;
import org.example.carebridge.domain.file.entity.File;
import org.example.carebridge.domain.file.repository.FileRepository;
import org.example.carebridge.global.exception.BadValueException;
import org.example.carebridge.global.exception.ExceptionType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
@Service
@RequiredArgsConstructor
public class FileService {

    protected final AmazonS3 amazonS3;
    private final FileRepository fileRepository;

    @Value("${cloud.aws.s3.bucket}")
    protected String bucket;

    protected static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB

    protected static final String[] EXTENSIONS = {"jpg", "jpeg", "png", "gif"};

    protected void validFile(MultipartFile file) {
        String extension = getExtension(file.getOriginalFilename());

        boolean isValidExtension = Arrays.stream(EXTENSIONS).anyMatch(extension::equalsIgnoreCase);

        if (!isValidExtension) {
            throw new BadValueException(ExceptionType.UNSUPPORTED_FILE_TYPE);
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new BadValueException(ExceptionType.FILE_SIZE_EXCEEDED);
        }
    }

    protected String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    }

    protected String generateUniqueFileName(String originalFileName) {
        String extension = getExtension(originalFileName);
        String uniqueId = java.util.UUID.randomUUID().toString();

        return uniqueId + "." + extension;
    }


    public File uploadAndSaveMetaData(Board board, MultipartFile file) throws IOException {
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

        return new File(
                board,
                fileUrl,
                file.getSize(),
                getExtension(originalFileName),
                originalFileName
        );

    }
    @Transactional
    public String uploadFile(MultipartFile profileImage, Board board) {
        File file;
        try {
            file = uploadAndSaveMetaData(board, profileImage);
        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패 : " + e.getMessage(), e);
        }
        fileRepository.save(file);
        board.updateFileUrl(file.getUrl());

        return file.getUrl();

    }
}
