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
package com.bluelotussoftware.jsf.compressor;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;
import com.sun.faces.io.FastStringWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import javax.faces.context.ResponseWriterWrapper;

/**
 *
 * @author John Yeary
 * @version 1.0.0
 */
public class HTMLCompressorWriter extends ResponseWriterWrapper {

    private static final Logger LOGGER = Logger.getLogger("HTMLCompressorWriter");
    private final ResponseWriter wrapped;
    private final HtmlCompressor compressor;
    private final FastStringWriter fsr;
    private final ResponseWriter clone;

    /**
     * Constructor that wraps a {@link ResponseWriter}.
     *
     * @param wrapped The {@link ResponseWriter} object to wrap.
     */
    public HTMLCompressorWriter(final ResponseWriter wrapped) {
        this.wrapped = wrapped;
        compressor = new HtmlCompressor();
        compressor.setEnabled(true);
        compressor.setRemoveIntertagSpaces(true);
        fsr = new FastStringWriter();
        clone = wrapped.cloneWithWriter(fsr);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseWriter getWrapped() {
        return wrapped;
    }

    /**
     * {@inheritDoc}
     *
     * @return A {@link ResponseWriter} wrapped by an
     * {@link HTMLCompressorWriter}.
     */
    @Override
    public ResponseWriter cloneWithWriter(final Writer writer) {
        LOGGER.fine("cloneWithWriter called...");
        return new HTMLCompressorWriter(getWrapped().cloneWithWriter(writer));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flush() throws IOException {
        clone.flush();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startDocument() throws IOException {
        clone.startDocument();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endDocument() throws IOException {
        clone.endDocument();
        LOGGER.fine("endDocument()");
        LOGGER.finest(fsr.getBuffer().toString());
        String html = compressor.compress(fsr.getBuffer().toString());
        LOGGER.finest(html);
        getWrapped().write(html);
        getWrapped().endDocument();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startElement(final String name, final UIComponent component) throws IOException {
        clone.startElement(name, component);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endElement(final String name) throws IOException {
        clone.endElement(name);
        String html = compressor.compress(fsr.getBuffer().toString());
        getWrapped().write(html);
        fsr.reset();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeAttribute(final String name, final Object value, final String property)
            throws IOException {
        clone.writeAttribute(name, value, property);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeURIAttribute(final String name, final Object value, final String property)
            throws IOException {
        clone.writeURIAttribute(name, value, property);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeComment(final Object comment) throws IOException {
        clone.writeComment(comment);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeText(final Object text, final String property) throws IOException {
        clone.writeText(text, property);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeText(final char[] text, final int off, final int len) throws IOException {
        clone.writeText(text, off, len);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(final char[] cbuf, final int off, final int len) throws IOException {
        clone.write(cbuf, off, len);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        clone.close();
    }

}
