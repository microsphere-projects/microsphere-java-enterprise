/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.microsphere.enterprise.cache.annotation.interceptor;

import io.microsphere.enterprise.cache.annotation.util.CacheOperationAnnotationInfo;

import javax.cache.Cache;
import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheKeyInvocationContext;
import javax.cache.annotation.CacheRemoveAll;
import javax.cache.annotation.GeneratedCacheKey;
import javax.interceptor.Interceptor;
import java.util.Optional;


/**
 * The {@link Interceptor @Interceptor} class for Java Caching annotation {@link CacheRemoveAll}.
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.0
 */
@CacheRemoveAll
@Interceptor
public class CacheRemoveAllInterceptor extends CacheOperationInterceptor<CacheRemoveAll> {

    @Override
    protected CacheOperationAnnotationInfo getCacheOperationAnnotationInfo(CacheRemoveAll cacheOperationAnnotation,
                                                                           CacheDefaults cacheDefaults) {
        return new CacheOperationAnnotationInfo(cacheOperationAnnotation, cacheDefaults);
    }

    @Override
    protected Object beforeExecute(CacheRemoveAll cacheOperationAnnotation,
                                   CacheKeyInvocationContext<CacheRemoveAll> cacheKeyInvocationContext,
                                   CacheOperationAnnotationInfo cacheOperationAnnotationInfo, Cache cache,
                                   Optional<GeneratedCacheKey> cacheKey) {
        if (!cacheOperationAnnotationInfo.isAfterInvocation()) {
            cache.removeAll();
        }
        return null;
    }

    @Override
    protected void afterExecute(CacheRemoveAll cacheOperationAnnotation,
                                CacheKeyInvocationContext<CacheRemoveAll> cacheKeyInvocationContext,
                                CacheOperationAnnotationInfo cacheOperationAnnotationInfo, Cache cache,
                                Optional<GeneratedCacheKey> cacheKey, Object result) {
        if (cacheOperationAnnotation.afterInvocation()) {
            cache.removeAll();
        }
    }

    @Override
    protected void handleFailure(CacheRemoveAll cacheOperationAnnotation,
                                 CacheKeyInvocationContext<CacheRemoveAll> cacheKeyInvocationContext,
                                 CacheOperationAnnotationInfo cacheOperationAnnotationInfo, Cache cache,
                                 Optional<GeneratedCacheKey> cacheKey, Throwable failure) {
        cache.removeAll();
    }
}
