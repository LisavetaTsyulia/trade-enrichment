package com.verygoodbank.tes.util;

import com.verygoodbank.tes.exception.FileProcessingException;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;

@UtilityClass
public class FileHelper {

    public final String TMP_PREFIX = "prefix-";
    public final String TMP_SUFFIX = "-suffix";

    public File createTmpFile() {
        File tmpFile = null;
        try {
            tmpFile = File.createTempFile(TMP_PREFIX, TMP_SUFFIX);
        } catch (IOException e) {
            throw new FileProcessingException(e);
        }
        tmpFile.deleteOnExit();
        return tmpFile;
    }
}
