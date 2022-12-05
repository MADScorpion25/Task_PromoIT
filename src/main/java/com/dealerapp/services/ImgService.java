package com.dealerapp.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImgService {

    public static String uploadImg(MultipartFile file, String path) throws IOException {
        String resFileName = "";
        if(file != null){
            File uploadDir = new File(path);
            if(!uploadDir.exists()){
                uploadDir.mkdirs();
            }
            String uidFile = UUID.randomUUID().toString();
            resFileName = uidFile + "." + file.getOriginalFilename();
            String absolutePath = uploadDir.getAbsolutePath();
            file.transferTo(new File(absolutePath + "\\" + resFileName));
        }
        return resFileName;
    }

    public static void deleteImg(String imgName, String path){
        File uploadDir = new File(path);
        File file = new File(uploadDir.getAbsolutePath() + "\\" + imgName);
        if(file.exists()){
            file.delete();
        }
    }
}
