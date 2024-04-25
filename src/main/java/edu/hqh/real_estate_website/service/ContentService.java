package edu.hqh.real_estate_website.service;

import edu.hqh.real_estate_website.mapper.ContentMapper;
import edu.hqh.real_estate_website.repository.ContentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class ContentService {
    ContentRepository contentRepository;
    ContentMapper contentMapper;    
}
