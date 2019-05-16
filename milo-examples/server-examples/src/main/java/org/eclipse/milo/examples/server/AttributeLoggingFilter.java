/*
 * Copyright (c) 2019 the Eclipse Milo Authors
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.eclipse.milo.examples.server;

import org.eclipse.milo.opcua.sdk.server.nodes.AttributeFilter;
import org.eclipse.milo.opcua.sdk.server.nodes.AttributeFilterContext;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttributeLoggingFilter implements AttributeFilter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Object getAttribute(AttributeFilterContext ctx, AttributeId attributeId) {
        Object value = ctx.getAttribute(attributeId);

        // only log external reads
        if (ctx.getSession().isPresent()) {
            logger.info(
                "get nodeId={} attributeId={} value={}",
                ctx.getNode().getNodeId(), attributeId, value
            );
        }

        return value;
    }

    @Override
    public void setAttribute(AttributeFilterContext ctx, AttributeId attributeId, Object value) {
        // only log external writes
        if (ctx.getSession().isPresent()) {
            logger.info(
                "set nodeId={} attributeId={} value={}",
                ctx.getNode().getNodeId(), attributeId, value
            );
        }

        ctx.setAttribute(attributeId, value);
    }

}