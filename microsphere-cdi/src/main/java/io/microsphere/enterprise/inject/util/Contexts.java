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

import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import java.lang.reflect.Type;

import static io.microsphere.reflect.TypeUtils.resolveActualTypeArgument;
import static io.microsphere.reflect.TypeUtils.resolveActualTypeArgumentClass;
import static io.microsphere.text.FormatUtils.format;

/**
 * The utilities class for {@link Context}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.0
 */
public abstract class Contexts {

    public static <T> Class<T> getBeanClass(Contextual<T> contextual) {
        Class<?> contextualClass = contextual.getClass();
        return resolveActualTypeArgumentClass(contextualClass, Contextual.class, 0);
    }

    public static <T> Type getBeanType(Contextual<T> contextual) {
        Class<?> contextualClass = contextual.getClass();
        return resolveActualTypeArgument(contextualClass, Contextual.class, 0);
    }

    public static boolean isActiveContext(Context context) {
        return context != null && !context.isActive();
    }

    /**
     * Validate the given {@Context context} is active or not.
     *
     * @param context {@link Context}
     * @throws ContextNotActiveException If no active context object exists for the scope type, the container throws a {@llink ContextNotActiveException}.
     */
    public static void validateActiveContext(Context context) throws ContextNotActiveException {
        if (!isActiveContext(context)) {
            throw new ContextNotActiveException(format("No active context object exists for the scope type[@{}]!", context.getScope().getName()));
        }
    }

}

