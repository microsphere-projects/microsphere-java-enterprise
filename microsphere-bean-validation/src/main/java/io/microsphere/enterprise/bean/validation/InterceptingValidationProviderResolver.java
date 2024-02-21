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
package io.microsphere.enterprise.bean.validation;

import javax.validation.Validation;
import javax.validation.ValidationProviderResolver;
import javax.validation.bootstrap.GenericBootstrap;
import javax.validation.spi.BootstrapState;
import javax.validation.spi.ValidationProvider;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * {@link ValidationProviderResolver} Class wraps the external {@link ValidationProviderResolver},
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ValidationProviderResolver
 * @since 1.0.0
 */
public class InterceptingValidationProviderResolver implements ValidationProviderResolver {

    private final ValidationProviderResolver delegate;

    public InterceptingValidationProviderResolver() {
        this(resolveDefaultValidationProviderResolver());
    }

    public InterceptingValidationProviderResolver(ValidationProviderResolver delegate) {
        this.delegate = delegate;
    }

    private static ValidationProviderResolver resolveDefaultValidationProviderResolver() {
        GenericBootstrap genericBootstrap = Validation.byDefaultProvider();
        if (genericBootstrap instanceof BootstrapState) {
            BootstrapState bootstrapState = (BootstrapState) genericBootstrap;
            return bootstrapState.getDefaultValidationProviderResolver();
        }
        throw new IllegalArgumentException("The default Provider is not an instance of javax.validation.spi.BootstrapState");
    }

    @Override
    public List<ValidationProvider<?>> getValidationProviders() {
        return delegate.getValidationProviders()
                .stream()
                .map(InterceptingValidationProvider::new)
                .collect(toList());
    }

}
