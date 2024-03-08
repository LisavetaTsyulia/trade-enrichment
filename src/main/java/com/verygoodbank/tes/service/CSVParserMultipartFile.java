package com.verygoodbank.tes.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Collection;

public interface CSVParserMultipartFile<T> {

    Collection<T> parse(MultipartFile file);
}
