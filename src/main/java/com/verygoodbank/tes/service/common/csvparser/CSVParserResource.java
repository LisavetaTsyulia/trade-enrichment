package com.verygoodbank.tes.service.common.csvparser;

import org.springframework.core.io.Resource;

import java.util.Collection;

public interface CSVParserResource<T> {

    Collection<T> parse(Resource resource);
}
