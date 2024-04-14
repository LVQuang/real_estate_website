package edu.hqh.real_estate_website.service;

import edu.hqh.real_estate_website.model.Content;
import edu.hqh.real_estate_website.repository.ContentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ContentService {
    ContentRepository contentRepository;

    public Content findById(Long id) { return contentRepository.findById(id).orElse(null); }

    public void addAndUpdate(Content content) { contentRepository.save(content); }

    public  void delete(Long id) { contentRepository.deleteById(id); }

    public List<Content> getAll() { return contentRepository.findAll(); }
}
