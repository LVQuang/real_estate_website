package edu.hqh.real_estate_website.service;

import edu.hqh.real_estate_website.entity.Image;
import edu.hqh.real_estate_website.enums.ErrorCode;
import edu.hqh.real_estate_website.exception.AppException;
import edu.hqh.real_estate_website.repository.ImageRepository;
import edu.hqh.real_estate_website.repository.PostRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class ImageService {
    ImageRepository imageRepository;
    PostRepository postRepository;


    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Image create(Image image) {
        
        image.setImageDate(LocalDate.now());
        return imageRepository.save(image);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Image> getAll() {
        return (List<Image>) imageRepository.findAll();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Image getById(String id) {
        return imageRepository.findById(id).get();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void delete(String id) {
        imageRepository.deleteById(id);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public boolean createListImage(List<MultipartFile> files, String postId)
            throws IOException, SQLException {

        Set<Image> images = new HashSet<>();

        for (MultipartFile file : files) {

            byte[] bytes = file.getBytes();
            Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

            var image = Image.builder()
                    .content(blob)
                    .imageDate(LocalDate.now())
                    .build();

            images.add(image);
        }

        imageRepository.saveAll(images);

        var post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.ITEM_DONT_EXISTS));

        post.setImages(images);

        postRepository.save(post);
        log.info("Add Images Successfully");
        return true;
    }
}
