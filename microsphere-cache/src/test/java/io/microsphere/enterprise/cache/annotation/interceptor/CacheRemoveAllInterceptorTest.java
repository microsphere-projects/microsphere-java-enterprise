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

import io.microsphere.enterprise.cache.DataRepository;
import io.microsphere.enterprise.cache.InMemoryDataRepository;
import io.microsphere.enterprise.interceptor.ComponentEnhancer;
import io.microsphere.enterprise.interceptor.DefaultComponentEnhancer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * {@link CacheRemoveAllInterceptor} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.0
 */
public class CacheRemoveAllInterceptorTest {

    private DataRepository dataRepository = new InMemoryDataRepository();

    private ComponentEnhancer enhancer = new DefaultComponentEnhancer();

    @Test
    public void test() {
        DataRepository repository = enhancer.enhance(dataRepository, DataRepository.class, new CachePutInterceptor(),
                new CacheRemoveInterceptor(), new CacheRemoveAllInterceptor());
        assertTrue(repository.create("A", 1));
        assertTrue(repository.remove("A"));
        assertNull(repository.get("A"));

        assertTrue(repository.create("A", 1));
        assertTrue(repository.create("B", 2));
        assertEquals(Integer.valueOf(1), repository.get("A"));
        assertEquals(Integer.valueOf(2), repository.get("B"));

        repository.removeAll();
        assertNull(repository.get("A"));
        assertNull(repository.get("B"));
    }
}
