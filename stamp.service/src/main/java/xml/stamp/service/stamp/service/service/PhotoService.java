package xml.stamp.service.stamp.service.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

@Service
public class PhotoService {

    final static String PHOTOS_PATH = "BMV-XML/stamp.service/src/main/resources/static/images/";

    public String addPhoto(MultipartFile[] multipartFiles) throws IOException {
        if (multipartFiles == null) {
            return "";
        }
        Path path = Paths.get(PHOTOS_PATH  + "_" + this.generateId());
        return savePicturesOnPath(multipartFiles, path);
    }

    private String savePicturesOnPath(MultipartFile[] multipartFiles, Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        MultipartFile mpf = multipartFiles[0];
        String fileName = mpf.getOriginalFilename();
        Path filePath;
        try (InputStream inputStream = mpf.getInputStream()) {
            filePath = path.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
        return filePath.toString();
    }

    private int generateId(){
        Random rn = new Random();
        int id = rn.nextInt();
        Path path = Paths.get(PHOTOS_PATH  + "_" + id);
        while (Files.exists(path)) {
            id = rn.nextInt();
            path = Paths.get(PHOTOS_PATH  + "_" + id);
        }
        return id;
    }
}
