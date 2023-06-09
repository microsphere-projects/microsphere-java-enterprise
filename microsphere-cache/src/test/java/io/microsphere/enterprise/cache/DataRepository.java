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
package io.microsphere.enterprise.cache;

import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheRemove;
import javax.cache.annotation.CacheRemoveAll;
import javax.cache.annotation.CacheResult;
import javax.cache.annotation.CacheValue;

/**
 * Data Repository
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.0
 */
@CacheDefaults(cacheName = "defaultCache")
public interface DataRepository {

    @CachePut(cacheName = "simpleCache")
    boolean create(@CacheKey String name, @CacheValue Object value);

    @CachePut
    boolean save(@CacheKey String name, @CacheKey String alias, @CacheValue Object value);

    @CachePut
    boolean save(String name, @CacheValue Object value);

    @CacheRemove(cacheName = "simpleCache", afterInvocation = false)
    boolean remove(String name);

    @CacheResult(exceptionCacheName = "exceptionCache")
    Object get(String name);

    @CacheResult(skipGet = true)
    Object getWithoutCache(String name);

    @CacheRemoveAll(cacheName = "simpleCache")
    void removeAll();
}
