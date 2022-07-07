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
package org.xwiki.rendering.internal.macro;

import java.util.Set;

import org.jmock.Expectations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xwiki.rendering.internal.transformation.macro.DefaultMacroTransformationConfiguration;
import org.xwiki.rendering.macro.Macro;
import org.xwiki.rendering.macro.MacroCategoryManager;
import org.xwiki.rendering.macro.MacroId;
import org.xwiki.rendering.macro.descriptor.DefaultMacroDescriptor;
import org.xwiki.rendering.syntax.Syntax;
import org.xwiki.rendering.transformation.macro.MacroTransformationConfiguration;
import org.xwiki.test.jmock.AbstractComponentTestCase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@link org.xwiki.rendering.macro.MacroCategoryManager}.
 *
 * @version $Id$
 * @since 2.0M3
 */
class DefaultMacroCategoryManagerTest extends AbstractComponentTestCase
{
    private MacroCategoryManager macroCategoryManager;

    @Override
    @BeforeEach
    public void setUp() throws Exception
    {
        super.setUp();
        this.macroCategoryManager = getComponentManager().getInstance(MacroCategoryManager.class);
    }

    @Test
    void testGetMacroCategories() throws Exception
    {
        // TODO: This test needs to be improved. Right now it's based on the Test Macro located in the transformation
        // package and for 4 of them a "Test" category has been set...
        DefaultMacroTransformationConfiguration configuration =
            getComponentManager().getInstance(MacroTransformationConfiguration.class);
        configuration.addCategory(new MacroId("testcontentmacro"), "Content");
        configuration.addCategory(new MacroId("testsimplemacro"), "Simple");

        Set<String> macroCategories = this.macroCategoryManager.getMacroCategories();

        // Check for a default category.
        assertTrue(macroCategories.contains("Test"));

        // Check for null category.
        assertTrue(macroCategories.contains(null));

        // Check for overwritten categories.
        assertTrue(macroCategories.contains("Content"));
        assertTrue(macroCategories.contains("Simple"));
    }

    @Test
    void testGetMacroNamesForCategory() throws Exception
    {
        // Create two mock macros and register them macros against the CM as macros registered for all syntaxes.
        final Macro testMacro1 = registerMockComponent(Macro.class, "mytestmacro1", "mock1");
        final Macro testMacro2 = registerMockComponent(Macro.class, "mytestmacro2", "mock2");
        getMockery().checking(new Expectations()
        {{
            allowing(testMacro1).getDescriptor();
            will(returnValue(new DefaultMacroDescriptor(new MacroId("macro1"), "Test macro - 1")));
            allowing(testMacro2).getDescriptor();
            will(returnValue(new DefaultMacroDescriptor(new MacroId("macro2"), "Test macro - 2")));
        }});

        // Override default macro categories.
        DefaultMacroTransformationConfiguration configuration =
            (DefaultMacroTransformationConfiguration) getComponentManager().getInstance(
                MacroTransformationConfiguration.class);
        configuration.addCategory(new MacroId("mytestmacro1"), "Cat1");
        configuration.addCategory(new MacroId("mytestmacro2"), "Cat2");

        // Check whether our macros are registered under correct categories.
        Set<MacroId> macroIds = this.macroCategoryManager.getMacroIds("Cat1");
        assertTrue(macroIds.contains(new MacroId("mytestmacro1")));
        assertFalse(macroIds.contains(new MacroId("mytestmacro2")));

        // These macros should be registered for all syntaxes.
        macroIds = this.macroCategoryManager.getMacroIds("Cat1", Syntax.JSPWIKI_1_0);
        assertTrue(macroIds.contains(new MacroId("mytestmacro1")));

        // Finally, unregister test macros.
        getComponentManager().unregisterComponent(Macro.class, "mytestmacro1");
        getComponentManager().unregisterComponent(Macro.class, "mytestmacro2");
    }

    @Test
    void testGetMacroIdsWithSyntaxSpecificMacros() throws Exception
    {
        // Create a mock macro and register it against CM as a xwiki/2.0 specific macro.
        final Macro mockMacro = registerMockComponent(Macro.class, "mytestmacro/xwiki/2.0");
        getMockery().checking(new Expectations()
        {{
            allowing(mockMacro).getDescriptor();
            will(returnValue(new DefaultMacroDescriptor(new MacroId("test"), "Test macro")));
        }});

        // Override the macro category for this macro.
        DefaultMacroTransformationConfiguration configuration =
            (DefaultMacroTransformationConfiguration) getComponentManager().getInstance(
                MacroTransformationConfiguration.class);
        configuration.addCategory(new MacroId("mytestmacro", Syntax.XWIKI_2_0), "Test");

        // Make sure our macro is put into the correct category & registered under correct syntax.
        Set<MacroId> macroIds = this.macroCategoryManager.getMacroIds("Test");
        assertTrue(macroIds.contains(new MacroId("mytestmacro", Syntax.XWIKI_2_0)));
        macroIds = this.macroCategoryManager.getMacroIds("Test", Syntax.XWIKI_2_0);
        assertTrue(macroIds.contains(new MacroId("mytestmacro", Syntax.XWIKI_2_0)));
        macroIds = this.macroCategoryManager.getMacroIds("Test", Syntax.JSPWIKI_1_0);
        assertFalse(macroIds.contains(new MacroId("mytestmacro")));

        // Finally, unregister the test macro.
        getComponentManager().unregisterComponent(Macro.class, "mytestmacro/xwiki/2.0");
    }

    @Test
    void getMacroCategory() throws Exception
    {
        // Create a mock macro and register it against CM as a xwiki/2.1 specific macro.
        final Macro mockMacro = registerMockComponent(Macro.class, "mytestmacro/xwiki/2.1");
        getMockery().checking(new Expectations()
        {{
            allowing(mockMacro).getDescriptor();
            DefaultMacroDescriptor result = new DefaultMacroDescriptor(new MacroId("test"), "Test macro");
            result.setDefaultCategory("DefaultCategory");
            will(returnValue(result));
        }});

        String mytestmacro = this.macroCategoryManager.getMacroCategory(new MacroId("mytestmacro", Syntax.XWIKI_2_1));
        assertEquals("DefaultCategory", mytestmacro);
    }

    @Test
    void getMacroCategoryWithOverride() throws Exception
    {
        // Create a mock macro and register it against CM as a xwiki/2.1 specific macro.
        final Macro mockMacro = registerMockComponent(Macro.class, "mytestmacro/xwiki/2.1");
        getMockery().checking(new Expectations()
        {{
            allowing(mockMacro).getDescriptor();
            DefaultMacroDescriptor result = new DefaultMacroDescriptor(new MacroId("test"), "Test macro");
            result.setDefaultCategory("DefaultCategory");
            will(returnValue(result));
        }});

        DefaultMacroTransformationConfiguration configuration =
            getComponentManager().getInstance(MacroTransformationConfiguration.class);
        configuration.addCategory(new MacroId("mytestmacro"), "OverrideCategory");

        String mytestmacro = this.macroCategoryManager.getMacroCategory(new MacroId("mytestmacro", Syntax.XWIKI_2_1));
        assertEquals("OverrideCategory", mytestmacro);
    }
}
