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
package io.microsphere.enterprise.inject.standard.producer;

import io.microsphere.enterprise.inject.standard.annotation.ReflectiveAnnotatedType;
import io.microsphere.enterprise.inject.standard.beans.AbstractBeanAttributes;
import io.microsphere.enterprise.inject.util.Beans;
import io.microsphere.enterprise.inject.util.Producers;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.BeanAttributes;
import java.lang.reflect.Method;

/**
 * {@link Produces Producer} {@link Method} {@link BeanAttributes} implementation
 *
 * @param <T> the class of the bean instance
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.0
 */
public class ProducerMethodBeanAttributes<T> extends AbstractBeanAttributes<Method, T> {

    private final AnnotatedMethod annotatedMethod;

    public ProducerMethodBeanAttributes(AnnotatedMethod annotatedMethod) {
        super(annotatedMethod.getJavaMember(), new ReflectiveAnnotatedType<>(annotatedMethod.getJavaMember().getReturnType()));
        this.annotatedMethod = annotatedMethod;
    }

    @Override
    protected String getBeanName(Method producerMethod) {
        return Beans.getBeanName(producerMethod);
    }

    @Override
    protected void validate(Method producerMethod) {
        Producers.validateProducerMethod(producerMethod);
    }

    @Override
    public AnnotatedMethod getAnnotated() {
        return annotatedMethod;
    }
}
