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
package io.microsphere.enterprise.inject.standard;

import javax.enterprise.inject.spi.AnnotatedCallable;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * {@link InjectionPoint} of {@link Method Methods'} {@link Parameter}
 * or {@link Constructor Constructors'} {@link Parameter}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.0
 */
public class ParameterInjectionPoint extends AbstractInjectionPoint<AnnotatedParameter, AnnotatedCallable, Executable> {

    public ParameterInjectionPoint(AnnotatedParameter annotatedParameter) {
        this(annotatedParameter, annotatedParameter.getDeclaringCallable());
    }

    public ParameterInjectionPoint(AnnotatedParameter annotatedParameter,
                                   AnnotatedCallable annotatedCallable) {
        this(annotatedParameter, annotatedCallable, null);
    }

    public ParameterInjectionPoint(AnnotatedParameter annotatedParameter,
                                   AnnotatedCallable annotatedCallable, Bean<?> bean) {
        super(annotatedParameter, annotatedCallable, bean);
    }
}
