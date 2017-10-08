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

import java.util.Iterator;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;

/**
 * An implementation of {@link RenderKitFactory} that provides a
 * {@link HtmlCompressorRenderKitWrapper} as necessary.
 *
 * @author John Yeary
 * @version 1.0.0
 */
public class HtmlCompressorRenderKitFactory extends RenderKitFactory {

    private static final Logger LOGGER = Logger.getLogger("HtmlCompressorRenderKitFactory");
    private final RenderKitFactory wrapped;

    /**
     * Constructor that wraps a {@link RenderKitFactory}.
     *
     * @param wrapped The {@link RenderKitFactory} object to wrap.
     */
    public HtmlCompressorRenderKitFactory(final RenderKitFactory wrapped) {
        this.wrapped = wrapped;
        LOGGER.fine("HtmlCompressorRenderKitFactory Loaded...");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addRenderKit(final String renderKitId, final RenderKit renderKit) {
        wrapped.addRenderKit(renderKitId, renderKit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RenderKit getRenderKit(final FacesContext context, final String renderKitId) {
        RenderKit renderKit = wrapped.getRenderKit(context, renderKitId);
        return (HTML_BASIC_RENDER_KIT.equals(renderKitId)) ? new HtmlCompressorRenderKitWrapper(renderKit) : renderKit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<String> getRenderKitIds() {
        return wrapped.getRenderKitIds();
    }

}
