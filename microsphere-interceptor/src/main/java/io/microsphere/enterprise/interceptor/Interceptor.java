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
package io.microsphere.enterprise.interceptor;


import io.microsphere.lang.Prioritized;

import java.util.Arrays;

import static io.microsphere.util.ServiceLoaderUtils.loadServices;

/**
 * The tagging interface that all {@link javax.interceptor.Interceptor @Interceptor} class should implement.
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.0
 */
public interface Interceptor {

    /**
     * Load the sorted instances of {@link Interceptor} via Java Standard SPI.
     *
     * @return non-null
     */
    static Interceptor[] loadInterceptors() {
        Class<Interceptor> interceptorClass = Interceptor.class;
        Interceptor[] interceptors = loadServices(interceptorClass, interceptorClass.getClassLoader());
        Arrays.sort(interceptors, Prioritized.COMPARATOR);
        return interceptors;
    }
}
