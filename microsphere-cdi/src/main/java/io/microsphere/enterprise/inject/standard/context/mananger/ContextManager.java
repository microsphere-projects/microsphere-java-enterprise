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
package io.microsphere.enterprise.inject.standard.context.mananger;

import io.microsphere.enterprise.inject.standard.context.AbstractContext;
import io.microsphere.enterprise.inject.util.Scopes;

import javax.enterprise.context.spi.Context;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.DeploymentException;
import javax.inject.Scope;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static io.microsphere.enterprise.inject.util.Contexts.isActiveContext;
import static io.microsphere.enterprise.inject.util.Contexts.validateActiveContext;
import static io.microsphere.text.FormatUtils.format;

/**
 * {@link Context} Manager with {@link Scope @Scopes}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.0
 */
public class ContextManager {

    /**
     * Synthetic Scopes from {@link BeforeBeanDiscovery#addScope(Class, boolean, boolean)}
     */
    private final Set<Class<? extends Annotation>> syntheticScopes;

    /**
     * Synthetic Normal Scopes from {@link BeforeBeanDiscovery#addScope(Class, boolean, boolean)}
     * <p>
     * The key is annotation type, the value is passivating or not
     */
    private final Map<Class<? extends Annotation>, Boolean> syntheticNormalScopes;

    private final Map<Class<? extends Annotation>, Context> contexts;

    public ContextManager() {
        this.syntheticScopes = new LinkedHashSet<>();
        this.syntheticNormalScopes = new LinkedHashMap<>();
        this.contexts = new HashMap<>();
    }

    public void addSyntheticScope(Class<? extends Annotation> scopeType, boolean normal, boolean passivating) {
        if (normal) {
            syntheticNormalScopes.put(scopeType, passivating);
        } else {
            syntheticScopes.add(scopeType);
        }
    }

    public boolean isScope(Class<? extends Annotation> annotationType) {
        return Scopes.isScope(annotationType) ||
                // Extensions
                syntheticScopes.contains(annotationType);
    }

    /**
     * @param context {@link Context}
     * @throws DeploymentException If the scope of specified {@link Context} has been registered
     */
    public void addContext(Context context) throws DeploymentException {
        Class<? extends Annotation> scope = context.getScope();
        validateActiveContext(context);
        Context oldContext = contexts.put(scope, context);
        if (isActiveContext(oldContext)) {
            // If more than one active context object exists for the given scope type, the container must throw an IllegalStateException.
            throw new IllegalStateException(format("There are more than one active context object exists for the given scope type[@{}]",
                    oldContext.getScope().getName()));
        }
    }

    public void addBean(Bean<?> bean) {
        Class<? extends Annotation> scope = bean.getScope();
        Context context = getContext(scope);
        if (context instanceof AbstractContext) {
            AbstractContext abstractContext = (AbstractContext) context;
            abstractContext.addBean(bean);
        }
    }

    public Context getContext(Class<? extends Annotation> scopeType) {
        Context context = contexts.get(scopeType);
        // If no active context object exists for the scope type, the container throws a ContextNotActiveException.
        validateActiveContext(context);
        return context;
    }

    public boolean isNormalScope(Class<? extends Annotation> annotationType) {
        return Scopes.isNormalScope(annotationType) ||
                // Extensions
                syntheticNormalScopes.containsKey(annotationType);
    }

    public boolean isPassivatingScope(Class<? extends Annotation> annotationType) {
        return Scopes.isPassivatingScope(annotationType) ||
                // Extensions
                syntheticNormalScopes.getOrDefault(annotationType, Boolean.FALSE);
    }

    public void destroy() {
        // TODO
    }
}
