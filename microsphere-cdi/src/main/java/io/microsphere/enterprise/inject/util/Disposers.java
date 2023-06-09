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
package io.microsphere.enterprise.inject.util;

import io.microsphere.enterprise.inject.standard.beans.ManagedBean;

import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.DefinitionException;
import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.microsphere.enterprise.inject.util.Exceptions.newDefinitionException;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableMap;

/**
 * The utilities class for Disposer Method
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.0
 */
public abstract class Disposers {

    private static final List<Class<? extends Annotation>> forbiddenParameterAnnotationTypes =
            asList(Observes.class, ObservesAsync.class);

    /**
     * A disposer method must be a default-access, public, protected or private, non-abstract method of
     * a managed bean class.
     * <p>
     * A disposer method may be either static or non-static.
     * <p>
     * A bean may declare multiple disposer methods.
     * <p>
     * Each disposer method must have exactly one disposed parameter, of the same type as the corresponding
     * producer method return type or producer field type.
     * <p>
     * A disposer method is bound to a producer method or producer field if:
     * <ul>
     *     <li>the producer method or producer field is declared by the same bean class as the disposer method, and</li>
     *     <li>the producer method or producer field is assignable to the disposed parameter, according to the rules of
     *     typesafe resolution defined in Typesafe resolution</li>
     * </ul>
     *
     * @param managedBean the {@link ManagedBean}
     * @return Unmodifiable {@link Map} represents the key is Disposer Method Type, and the value is {@link AnnotatedMethod}
     * @throws DefinitionException If the disposer method violate these rules :
     *                             <ul>
     *                                 <li>a method has more than one parameter annotated {@link Disposes @Disposes}</li>
     *                                 <li>a disposer method is annotated {@link Produces @Produces} or
     *                                 {@link Inject @Inject} has a parameter annotated
     *                                 {@link Observes @Observes} or has a parameter annotated {@link ObservesAsync @ObservesAsync}</li>
     *                                 <li>an interceptor or decorator has a method annotated {@link Disposes @Disposes}</li>
     *                                 <li>If there are multiple disposer methods for a single producer method or producer field</li>
     *                                 <li>If there is no producer method or producer field declared by the bean class
     *                                 that is assignable to the disposed parameter of a disposer method</li>
     *                             </ul>
     */
    public static Map<Type, AnnotatedMethod> resolveAndValidateDisposerMethods(ManagedBean managedBean) throws DefinitionException {
        Map<Type, AnnotatedMethod> disposerMethodsMap = new LinkedHashMap<>();
        AnnotatedType annotatedType = managedBean.getBeanType();
        Set<Type> producerTypes = managedBean.getProducerTypes();
        Set<AnnotatedMethod> methods = annotatedType.getMethods();
        // records the defined types from disposer method
        Set<Type> definedDisposerMethodTypes = new LinkedHashSet<>();
        methods.forEach(method -> {
            List<AnnotatedParameter> parameters = method.getParameters();
            int disposesParamCount = 0;
            for (AnnotatedParameter parameter : parameters) {
                if (parameter.isAnnotationPresent(Disposes.class)) {
                    Type parameterType = parameter.getBaseType();

                    if (definedDisposerMethodTypes.contains(parameterType)) {
                        throw newDefinitionException("There are multiple disposer methods in bean type[{}] for " +
                                "a single producer method or producer field!", managedBean.getBeanClass().getName());
                    }
                    if (!producerTypes.contains(parameterType)) {
                        throw newDefinitionException("No producer method or producer field declared by the bean class[name : {}]" +
                                        " that is assignable to the disposed parameter[type : {}] of a disposer method[{}]!",
                                managedBean.getBeanClass().getName(), parameterType.getTypeName(), method.getJavaMember());
                    }
                    definedDisposerMethodTypes.addAll(parameter.getTypeClosure());
                    disposesParamCount++;
                    disposerMethodsMap.put(parameterType, method);
                    validateDisposerMethod(method);
                }
            }
            if (disposesParamCount > 1) {
                throw newDefinitionException("The method[{}] has more than one parameter annotated @{}",
                        method.getJavaMember(), Disposes.class);
            }
        });

        return unmodifiableMap(disposerMethodsMap);
    }

    private static void validateDisposerMethod(AnnotatedMethod disposerMethod) {
        if (disposerMethod.isAnnotationPresent(Produces.class)) {
            throw newDefinitionException("The disposer method[{}] must not annotate @{}",
                    disposerMethod.getJavaMember(), Produces.class.getName());
        }
        if (disposerMethod.isAnnotationPresent(Inject.class)) {
            List<AnnotatedParameter> parameters = disposerMethod.getParameters();
            for (AnnotatedParameter parameter : parameters) {
                for (Class<? extends Annotation> forbiddenAnnotationType : forbiddenParameterAnnotationTypes) {
                    if (parameter.isAnnotationPresent(forbiddenAnnotationType)) {
                        throw newDefinitionException("The disposer method[{}] annotates @{} must not have a parameter annotated @{}",
                                disposerMethod.getJavaMember(), Inject.class.getName(), forbiddenAnnotationType.getName());
                    }
                }
            }
        }
    }

}
