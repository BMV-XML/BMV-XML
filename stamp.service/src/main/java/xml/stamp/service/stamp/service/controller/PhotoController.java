package xml.stamp.service.stamp.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xml.stamp.service.stamp.service.service.PhotoService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping("/photo")
    public ResponseEntity<InputStreamResource> addPhoto(@RequestPart("files") MultipartFile[] multiPartFiles) throws IOException {
        System.out.println(" Add new photo");
        String profilePhotoPath = photoService.addPhoto(multiPartFiles);
        System.out.println(profilePhotoPath);
        File file = new File(profilePhotoPath);
        return new ResponseEntity<>(new InputStreamResource(Files.newInputStream(file.toPath())), HttpStatus.OK);
    }
}
