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

import io.microsphere.util.Version;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;

import static io.microsphere.util.Version.of;

/**
 * Servlet Version
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @see Servlet
 * @see ServletContext
 * @see Version
 * @since 1.0.0
 */
public enum ServletVersion {

    /**
     * Servlet 2.3 mainly supports Servlet's Listeners
     *
     * @see javax.servlet.ServletContextListener
     */
    SERVLET_2_3(2, 3),

    /**
     * Servlet 2.4 mainly supports annotations
     *
     * @see javax.annotation.PostConstruct
     */
    SERVLET_2_4(2, 4),

    /**
     * Servlet 2.5
     */
    SERVLET_2_5(2, 5),

    /**
     * Servlet 3.0 mainly supports Async Servlet, Servlet Annotations, pluggable Servlet components and so on
     *
     * @see javax.servlet.AsyncContext
     * @see javax.servlet.annotation.WebServlet
     * @see javax.servlet.ServletContext#addServlet(String, Servlet)
     */
    SERVLET_3_0(3, 0),

    /**
     * Servlet 3.1 mainly supports non-blocking Servlet, WebSocket protocol Upgrade
     *
     * @see javax.servlet.ReadListener
     * @see javax.servlet.http.HttpUpgradeHandler
     */
    SERVLET_3_1(3, 1),

    /**
     * Servlet 4.0 mainly supports HTTP/2
     *
     * @see javax.servlet.http.PushBuilder
     */
    SERVLET_4_0(4, 0);

    private final Version version;

    ServletVersion(int majorVersion, int minorVersion) {
        this.version = of(majorVersion, minorVersion);
    }

    public int getMajor() {
        return version.getMajor();
    }

    public int getMinor() {
        return version.getMinor();
    }

    public boolean isGreaterThan(ServletContext servletContext) {
        Version that = getVersion(servletContext);
        return version.isGreaterThan(that);
    }

    public boolean isGreaterOrEqual(ServletContext servletContext) {
        Version that = getVersion(servletContext);
        return version.isGreaterOrEqual(that);
    }

    public boolean isLessThan(ServletContext servletContext) {
        Version that = getVersion(servletContext);
        return version.isLessThan(that);
    }

    public boolean isLessOrEqual(ServletContext servletContext) {
        Version that = getVersion(servletContext);
        return version.isLessOrEqual(that);
    }

    public boolean equals(Version that) {
        return version.equals(that);
    }

    /**
     * Get the {@link Version Servlet version} of Current {@link ServletContext} in runtime
     *
     * @param servletContext {@link ServletContext}
     * @return non-null
     */
    public static Version getVersion(ServletContext servletContext) {
        int majorVersion = servletContext.getMajorVersion();
        int minorVersion = servletContext.getMinorVersion();
        return of(majorVersion, minorVersion);
    }

    /**
     * Get the {@link ServletVersion Servlet version} of Current {@link ServletContext} in runtime
     *
     * @param servletContext {@link ServletContext}
     * @return non-null
     */
    public static ServletVersion valueOf(ServletContext servletContext) {
        int majorVersion = servletContext.getMajorVersion();
        int minorVersion = servletContext.getMinorVersion();
        ServletVersion version = null;
        for (ServletVersion servletVersion : ServletVersion.values()) {
            if (servletVersion.getMajor() == majorVersion && servletVersion.getMinor() == minorVersion) {
                version = servletVersion;
                break;
            }
        }
        if (version == null) {
            throw new IllegalArgumentException("No ServletVersion enum for Servlet " + majorVersion + "." + minorVersion);
        }
        return version;
    }
}
