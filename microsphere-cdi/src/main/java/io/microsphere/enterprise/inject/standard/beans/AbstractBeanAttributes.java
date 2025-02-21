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
package io.microsphere.enterprise.inject.standard.beans;

import io.microsphere.enterprise.inject.util.Qualifiers;
import org.slf4j.Logger;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanAttributes;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.StringJoiner;

import static io.microsphere.enterprise.inject.util.Beans.getBeanTypes;
import static io.microsphere.enterprise.inject.util.Scopes.getScopeType;
import static io.microsphere.enterprise.inject.util.Stereotypes.getStereotypeTypes;
import static io.microsphere.util.AnnotationUtils.isAnnotationPresent;
import static java.util.Objects.requireNonNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Abstract {@link BeanAttributes} implementation
 *
 * @param <T> the class of the bean instance
 * @param <A> the type of annotated element
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.0
 */
public abstract class AbstractBeanAttributes<A extends AnnotatedElement, T> implements BeanAttributes<T> {

    protected final Logger logger = getLogger(this.getClass());

    private final A annotatedElement;

    private final Class<?> beanClass;

    private Set<Type> beanTypes;

    private Set<Annotation> qualifiers;

    private String beanName;

    private Class<? extends Annotation> scopeType;

    private Set<Class<? extends Annotation>> stereotypeTypes;

    private boolean alternative;

    private final AnnotatedType<T> beanType;

    private boolean vetoed;

    public AbstractBeanAttributes(A annotatedElement, AnnotatedType<T> beanType) {
        requireNonNull(annotatedElement, "The 'annotatedElement' argument must not be null!");
        requireNonNull(beanType, "The 'beanType' argument must not be null!");
        validate(annotatedElement);
        this.annotatedElement = annotatedElement;
        this.beanClass = beanType.getJavaClass();
        this.beanTypes = getBeanTypes(beanClass);
        this.qualifiers = Qualifiers.getQualifiers(annotatedElement);
        this.scopeType = getScopeType(annotatedElement);
        this.beanName = getBeanName(annotatedElement);
        this.stereotypeTypes = getStereotypeTypes(annotatedElement);
        this.alternative = isAnnotationPresent(annotatedElement, Alternative.class);
        this.beanType = beanType;
        this.vetoed = false;
    }

    public void setBeanAttributes(BeanAttributes<T> beanAttributes) {
        this.beanTypes = beanAttributes.getTypes();
        this.qualifiers = beanAttributes.getQualifiers();
        this.scopeType = beanAttributes.getScope();
        this.beanName = beanAttributes.getName();
        this.stereotypeTypes = beanAttributes.getStereotypes();
        this.alternative = beanAttributes.isAlternative();
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    @Override
    public Set<Type> getTypes() {
        return beanTypes;
    }

    @Override
    public Set<Annotation> getQualifiers() {
        return qualifiers;
    }

    @Override
    public Class<? extends Annotation> getScope() {
        return scopeType;
    }

    @Override
    public String getName() {
        return beanName;
    }

    @Override
    public Set<Class<? extends Annotation>> getStereotypes() {
        return stereotypeTypes;
    }

    @Override
    public boolean isAlternative() {
        return alternative;
    }

    public A getAnnotatedElement() {
        return annotatedElement;
    }

    public AnnotatedType getBeanType() {
        return beanType;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", getClass().getSimpleName() + "[", "]")
                .add("annotatedElement=" + getAnnotatedElement())
                .add("beanClass=" + getBeanClass())
                .add("types=" + getTypes())
                .add("qualifiers=" + getQualifiers())
                .add("beanName='" + getName() + "'")
                .add("scopeType=" + getScope())
                .add("stereotypeTypes=" + getStereotypes())
                .add("alternative=" + isAlternative())
                .toString();
    }

    public final void veto() {
        vetoed = true;
    }

    public final boolean isVetoed() {
        return vetoed;
    }

    // Abstract methods
    protected abstract String getBeanName(A annotatedElement);

    protected abstract void validate(A annotatedElement);

    public abstract Annotated getAnnotated();

}
