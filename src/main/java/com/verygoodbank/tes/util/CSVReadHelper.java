package com.verygoodbank.tes.util;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Log4j2
@UtilityClass
public class CSVReadHelper {

    public File getCSVFile(final String fileName) {
        try {
            return Objects.requireNonNull(new ClassPathResource(fileName).getFile());
        } catch (IOException ex) {
            log.error("File read error: ", ex);
        }
        return null;
    }
}
