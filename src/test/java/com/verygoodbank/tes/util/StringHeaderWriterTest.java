package com.verygoodbank.tes.util;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.Writer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

public class StringHeaderWriterTest {
    @Test
    public void testStringHeader() throws IOException {
        // given header Writer Helper and mocked Writer
        String headerStr = "header_provided";
        StringHeaderWriter stringHeaderWriter = new StringHeaderWriter(headerStr);
        Writer writerMock = Mockito.mock(Writer.class);

        // when executing write header
        stringHeaderWriter.writeHeader(writerMock);

        // then writer is triggered with expected header string
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        verify(writerMock).write(argument.capture());
        assertEquals(headerStr, argument.getValue());
    }
}
