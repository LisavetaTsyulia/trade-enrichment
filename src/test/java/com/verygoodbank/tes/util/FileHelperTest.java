package com.verygoodbank.tes.util;

import org.junit.jupiter.api.Test;

import java.io.File;

import static com.verygoodbank.tes.util.FileHelper.TMP_PREFIX;
import static com.verygoodbank.tes.util.FileHelper.TMP_SUFFIX;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileHelperTest {
    @Test
    public void testCreateTmpFile() {
        // when try to create tmp file
        File tmpFile = FileHelper.createTmpFile();

        // then new tmp file is created
        assertNotNull(tmpFile);
        assertTrue(tmpFile.exists());
        assertTrue(tmpFile.getName().startsWith(TMP_PREFIX));
        assertTrue(tmpFile.getName().endsWith(TMP_SUFFIX));

        // Clean up
        tmpFile.delete();
    }
}
