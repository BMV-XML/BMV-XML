package xml.stamp.service.stamp.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xml.stamp.service.stamp.service.dto.PhotoFileDTO;
import xml.stamp.service.stamp.service.service.PhotoService;

import java.io.IOException;

@RestController
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping(value = "/photo", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<PhotoFileDTO> addPhoto(@RequestPart("files") MultipartFile[] multiPartFiles) throws IOException {
        System.out.println(" Add new photo");
        String profilePhotoPath = photoService.addPhotoFile(multiPartFiles);
        System.out.println(profilePhotoPath);
        int index = profilePhotoPath.indexOf("S");
        String name = profilePhotoPath.substring(index);
        System.out.println("name : " + name);
       // File file = new File(profilePhotoPath);
        String path = "http://localhost:8080/resources/images/" + name;
        System.out.println("path :" + path);
        return new ResponseEntity<>(new PhotoFileDTO(path), HttpStatus.OK);
    }

    @PostMapping(value = "/file", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<PhotoFileDTO> addFile(@RequestPart("files") MultipartFile[] multiPartFiles) throws IOException {
        String profilePhotoPath = photoService.addPhotoFile(multiPartFiles);
        System.out.println(profilePhotoPath);
        int index = profilePhotoPath.indexOf("S");
        String name = profilePhotoPath.substring(index);
        System.out.println("name : " + name);
        // File file = new File(profilePhotoPath);
        String path = "http://localhost:8080/resources/images/" + name;
        System.out.println("path :" + path);
        return new ResponseEntity<>(new PhotoFileDTO(path), HttpStatus.OK);

    }
}
