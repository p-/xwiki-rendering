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
package org.xwiki.rendering.listener.chaining;

import java.util.Map;

import org.xwiki.rendering.listener.Format;
import org.xwiki.rendering.listener.HeaderLevel;
import org.xwiki.rendering.listener.ListType;
import org.xwiki.rendering.listener.MetaData;
import org.xwiki.rendering.listener.QueueListener;
import org.xwiki.rendering.listener.QueueListener.Event;
import org.xwiki.rendering.listener.reference.ResourceReference;
import org.xwiki.rendering.syntax.Syntax;

/**
 * Stores events without emitting them back in order to accumulate them and to provide a lookahead feature. The
 * lookahead depth is configurable.
 *
 * @version $Id$
 * @since 1.8RC1
 */
public class LookaheadChainingListener extends AbstractChainingListener
{
    private QueueListener previousEvents = new QueueListener();

    private int lookaheadDepth;

    public LookaheadChainingListener(ListenerChain listenerChain, int lookaheadDepth)
    {
        setListenerChain(listenerChain);
        this.lookaheadDepth = lookaheadDepth;
    }

    public Event getNextEvent()
    {
        return getNextEvent(1);
    }

    public Event getNextEvent(int depth)
    {
        return this.previousEvents.getEvent(depth);
    }

    @Override
    public void beginDefinitionDescription()
    {
        this.previousEvents.beginDefinitionDescription();
        firePreviousEvent();
    }

    /**
     * {@inheritDoc}
     *
     * @since 2.0RC1
     */
    @Override
    public void beginDefinitionList(Map<String, String> parameters)
    {
        this.previousEvents.beginDefinitionList(parameters);
        firePreviousEvent();
    }

    @Override
    public void beginDefinitionTerm()
    {
        this.previousEvents.beginDefinitionTerm();
        firePreviousEvent();
    }

    /**
     * {@inheritDoc}
     *
     * @since 3.0M2
     */
    @Override
    public void beginDocument(MetaData metaData)
    {
        this.previousEvents.beginDocument(metaData);
        flush();
    }

    @Override
    public void beginGroup(Map<String, String> parameters)
    {
        this.previousEvents.beginGroup(parameters);
        firePreviousEvent();
    }

    @Override
    public void beginFormat(Format format, Map<String, String> parameters)
    {
        this.previousEvents.beginFormat(format, parameters);
        firePreviousEvent();
    }

    @Override
    public void beginHeader(HeaderLevel level, String id, Map<String, String> parameters)
    {
        this.previousEvents.beginHeader(level, id, parameters);
        firePreviousEvent();
    }

    /**
     * {@inheritDoc}
     *
     * @since 2.5RC1
     */
    @Override
    public void beginLink(ResourceReference reference, boolean isFreeStandingURI, Map<String, String> parameters)
    {
        this.previousEvents.beginLink(reference, isFreeStandingURI, parameters);
        firePreviousEvent();
    }

    @Override
    public void beginList(ListType listType, Map<String, String> parameters)
    {
        this.previousEvents.beginList(listType, parameters);
        firePreviousEvent();
    }

    @Override
    public void beginListItem()
    {
        this.previousEvents.beginListItem();
        firePreviousEvent();
    }

    @Override
    public void beginMacroMarker(String name, Map<String, String> parameters, String content, boolean isInline)
    {
        this.previousEvents.beginMacroMarker(name, parameters, content, isInline);
        firePreviousEvent();
    }

    /**
     * {@inheritDoc}
     *
     * @since 3.0M2
     */
    @Override
    public void beginMetaData(MetaData metadata)
    {
        this.previousEvents.beginMetaData(metadata);
        firePreviousEvent();
    }

    @Override
    public void beginParagraph(Map<String, String> parameters)
    {
        this.previousEvents.beginParagraph(parameters);
        firePreviousEvent();
    }

    @Override
    public void beginQuotation(Map<String, String> parameters)
    {
        this.previousEvents.beginQuotation(parameters);
        firePreviousEvent();
    }

    @Override
    public void beginQuotationLine()
    {
        this.previousEvents.beginQuotationLine();
        firePreviousEvent();
    }

    @Override
    public void beginSection(Map<String, String> parameters)
    {
        this.previousEvents.beginSection(parameters);
        firePreviousEvent();
    }

    @Override
    public void beginTable(Map<String, String> parameters)
    {
        this.previousEvents.beginTable(parameters);
        firePreviousEvent();
    }

    @Override
    public void beginTableCell(Map<String, String> parameters)
    {
        this.previousEvents.beginTableCell(parameters);
        firePreviousEvent();
    }

    @Override
    public void beginTableHeadCell(Map<String, String> parameters)
    {
        this.previousEvents.beginTableHeadCell(parameters);
        firePreviousEvent();
    }

    @Override
    public void beginTableRow(Map<String, String> parameters)
    {
        this.previousEvents.beginTableRow(parameters);
        firePreviousEvent();
    }

    @Override
    public void endDefinitionDescription()
    {
        this.previousEvents.endDefinitionDescription();
        firePreviousEvent();
    }

    /**
     * {@inheritDoc}
     *
     * @since 2.0RC1
     */
    @Override
    public void endDefinitionList(Map<String, String> parameters)
    {
        this.previousEvents.endDefinitionList(parameters);
        firePreviousEvent();
    }

    @Override
    public void endDefinitionTerm()
    {
        this.previousEvents.endDefinitionTerm();
        firePreviousEvent();
    }

    /**
     * {@inheritDoc}
     *
     * @since 3.0M2
     */
    @Override
    public void endDocument(MetaData metaData)
    {
        this.previousEvents.endDocument(metaData);
        flush();
    }

    @Override
    public void endGroup(Map<String, String> parameters)
    {
        this.previousEvents.endGroup(parameters);
        firePreviousEvent();
    }

    @Override
    public void endFormat(Format format, Map<String, String> parameters)
    {
        this.previousEvents.endFormat(format, parameters);
        firePreviousEvent();
    }

    @Override
    public void endHeader(HeaderLevel level, String id, Map<String, String> parameters)
    {
        this.previousEvents.endHeader(level, id, parameters);
        firePreviousEvent();
    }

    /**
     * {@inheritDoc}
     *
     * @since 2.5RC1
     */
    @Override
    public void endLink(ResourceReference reference, boolean isFreeStandingURI, Map<String, String> parameters)
    {
        this.previousEvents.endLink(reference, isFreeStandingURI, parameters);
        firePreviousEvent();
    }

    @Override
    public void endList(ListType listType, Map<String, String> parameters)
    {
        this.previousEvents.endList(listType, parameters);
        firePreviousEvent();
    }

    @Override
    public void endListItem()
    {
        this.previousEvents.endListItem();
        firePreviousEvent();
    }

    @Override
    public void endMacroMarker(String name, Map<String, String> parameters, String content, boolean isInline)
    {
        this.previousEvents.endMacroMarker(name, parameters, content, isInline);
        firePreviousEvent();
    }

    /**
     * {@inheritDoc}
     *
     * @since 3.0M2
     */
    @Override
    public void endMetaData(MetaData metadata)
    {
        this.previousEvents.endMetaData(metadata);
        firePreviousEvent();
    }

    @Override
    public void endParagraph(Map<String, String> parameters)
    {
        this.previousEvents.endParagraph(parameters);
        firePreviousEvent();
    }

    @Override
    public void endQuotation(Map<String, String> parameters)
    {
        this.previousEvents.endQuotation(parameters);
        firePreviousEvent();
    }

    @Override
    public void endQuotationLine()
    {
        this.previousEvents.endQuotationLine();
        firePreviousEvent();
    }

    @Override
    public void endSection(Map<String, String> parameters)
    {
        this.previousEvents.endSection(parameters);
        firePreviousEvent();
    }

    @Override
    public void endTable(Map<String, String> parameters)
    {
        this.previousEvents.endTable(parameters);
        firePreviousEvent();
    }

    @Override
    public void endTableCell(Map<String, String> parameters)
    {
        this.previousEvents.endTableCell(parameters);
        firePreviousEvent();
    }

    @Override
    public void endTableHeadCell(Map<String, String> parameters)
    {
        this.previousEvents.endTableHeadCell(parameters);
        firePreviousEvent();
    }

    @Override
    public void endTableRow(Map<String, String> parameters)
    {
        this.previousEvents.endTableRow(parameters);
        firePreviousEvent();
    }

    @Override
    public void onRawText(String text, Syntax syntax)
    {
        this.previousEvents.onRawText(text, syntax);
        firePreviousEvent();
    }

    @Override
    public void onEmptyLines(int count)
    {
        this.previousEvents.onEmptyLines(count);
        firePreviousEvent();
    }

    @Override
    public void onHorizontalLine(Map<String, String> parameters)
    {
        this.previousEvents.onHorizontalLine(parameters);
        firePreviousEvent();
    }

    @Override
    public void onId(String name)
    {
        this.previousEvents.onId(name);
        firePreviousEvent();
    }

    /**
     * {@inheritDoc}
     *
     * @since 2.5RC1
     */
    @Override
    public void onImage(ResourceReference reference, boolean isFreeStandingURI, Map<String, String> parameters)
    {
        this.previousEvents.onImage(reference, isFreeStandingURI, parameters);
        firePreviousEvent();
    }

    @Override
    public void onMacro(String id, Map<String, String> parameters, String content, boolean isInline)
    {
        this.previousEvents.onMacro(id, parameters, content, isInline);
        firePreviousEvent();
    }

    @Override
    public void onNewLine()
    {
        this.previousEvents.onNewLine();
        firePreviousEvent();
    }

    @Override
    public void onSpace()
    {
        this.previousEvents.onSpace();
        firePreviousEvent();
    }

    @Override
    public void onSpecialSymbol(char symbol)
    {
        this.previousEvents.onSpecialSymbol(symbol);
        firePreviousEvent();
    }

    @Override
    public void onVerbatim(String protectedString, boolean isInline, Map<String, String> parameters)
    {
        this.previousEvents.onVerbatim(protectedString, isInline, parameters);
        firePreviousEvent();
    }

    @Override
    public void onWord(String word)
    {
        this.previousEvents.onWord(word);
        firePreviousEvent();
    }

    private void firePreviousEvent()
    {
        if (this.previousEvents.size() > this.lookaheadDepth) {
            Event event = this.previousEvents.remove();
            event.eventType.fireEvent(getListenerChain().getNextListener(getClass()), event.eventParameters);
        }
    }

    private void flush()
    {
        // Ensure that all remaining events are flushed
        while (!this.previousEvents.isEmpty()) {
            Event event = this.previousEvents.remove();
            event.eventType.fireEvent(getListenerChain().getNextListener(getClass()), event.eventParameters);
        }
    }
}
