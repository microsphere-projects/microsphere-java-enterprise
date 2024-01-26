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
import javax.validation.groups.Default;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * The Interceptor class for the bean {@link Validator} and {@link ExecutableValidator}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see InterceptingValidator
 * @see Validator
 * @see ExecutableValidator
 * @since 1.0.0
 */
public interface ValidationInterceptor {

    /**
     * Intercept before {@link Validator#validate(Object, Class[])}
     *
     * @param object object to validate
     * @param groups the group or list of groups targeted for validation (defaults to
     *               {@link Default})
     */
    default <T> void beforeValidate(T object, Class<?>... groups) {
    }

    /**
     * Intercept after {@link Validator#validate(Object, Class[])}
     *
     * @param object               object to validate
     * @param constraintViolations constraint violations or an empty set if none
     * @param error                error if validation is failed
     * @param groups               the group or list of groups targeted for validation (defaults to
     *                             {@link Default})
     */
    default <T> void afterValidate(T object, Set<ConstraintViolation<T>> constraintViolations, Throwable error, Class<?>... groups) {
    }

    /**
     * Intercept before {@link Validator#validateProperty(Object, String, Class[])}
     *
     * @param object       object to validate
     * @param propertyName property to validate (i.e. field and getter constraints)
     * @param groups       the group or list of groups targeted for validation (defaults to
     *                     {@link Default})
     */
    default <T> void beforeValidateProperty(T object, String propertyName, Class<?>... groups) {
    }

    /**
     * Intercept after {@link Validator#validateProperty(Object, String, Class[])}
     *
     * @param object               object to validate
     * @param propertyName         property to validate (i.e. field and getter constraints)
     * @param constraintViolations constraint violations or an empty set if none
     * @param error                error if validation is failed
     * @param groups               the group or list of groups targeted for validation (defaults to
     *                             {@link Default})
     */
    default <T> void afterValidateProperty(T object, String propertyName, Set<ConstraintViolation<T>> constraintViolations, Throwable error, Class<?>... groups) {
    }

    /**
     * Intercept before {@link Validator#validateValue(Class, String, Object, Class[])}
     *
     * @param beanType     the bean type
     * @param propertyName property to validate
     * @param value        property value to validate
     * @param groups       the group or list of groups targeted for validation (defaults to
     *                     {@link Default}).
     */
    default <T> void beforeValidateValue(Class<T> beanType, String propertyName, Object value, Class<?>... groups) {
    }

    /**
     * Intercept after {@link Validator#validateValue(Class, String, Object, Class[])}
     *
     * @param beanType             the bean type
     * @param propertyName         property to validate
     * @param value                property value to validate
     * @param constraintViolations constraint violations or an empty set if none
     * @param error                error if validation is failed
     * @param groups               the group or list of groups targeted for validation (defaults to
     *                             {@link Default}).
     */
    default <T> void afterValidateValue(Class<T> beanType, String propertyName, Object value, Set<ConstraintViolation<T>> constraintViolations, Throwable error, Class<?>... groups) {
    }

    /**
     * Intercept before {@link ExecutableValidator#validateParameters(Object, Method, Object[], Class[])}
     *
     * @param object          the object on which the method to validate is invoked
     * @param method          the method for which the parameter constraints is validated
     * @param parameterValues the values provided by the caller for the given method's
     *                        parameters
     * @param groups          the group or list of groups targeted for validation (defaults to
     *                        {@link Default})
     */
    default <T> void beforeValidateParameters(T object, Method method, Object[] parameterValues, Class<?>... groups) {
    }

    /**
     * Intercept after {@link ExecutableValidator#validateParameters(Object, Method, Object[], Class[])}
     *
     * @param object               the object on which the method to validate is invoked
     * @param method               the method for which the parameter constraints is validated
     * @param parameterValues      the values provided by the caller for the given method's
     *                             parameters
     * @param constraintViolations constraint violations or an empty set if none
     * @param error                error if validation is failed
     * @param groups               the group or list of groups targeted for validation (defaults to
     *                             {@link Default})
     */
    default <T> void afterValidateParameters(T object, Method method, Object[] parameterValues, Set<ConstraintViolation<T>> constraintViolations, Throwable error, Class<?>... groups) {
    }

    /**
     * Intercept before {@link ExecutableValidator#validateReturnValue(Object, Method, Object, Class[])}
     *
     * @param object      the object on which the method to validate is invoked
     * @param method      the method for which the return value constraints is validated
     * @param returnValue the value returned by the given method
     * @param groups      the group or list of groups targeted for validation (defaults to
     *                    {@link Default})
     */
    default <T> void beforeValidateReturnValue(T object, Method method, Object returnValue, Class<?>... groups) {
    }

    /**
     * Intercept after {@link ExecutableValidator#validateReturnValue(Object, Method, Object, Class[])}
     *
     * @param object               the object on which the method to validate is invoked
     * @param method               the method for which the return value constraints is validated
     * @param returnValue          the value returned by the given method
     * @param constraintViolations constraint violations or an empty set if none
     * @param error                error if validation is failed
     * @param groups               the group or list of groups targeted for validation (defaults to
     *                             {@link Default})
     */
    default <T> void afterValidateReturnValue(T object, Method method, Object returnValue, Set<ConstraintViolation<T>> constraintViolations, Throwable error, Class<?>... groups) {
    }

    /**
     * Intercept before {@link ExecutableValidator#validateConstructorParameters(Constructor, Object[], Class[])}
     *
     * @param constructor     the constructor for which the parameter constraints is validated
     * @param parameterValues the values provided by the caller for the given constructor's
     *                        parameters
     * @param groups          the group or list of groups targeted for validation (defaults to
     *                        {@link Default})
     */
    default <T> void beforeValidateConstructorParameters(Constructor<? extends T> constructor, Object[] parameterValues, Class<?>... groups) {
    }

    /**
     * Intercept after {@link ExecutableValidator#validateConstructorParameters(Constructor, Object[], Class[])}
     *
     * @param constructor          the constructor for which the parameter constraints is validated
     * @param parameterValues      the values provided by the caller for the given constructor's
     *                             parameters
     * @param constraintViolations constraint violations or an empty set if none
     * @param error                error if validation is failed
     * @param groups               the group or list of groups targeted for validation (defaults to
     *                             {@link Default})
     */
    default <T> void afterValidateConstructorParameters(Constructor<? extends T> constructor, Object[] parameterValues, Set<ConstraintViolation<T>> constraintViolations, Throwable error, Class<?>... groups) {
    }

    /**
     * Intercept before {@link ExecutableValidator#validateConstructorReturnValue(Constructor, Object, Class[])}
     *
     * @param constructor   the constructor for which the return value constraints is validated
     * @param createdObject the object instantiated by the given method
     * @param groups        the group or list of groups targeted for validation (defaults to
     *                      {@link Default})
     */
    default <T> void beforeValidateConstructorReturnValue(Constructor<? extends T> constructor, Object createdObject, Class<?>... groups) {
    }

    /**
     * Intercept before {@link ExecutableValidator#validateConstructorReturnValue(Constructor, Object, Class[])}
     *
     * @param constructor          the constructor for which the return value constraints is validated
     * @param createdObject        the object instantiated by the given method
     * @param constraintViolations constraint violations or an empty set if none
     * @param error                error if validation is failed
     * @param groups               the group or list of groups targeted for validation (defaults to
     *                             {@link Default})
     */
    default <T> void afterValidateConstructorReturnValue(Constructor<? extends T> constructor, Object createdObject, Set<ConstraintViolation<T>> constraintViolations, Throwable error, Class<?>... groups) {
    }
}
