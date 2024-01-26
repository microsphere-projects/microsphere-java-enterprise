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

import javax.validation.Configuration;
import javax.validation.ValidatorFactory;
import javax.validation.spi.BootstrapState;
import javax.validation.spi.ConfigurationState;
import javax.validation.spi.ValidationProvider;
import java.util.ServiceLoader;

/**
 * Intercepting {@link ValidationProvider}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ValidationProvider
 * @since 1.0.0
 */
class InterceptingValidationProvider<T extends Configuration<T>> implements ValidationProvider<T> {

    private final ValidationProvider<T> delegate;

    private final Iterable<ValidationInterceptor> validationInterceptors;

    InterceptingValidationProvider(ValidationProvider<T> delegate) {
        this.delegate = delegate;
        this.validationInterceptors = loadValidationInterceptors();
    }

    private Iterable<ValidationInterceptor> loadValidationInterceptors() {
        return ServiceLoader.load(ValidationInterceptor.class);
    }

    @Override
    public T createSpecializedConfiguration(BootstrapState state) {
        return delegate.createSpecializedConfiguration(state);
    }

    @Override
    public Configuration<?> createGenericConfiguration(BootstrapState state) {
        return delegate.createGenericConfiguration(state);
    }

    @Override
    public ValidatorFactory buildValidatorFactory(ConfigurationState configurationState) {
        return new InterceptingValidatorFactory(delegate.buildValidatorFactory(configurationState), validationInterceptors);
    }
}
