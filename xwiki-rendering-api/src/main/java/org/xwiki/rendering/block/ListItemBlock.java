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
package org.xwiki.rendering.block;

import java.util.List;

import org.xwiki.rendering.listener.Listener;

/**
 * Represents a List item element in a page.
 *
 * @version $Id$
 * @since 1.5M2
 */
public class ListItemBlock extends AbstractBlock
{
    /**
     * Constructs a list item Block.
     *
     * @param childrenBlocks the blocks representing the list item content
     */
    public ListItemBlock(List<Block> childrenBlocks)
    {
        super(childrenBlocks);
    }

    @Override
    public void before(Listener listener)
    {
        listener.beginListItem();
    }

    @Override
    public void after(Listener listener)
    {
        listener.endListItem();
    }
}
