package com.verygoodbank.tes.service.simpleImpl;

import java.io.File;
import java.util.Collection;

public interface CSVWriterI<T> {

    File persistToFile(Collection<T> data);
}
