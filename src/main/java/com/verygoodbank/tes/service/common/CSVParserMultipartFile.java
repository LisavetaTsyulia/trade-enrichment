package com.verygoodbank.tes.service.common;

import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

public interface CSVParserMultipartFile<T> {

    Collection<T> parse(MultipartFile file);
}
