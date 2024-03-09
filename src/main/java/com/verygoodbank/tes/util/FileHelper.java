package com.verygoodbank.tes.util;

import com.verygoodbank.tes.exception.FileProcessingException;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.IOException;

@UtilityClass
public class FileHelper {

    public File createTmpFile() {
        File tmpFile = null;
        try {
            tmpFile = File.createTempFile("prefix-", "-suffix");
        } catch (IOException e) {
            throw new FileProcessingException(e);
        }
        tmpFile.deleteOnExit();
        return tmpFile;
    }
}
