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

import io.microsphere.enterprise.inject.standard.beans.manager.StandardBeanManager;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.CreationException;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.Producer;
import java.lang.reflect.Field;
import java.util.Set;

import static io.microsphere.reflect.MemberUtils.isStatic;
import static java.util.Collections.emptySet;

/**
 * {@link Producer} implementation for Producer {@link AnnotatedField Method}
 *
 * @param <T> The class of object produced by the producer
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.0
 */
public class AnnotatedFieldProducer<T, X> extends AbstractProducer<T, X> {

    private final AnnotatedField<T> producerField;

    public AnnotatedFieldProducer(AnnotatedField<T> producerField, Bean<X> declaringBean, StandardBeanManager standardBeanManager) {
        super(declaringBean, standardBeanManager);
        this.producerField = producerField;
    }

    @Override
    public T produce(CreationalContext<T> ctx) {
        Field field = producerField.getJavaMember();

        Object instance = null;

        final T beanInstance;
        try {
            if (!isStatic(field)) {
                instance = getDeclaringBeanInstance(ctx);
            }
            beanInstance = (T) field.get(instance);
        } catch (Throwable e) {
            throw new CreationException(e);
        }
        return beanInstance;
    }

    public AnnotatedField<T> getProducerField() {
        return producerField;
    }

    @Override
    protected Set<InjectionPoint> resolveInjectionPoints() {
        return emptySet();
    }
}
