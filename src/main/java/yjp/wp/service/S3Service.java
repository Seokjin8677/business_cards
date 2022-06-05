package yjp.wp.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marvin.image.MarvinImage;
import org.marvinproject.image.transform.scale.Scale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile multipartFile) {
        if (Objects.requireNonNull(multipartFile.getContentType()).contains("image")) {
            try (InputStream inputStream = multipartFile.getInputStream()){
                BufferedImage originalImage = ImageIO.read(inputStream);

                BufferedImage resizedImage = resizeImage(originalImage, 300, 300);

                String type = multipartFile.getContentType().substring(multipartFile.getContentType().lastIndexOf("/") + 1);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(resizedImage, type, baos);
                baos.flush();
                InputStream is = new ByteArrayInputStream(baos.toByteArray());

                String fileName =  "business_card/" + UUID.randomUUID() + multipartFile.getOriginalFilename();

                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentLength(baos.size());
                objectMetadata.setContentType(multipartFile.getContentType());

                amazonS3.putObject(new PutObjectRequest(bucket, fileName, is , objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

                return "https://yj-aerofarm-cityfarmer.s3.ap-northeast-2.amazonaws.com/" + fileName;
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return "/image/default-image.png";
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) throws IOException {
        if (originalImage.getWidth() < width && originalImage.getHeight() < height) {
            return originalImage;
        }

        MarvinImage imageMarvin = new MarvinImage(originalImage);

        Scale scale = new Scale();
        scale.load();
        scale.setAttribute("newWidth", width);
        scale.setAttribute("newHeight", height);
        scale.process(imageMarvin.clone(), imageMarvin, null, null, false);

        return imageMarvin.getBufferedImageNoAlpha();
    }

    public void deleteFile(String fileName) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, "business_card" + fileName.substring(fileName.lastIndexOf("/"))));
    }
}
