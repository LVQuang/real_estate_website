package edu.hqh.real_estate_website.service;

import edu.hqh.real_estate_website.entity.Image;
import edu.hqh.real_estate_website.entity.Post;
import edu.hqh.real_estate_website.enums.ErrorCode;
import edu.hqh.real_estate_website.exception.AppException;
import edu.hqh.real_estate_website.repository.ImageRepository;
import edu.hqh.real_estate_website.repository.PostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class ImageService {
    ImageRepository imageRepository;
    PostRepository postRepository;

    public Image create(Image image) {
        
        image.setImageDate(LocalDate.now());
        return imageRepository.save(image);
    }

    public List<Image> getAll() {
        return (List<Image>) imageRepository.findAll();
    }

    public Image getById(String id) {
        return imageRepository.findById(id).get();
    }

    public void delete(String id) {
        imageRepository.deleteById(id);
    }

    public boolean createListImage(List<MultipartFile> files, String postId)
            throws IOException, SQLException {
        for (MultipartFile file : files) {
            byte[] bytes = file.getBytes();
            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
            var image = Image.builder()
                    .content(blob)
                    .imageDate(LocalDate.now())
                    .build();

            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new AppException(ErrorCode.ITEM_DONT_EXISTS));

            post.setImages(Collections.singleton(image));

            image.setPost(post);

            imageRepository.save(image);

        }

        return true;
    }
}
