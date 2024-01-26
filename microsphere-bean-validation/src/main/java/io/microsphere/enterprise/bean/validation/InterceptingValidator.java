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

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Set;

import static java.util.Collections.emptySet;

/**
 * Intercepting {@link Validator}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Validator
 * @see ValidationInterceptor
 * @since 1.0.0
 */
class InterceptingValidator implements Validator, ExecutableValidator, ValidationInterceptor {

    private final Validator delegate;

    private final Iterable<ValidationInterceptor> validationInterceptors;

    InterceptingValidator(Validator delegate, Iterable<ValidationInterceptor> validationInterceptors) {
        this.delegate = delegate;
        this.validationInterceptors = validationInterceptors;
    }

    @Override
    public <T> void beforeValidate(T object, Class<?>... groups) {
        validationInterceptors.forEach(i -> i.beforeValidate(object, groups));
    }

    @Override
    public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
        beforeValidate(object, groups);
        Set<ConstraintViolation<T>> constraintViolations = emptySet();
        Throwable error = null;
        try {
            constraintViolations = validator().validate(object, groups);
        } catch (Throwable e) {
            error = e;
        } finally {
            afterValidate(object, constraintViolations, error, groups);
        }
        return constraintViolations;
    }

    @Override
    public <T> void afterValidate(T object, Set<ConstraintViolation<T>> constraintViolations, Throwable error, Class<?>... groups) {
        validationInterceptors.forEach(i -> i.afterValidate(object, constraintViolations, error, groups));
    }

    // TODO Intercept more

    @Override
    public <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName, Class<?>... groups) {
        return validator().validateProperty(object, propertyName, groups);
    }

    @Override
    public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType, String propertyName, Object value, Class<?>... groups) {
        return validator().validateValue(beanType, propertyName, value, groups);
    }

    @Override
    public <T> Set<ConstraintViolation<T>> validateParameters(T object, Method method, Object[] parameterValues, Class<?>... groups) {
        return executableValidator().validateParameters(object, method, parameterValues, groups);
    }

    @Override
    public <T> Set<ConstraintViolation<T>> validateReturnValue(T object, Method method, Object returnValue, Class<?>... groups) {
        return executableValidator().validateReturnValue(object, method, returnValue, groups);
    }

    @Override
    public <T> Set<ConstraintViolation<T>> validateConstructorParameters(Constructor<? extends T> constructor, Object[] parameterValues, Class<?>... groups) {
        return executableValidator().validateConstructorParameters(constructor, parameterValues, groups);
    }

    @Override
    public <T> Set<ConstraintViolation<T>> validateConstructorReturnValue(Constructor<? extends T> constructor, T createdObject, Class<?>... groups) {
        return executableValidator().validateConstructorReturnValue(constructor, createdObject, groups);
    }

    private Validator validator() {
        return delegate;
    }

    private ExecutableValidator executableValidator() {
        return delegate.forExecutables();
    }

    @Override
    public BeanDescriptor getConstraintsForClass(Class<?> clazz) {
        return delegate.getConstraintsForClass(clazz);
    }

    @Override
    public <T> T unwrap(Class<T> type) {
        return delegate.unwrap(type);
    }

    @Override
    public ExecutableValidator forExecutables() {
        return this;
    }
}
