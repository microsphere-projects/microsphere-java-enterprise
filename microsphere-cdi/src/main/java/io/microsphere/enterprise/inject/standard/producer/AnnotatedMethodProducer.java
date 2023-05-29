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
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.Producer;
import java.lang.reflect.Method;
import java.util.Set;

import static org.geektimes.commons.reflect.util.MemberUtils.isStatic;
import static io.microsphere.enterprise.inject.util.Injections.getMethodParameterInjectionPoints;

/**
 * {@link Producer} implementation for Producer {@link AnnotatedMethod Method}
 *
 * @param <T> The class of object produced by the producer
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.0
 */
public class AnnotatedMethodProducer<T, X> extends AbstractProducer<T, X> {

    private final AnnotatedMethod<T> producerMethod;

    public AnnotatedMethodProducer(AnnotatedMethod<T> producerMethod, Bean<X> declaringBean, StandardBeanManager standardBeanManager) {
        super(declaringBean, standardBeanManager);
        this.producerMethod = producerMethod;
    }

    @Override
    public T produce(CreationalContext<T> ctx) {
        Method method = producerMethod.getJavaMember();

        Object[] injectedArguments = resolveInjectedArguments(ctx);

        Object instance = null;

        final T beanInstance;
        try {
            if (!isStatic(method)) {
                instance = getDeclaringBeanInstance(ctx);
            }
            beanInstance = (T) method.invoke(instance, injectedArguments);
        } catch (Throwable e) {
            throw new CreationException(e);
        }
        return beanInstance;
    }

    public AnnotatedMethod<T> getProducerMethod() {
        return producerMethod;
    }

    @Override
    protected Set<InjectionPoint> resolveInjectionPoints() {
        return (Set) getMethodParameterInjectionPoints(producerMethod, getDeclaringBean());
    }
}