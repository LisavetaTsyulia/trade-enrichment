package com.verygoodbank.tes.service.batchImpl.resourceEnricher;


import org.springframework.core.io.Resource;

import java.util.concurrent.CompletableFuture;

public interface ResourceEnricher<T> {

    CompletableFuture<T> processResource(Resource resource);
}
