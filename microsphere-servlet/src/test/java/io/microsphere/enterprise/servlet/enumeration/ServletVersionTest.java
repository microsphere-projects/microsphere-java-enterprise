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
package io.microsphere.enterprise.servlet.enumeration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockServletContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link ServletVersion} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @see ServletVersion
 * @since 1.0.0
 */
public class ServletVersionTest {

    private MockServletContext servletContext;

    @BeforeEach
    public void init() {
        servletContext = new MockServletContext();
        servletContext.setMajorVersion(3);
        servletContext.setMinorVersion(0);
    }

    @Test
    public void test() {
        assertTrue(ServletVersion.SERVLET_2_3.lt(servletContext));
        assertTrue(ServletVersion.SERVLET_2_3.le(servletContext));
        assertFalse(ServletVersion.SERVLET_2_3.gt(servletContext));
        assertFalse(ServletVersion.SERVLET_2_3.ge(servletContext));
        assertFalse(ServletVersion.SERVLET_2_3.eq(servletContext));

        assertTrue(ServletVersion.SERVLET_2_4.lt(servletContext));
        assertTrue(ServletVersion.SERVLET_2_4.le(servletContext));
        assertFalse(ServletVersion.SERVLET_2_4.gt(servletContext));
        assertFalse(ServletVersion.SERVLET_2_4.ge(servletContext));
        assertFalse(ServletVersion.SERVLET_2_4.eq(servletContext));

        assertTrue(ServletVersion.SERVLET_2_5.lt(servletContext));
        assertTrue(ServletVersion.SERVLET_2_5.le(servletContext));
        assertFalse(ServletVersion.SERVLET_2_5.gt(servletContext));
        assertFalse(ServletVersion.SERVLET_2_5.ge(servletContext));
        assertFalse(ServletVersion.SERVLET_2_5.eq(servletContext));

        assertFalse(ServletVersion.SERVLET_3_0.lt(servletContext));
        assertTrue(ServletVersion.SERVLET_3_0.le(servletContext));
        assertFalse(ServletVersion.SERVLET_3_0.gt(servletContext));
        assertTrue(ServletVersion.SERVLET_3_0.ge(servletContext));
        assertTrue(ServletVersion.SERVLET_3_0.eq(servletContext));

        assertFalse(ServletVersion.SERVLET_3_1.lt(servletContext));
        assertFalse(ServletVersion.SERVLET_3_1.le(servletContext));
        assertTrue(ServletVersion.SERVLET_3_1.gt(servletContext));
        assertTrue(ServletVersion.SERVLET_3_1.ge(servletContext));
        assertFalse(ServletVersion.SERVLET_3_1.eq(servletContext));

        assertFalse(ServletVersion.SERVLET_4_0.lt(servletContext));
        assertFalse(ServletVersion.SERVLET_4_0.le(servletContext));
        assertTrue(ServletVersion.SERVLET_4_0.gt(servletContext));
        assertTrue(ServletVersion.SERVLET_4_0.ge(servletContext));
        assertFalse(ServletVersion.SERVLET_4_0.eq(servletContext));
    }

}
