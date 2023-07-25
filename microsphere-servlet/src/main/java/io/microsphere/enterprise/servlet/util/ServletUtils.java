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
package io.microsphere.enterprise.servlet.util;

import io.microsphere.util.BaseUtils;
import io.microsphere.util.Version;

import javax.servlet.Servlet;



/**
 * The utilities class for {@link Servlet}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @see Servlet
 * @since 1.0.0
 */
public abstract class ServletUtils extends BaseUtils {

    /**
     * Servlet 2.3 mainly supports Servlet's Listeners
     *
     * @see javax.servlet.ServletContextListener
     */
    public static final Version SERVLET_23 = Version.of(2, 3);

    /**
     * Servlet 2.4 mainly supports annotations
     *
     * @see javax.annotation.PostConstruct
     */
    public static final Version SERVLET_24 = Version.of(2, 4);

    /**
     * Servlet 2.5
     */
    public static final Version SERVLET_25 = Version.of(2, 5);

    /**
     * Servlet 3.0 mainly supports Async Servlet, Servlet Annotations, pluggable Servlet components and so on
     *
     * @see javax.servlet.AsyncContext
     * @see javax.servlet.annotation.WebServlet
     * @see javax.servlet.ServletContext#addServlet(String, Servlet)
     */
    public static final Version SERVLET_30 = Version.of(3, 0);

    /**
     * Servlet 3.1 mainly supports non-blocking Servlet, WebSocket protocol Upgrade
     *
     * @see javax.servlet.ReadListener
     * @see javax.servlet.http.HttpUpgradeHandler
     */
    public static final Version SERVLET_31 = Version.of(3, 1);

    /**
     * Servlet 4.0 mainly supports HTTP/2
     *
     * @see javax.servlet.http.PushBuilder
     */
    public static final Version SERVLET_40 = Version.of(4, 0);
}
