/*
 * Copyright 2017 Blue Lotus Software, LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bluelotussofware.jsf.faces.render;

import com.bluelotussoftware.jsf.compressor.HTMLCompressorWriter;
import java.io.Writer;
import java.util.logging.Logger;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitWrapper;

/**
 * A {@link RenderKitWrapper} that provides a wrapped
 * {@link HTMLCompressorWriter}.
 *
 * @author John Yeary
 * @version 1.0.0
 */
public class HtmlCompressorRenderKitWrapper extends RenderKitWrapper {

    private static final Logger LOGGER = Logger.getLogger("HtmlCompressorRenderKitWrapper");
    private final RenderKit wrapped;

    /**
     * Constructor that takes a {@link RenderKit} and wraps it.
     *
     * @param wrapped The {@link RenderKit} object to wrap.
     */
    public HtmlCompressorRenderKitWrapper(final RenderKit wrapped) {
        this.wrapped = wrapped;
        LOGGER.fine("HtmlCompressorRenderKitWrapper called...");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RenderKit getWrapped() {
        return wrapped;
    }

    /**
     * {@inheritDoc}
     *
     * @return A {@link ResponseWriter} wrapped by a
     * {@link HTMLCompressorWriter}.
     */
    @Override
    public ResponseWriter createResponseWriter(final Writer writer, final String contentTypeList, final String characterEncoding) {
        LOGGER.fine("createResponseWriter() called...");
        ResponseWriter responseWriter = getWrapped().createResponseWriter(writer, contentTypeList, characterEncoding);
        HTMLCompressorWriter htmlcw = new HTMLCompressorWriter(responseWriter);
        return htmlcw;
    }

}
