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
package io.microsphere.enterprise.interceptor.util;

import io.microsphere.enterprise.interceptor.AnnotatedInterceptor;
import io.microsphere.enterprise.interceptor.ExternalInterceptor;
import io.microsphere.enterprise.interceptor.Logging;
import io.microsphere.enterprise.interceptor.Monitored;
import org.junit.Test;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Priority;
import javax.interceptor.AroundConstruct;
import javax.interceptor.AroundInvoke;
import javax.interceptor.AroundTimeout;
import javax.interceptor.Interceptor;
import javax.interceptor.InterceptorBinding;
import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import static io.microsphere.enterprise.interceptor.util.InterceptorUtils.AROUND_CONSTRUCT_ANNOTATION_TYPE;
import static io.microsphere.enterprise.interceptor.util.InterceptorUtils.AROUND_INVOKE_ANNOTATION_TYPE;
import static io.microsphere.enterprise.interceptor.util.InterceptorUtils.AROUND_TIMEOUT_ANNOTATION_TYPE;
import static io.microsphere.enterprise.interceptor.util.InterceptorUtils.INTERCEPTOR_ANNOTATION_TYPE;
import static io.microsphere.enterprise.interceptor.util.InterceptorUtils.INTERCEPTOR_BINDING_ANNOTATION_TYPE;
import static io.microsphere.enterprise.interceptor.util.InterceptorUtils.POST_CONSTRUCT_ANNOTATION_TYPE;
import static io.microsphere.enterprise.interceptor.util.InterceptorUtils.PRE_DESTROY_ANNOTATION_TYPE;
import static io.microsphere.enterprise.interceptor.util.InterceptorUtils.isAnnotatedInterceptorBinding;
import static io.microsphere.enterprise.interceptor.util.InterceptorUtils.isAroundConstructMethod;
import static io.microsphere.enterprise.interceptor.util.InterceptorUtils.isAroundInvokeMethod;
import static io.microsphere.enterprise.interceptor.util.InterceptorUtils.isAroundTimeoutMethod;
import static io.microsphere.enterprise.interceptor.util.InterceptorUtils.isInterceptorClass;
import static io.microsphere.enterprise.interceptor.util.InterceptorUtils.isPostConstructMethod;
import static io.microsphere.enterprise.interceptor.util.InterceptorUtils.isPreDestroyMethod;
import static io.microsphere.enterprise.interceptor.util.InterceptorUtils.sortInterceptors;
import static io.microsphere.enterprise.interceptor.util.InterceptorUtils.unwrap;
import static io.microsphere.enterprise.interceptor.util.InterceptorUtils.validateInterceptorClass;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * {@link InterceptorUtils} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.0
 */
public class InterceptorUtilsTest {

    @Test
    public void testConstants() {
        assertEquals(Interceptor.class, INTERCEPTOR_ANNOTATION_TYPE);
        assertEquals(InterceptorBinding.class, INTERCEPTOR_BINDING_ANNOTATION_TYPE);
        assertEquals(AroundInvoke.class, AROUND_INVOKE_ANNOTATION_TYPE);
        assertEquals(AroundTimeout.class, AROUND_TIMEOUT_ANNOTATION_TYPE);
        assertEquals(AroundConstruct.class, AROUND_CONSTRUCT_ANNOTATION_TYPE);
        assertEquals(PostConstruct.class, POST_CONSTRUCT_ANNOTATION_TYPE);
        assertEquals(PreDestroy.class, PRE_DESTROY_ANNOTATION_TYPE);
    }

    @Test
    public void testIsInterceptorClass() {
        isInterceptorClass(ExternalInterceptor.class);
    }

    @Test
    public void testSortInterceptors() {
        List<Class<?>> interceptorClasses = new LinkedList<>(asList(A.class, B.class));
        interceptorClasses = sortInterceptors(interceptorClasses);
        assertEquals(B.class, interceptorClasses.get(0));
        assertEquals(A.class, interceptorClasses.get(1));
    }

    @Test
    public void testUnwrap() {
        testOnError(() -> unwrap(A.class), Exception.class);
        assertNotNull(unwrap(String.class));
    }

    @Test
    public void testValidateInterceptorClass() {
        validateInterceptorClass(ExternalInterceptor.class);
        testOnError(() -> validateInterceptorClass(String.class), IllegalStateException.class);
        testOnError(() -> validateInterceptorClass(A.class), IllegalStateException.class);
        testOnError(() -> validateInterceptorClass(B.class), IllegalStateException.class);
    }

    protected void testOnError(Runnable runnable, Class<? extends Throwable> errorClass) {
        Throwable throwable = null;
        try {
            runnable.run();
        } catch (Throwable e) {
            throwable = e;
        }
        assertTrue(errorClass.isAssignableFrom(throwable.getClass()));
    }

    @Test
    public void testIsAnnotatedInterceptorBinding() {
        assertTrue(isAnnotatedInterceptorBinding(Logging.class));
        assertTrue(isAnnotatedInterceptorBinding(Monitored.class));
        assertFalse(isAnnotatedInterceptorBinding(Override.class));
    }

    @Test
    public void testIsAroundInvokeMethod() throws Throwable {
        Method method = AnnotatedInterceptor.class.getMethod("intercept", InvocationContext.class);
        assertTrue(isAroundInvokeMethod(method));
    }

    @Test
    public void testIsAroundTimeoutMethod() throws Throwable {
        Method method = AnnotatedInterceptor.class.getMethod("interceptTimeout", InvocationContext.class);
        assertTrue(isAroundTimeoutMethod(method));
    }

    @Test
    public void testIsAroundConstructMethod() throws Throwable {
        Method method = AnnotatedInterceptor.class.getMethod("interceptConstruct", InvocationContext.class);
        assertTrue(isAroundConstructMethod(method));
    }

    @Test
    public void testIsPostConstructMethod() throws Throwable {
        Method method = AnnotatedInterceptor.class.getMethod("interceptPostConstruct", InvocationContext.class);
        assertTrue(isPostConstructMethod(method));
    }

    @Test
    public void testIsPreDestroyMethod() throws Throwable {
        Method method = AnnotatedInterceptor.class.getMethod("interceptPreDestroy", InvocationContext.class);
        assertTrue(isPreDestroyMethod(method));
    }


    @Priority(2)
    @Interceptor
    abstract class A {
    }

    @Priority(1)
    @Interceptor
    final class B {
    }
}
