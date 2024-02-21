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

import javax.validation.ClockProvider;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.MessageInterpolator;
import javax.validation.ParameterNameProvider;
import javax.validation.TraversableResolver;
import javax.validation.Validator;
import javax.validation.ValidatorContext;
import javax.validation.ValidatorFactory;

/**
 * Intercepting {@link ValidatorFactory} Class
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ValidatorFactory
 * @since 1.0.0
 */
class InterceptingValidatorFactory implements ValidatorFactory {

    private final ValidatorFactory delegate;

    private final Iterable<ValidationInterceptor> validationInterceptors;

    InterceptingValidatorFactory(ValidatorFactory delegate, Iterable<ValidationInterceptor> validationInterceptors) {
        this.delegate = delegate;
        this.validationInterceptors = validationInterceptors;
    }

    @Override
    public Validator getValidator() {
        return new InterceptingValidator(delegate.getValidator(), validationInterceptors);
    }

    @Override
    public ValidatorContext usingContext() {
        return delegate.usingContext();
    }

    @Override
    public MessageInterpolator getMessageInterpolator() {
        return delegate.getMessageInterpolator();
    }

    @Override
    public TraversableResolver getTraversableResolver() {
        return delegate.getTraversableResolver();
    }

    @Override
    public ConstraintValidatorFactory getConstraintValidatorFactory() {
        return delegate.getConstraintValidatorFactory();
    }

    @Override
    public ParameterNameProvider getParameterNameProvider() {
        return delegate.getParameterNameProvider();
    }

    @Override
    public ClockProvider getClockProvider() {
        return delegate.getClockProvider();
    }

    @Override
    public <T> T unwrap(Class<T> type) {
        return delegate.unwrap(type);
    }

    @Override
    public void close() {
        delegate.close();
    }
}
