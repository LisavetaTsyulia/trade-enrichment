package com.verygoodbank.tes.service.common;

import java.io.File;
import java.io.Reader;
import java.util.Collection;

public interface CSVParserFile<T> {

    Collection<T> parse(File file);

    Collection<T> parse(String fileName);
}
