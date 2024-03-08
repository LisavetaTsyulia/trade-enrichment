package com.verygoodbank.tes.service;

import java.io.File;
import java.util.Collection;

public interface CSVParserFile<T> {

    Collection<T> parse(File file);
}
