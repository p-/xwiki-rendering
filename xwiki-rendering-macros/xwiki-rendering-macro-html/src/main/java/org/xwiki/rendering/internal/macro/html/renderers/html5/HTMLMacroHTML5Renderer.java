/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.rendering.internal.macro.html.renderers.html5;

import javax.inject.Inject;
import javax.inject.Named;

import org.xwiki.component.annotation.Component;
import org.xwiki.component.annotation.InstantiationStrategy;
import org.xwiki.component.descriptor.ComponentInstantiationStrategy;
import org.xwiki.rendering.internal.macro.html.AbstractHTMLMacroRenderer;
import org.xwiki.rendering.internal.renderer.html5.HTML5ChainingRenderer;
import org.xwiki.rendering.internal.renderer.xhtml.image.XHTMLImageRenderer;
import org.xwiki.rendering.internal.renderer.xhtml.link.XHTMLLinkRenderer;
import org.xwiki.rendering.renderer.AbstractChainingPrintRenderer;
import org.xwiki.stability.Unstable;

/**
 * Renderer that generates HTML5 from a XDOM resulting from the parsing of text containing HTML mixed with
 * wiki syntax.
 *
 * @version $Id$
 * @since 11.4RC1
 */
@Component
@Named("htmlmacro+html/5.0")
@InstantiationStrategy(ComponentInstantiationStrategy.PER_LOOKUP)
@Unstable
public class HTMLMacroHTML5Renderer extends AbstractHTMLMacroRenderer
{
    /**
     * To render link events into XHTML. This is done so that it's pluggable because link rendering depends on how the
     * underlying system wants to handle it. For example for XWiki we check if the document exists, we get the document
     * URL, etc.
     */
    @Inject
    private XHTMLLinkRenderer linkRenderer;

    /**
     * To render image events into XHTML. This is done so that it's pluggable because image rendering depends on how the
     * underlying system wants to handle it. For example for XWiki we check if the image exists as a document
     * attachments, we get its URL, etc.
     */
    @Inject
    private XHTMLImageRenderer imageRenderer;

    @Override
    protected AbstractChainingPrintRenderer getSyntaxRenderer()
    {
        return new HTML5ChainingRenderer(this.linkRenderer, this.imageRenderer, getListenerChain());
    }
}