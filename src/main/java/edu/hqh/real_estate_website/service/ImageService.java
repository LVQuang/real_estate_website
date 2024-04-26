package edu.hqh.real_estate_website.service;

import edu.hqh.real_estate_website.entity.Image;
import edu.hqh.real_estate_website.repository.ImageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class ImageService {
    ImageRepository imageRepository;

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
}
