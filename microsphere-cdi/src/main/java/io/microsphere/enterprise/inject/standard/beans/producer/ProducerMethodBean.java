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
package io.microsphere.enterprise.inject.standard.beans.producer;

import io.microsphere.enterprise.inject.standard.MethodParameterInjectionPoint;
import io.microsphere.enterprise.inject.standard.annotation.ReflectiveAnnotatedMethod;
import io.microsphere.enterprise.inject.standard.beans.AbstractBean;
import io.microsphere.enterprise.inject.util.Beans;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.Producer;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static io.microsphere.enterprise.inject.util.Producers.validateProducerMethod;
import static java.util.Collections.unmodifiableSet;

/**
 * Producer {@link Method} {@link Bean} based on Java Reflection
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @see Producer
 * @since 1.0.0
 */
public class ProducerMethodBean<T> extends AbstractBean<Method, T> implements Producer<T> {

    private final AnnotatedMethod<T> method;

    public ProducerMethodBean(AnnotatedMethod<T> method) {
        super(method.getJavaMember(), method.getDeclaringType());
        this.method = method;
    }

    @Override
    protected void validate(Method producerMethod) {
        validateProducerMethod(producerMethod);
    }

    @Override
    protected String getBeanName(Method producerMethod) {
        return Beans.getBeanName(producerMethod);
    }

    @Override
    protected T doCreate(CreationalContext<T> creationalContext) {
        // TODO
        return null;
    }

    @Override
    protected void doDestroy(T instance, CreationalContext<T> creationalContext) {
        // TODO
        creationalContext.release();
    }

    @Override
    public T produce(CreationalContext<T> ctx) {
        return create(ctx);
    }

    @Override
    public void dispose(T instance) {
        destroy(instance, null);
    }

    @Override
    public Set<InjectionPoint> getInjectionPoints() {
        Method producerMethod = getAnnotatedElement();
        AnnotatedMethod annotatedMethod = new ReflectiveAnnotatedMethod(producerMethod);

        List<AnnotatedParameter> annotatedParameters = annotatedMethod.getParameters();
        Set<InjectionPoint> injectionPoints = new LinkedHashSet<>();

        for (AnnotatedParameter annotatedParameter : annotatedParameters) {
            InjectionPoint injectionPoint = new MethodParameterInjectionPoint(annotatedParameter, annotatedMethod, this);
            injectionPoints.add(injectionPoint);
        }

        return unmodifiableSet(injectionPoints);
    }

    public AnnotatedMethod<T> getMethod() {
        return method;
    }

    @Override
    public AnnotatedMethod<T> getAnnotated() {
        return method;
    }
}