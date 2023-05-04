package com.programming.karaoke.service;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

@Service
//@RequiredArgsConstructor
public class S3Service implements FileService{
    public static final String BUCKET_NAME = "bedan";
    private final AmazonS3Client amazonS3Client;

    public S3Service(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }
   public S3Service(){
       this.amazonS3Client = new AmazonS3Client(new BasicAWSCredentials("AKIARNRISJ27C7TC23HO", "OnctBsMFmvfz73hKjqNwu7CDFZ835nCfdi+1r5J/"));
       this.amazonS3Client.setRegion(Region.getRegion(Regions.US_EAST_1));
   }
    @Override
    public String uploadFile(MultipartFile file){
//        Upload the file to AWS

//        perpare a key
//        1. extract the mo rong cua ten tep tin tu duong dan
     var fileNameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
//     UUID là 1 chuỗi định danh duy nhât , sử dụng đannh dấu các đối tượng trong hệ thống
//        Ví dụ, nếu UUID được tạo ra là d8b3a5a5-5a5d-47b1-a8d6-847b0c2a72f2, và
//        phần mở rộng của tên tập tin là .jpg,
//        thì giá trị của biến s sẽ là d8b3a5a5-5a5d-47b1-a8d6-847b0c2a72f2.jpg.
     String key = UUID.randomUUID()+"." + fileNameExtension;

//     Objectmetadata là 1 lớp trong awa sdk -> quản lí thông tin lquan đến 1 đối tượng (kkichs thước , loại content, mã hoa ,...)
     var metadata = new ObjectMetadata();
     metadata.setContentLength(file.getSize());
     metadata.setContentType(file.getContentType());
     try{
         amazonS3Client.putObject(BUCKET_NAME,key,file.getInputStream(),metadata);

     } catch ( IOException e)
     {
         throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"An exception occurred while uploading the file");
     }
//     Đây là một đoạn mã trong Java để thiết lập quyền truy cập cho một tệp tin đã được tải lên Amazon S3
     amazonS3Client.setObjectAcl(BUCKET_NAME,key, CannedAccessControlList.PublicRead);
//Lấy URL của một tệp tin đã được tải lên Amazon S3
      return  amazonS3Client.getResourceUrl(BUCKET_NAME,key);
    }
}
