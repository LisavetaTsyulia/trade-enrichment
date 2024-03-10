package com.verygoodbank.tes.service.common.csvparser;

import java.io.File;
import java.io.Reader;
import java.util.Collection;

public interface CSVParserFile<T> {

    Collection<T> parse(String fileName);
}
