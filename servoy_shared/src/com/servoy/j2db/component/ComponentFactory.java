/*
 This file belongs to the Servoy development and deployment environment, Copyright (C) 1997-2010 Servoy BV

 This program is free software; you can redistribute it and/or modify it under
 the terms of the GNU Affero General Public License as published by the Free
 Software Foundation; either version 3 of the License, or (at your option) any
 later version.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License along
 with this program; if not, see http://www.gnu.org/licenses or write to the Free
 Software Foundation,Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301
 */
package com.servoy.j2db.component;


import java.applet.Applet;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.html.CSS;

import com.servoy.j2db.FlattenedSolution;
import com.servoy.j2db.FormController;
import com.servoy.j2db.FormManager;
import com.servoy.j2db.IApplication;
import com.servoy.j2db.IForm;
import com.servoy.j2db.IScriptExecuter;
import com.servoy.j2db.IServiceProvider;
import com.servoy.j2db.IServoyBeanFactory;
import com.servoy.j2db.J2DBGlobals;
import com.servoy.j2db.MediaURLStreamHandler;
import com.servoy.j2db.dataprocessing.CustomValueList;
import com.servoy.j2db.dataprocessing.FoundSetManager;
import com.servoy.j2db.dataprocessing.IColumnConverter;
import com.servoy.j2db.dataprocessing.IDisplayData;
import com.servoy.j2db.dataprocessing.ITypedColumnConverter;
import com.servoy.j2db.dataprocessing.IValueList;
import com.servoy.j2db.dataprocessing.LookupValueList;
import com.servoy.j2db.dataprocessing.TagResolver;
import com.servoy.j2db.dataprocessing.ValueListFactory;
import com.servoy.j2db.dataui.IServoyAwareBean;
import com.servoy.j2db.persistence.AbstractBase;
import com.servoy.j2db.persistence.BaseComponent;
import com.servoy.j2db.persistence.Bean;
import com.servoy.j2db.persistence.Column;
import com.servoy.j2db.persistence.ColumnInfo;
import com.servoy.j2db.persistence.ColumnWrapper;
import com.servoy.j2db.persistence.Field;
import com.servoy.j2db.persistence.Form;
import com.servoy.j2db.persistence.GraphicalComponent;
import com.servoy.j2db.persistence.IColumn;
import com.servoy.j2db.persistence.IColumnTypes;
import com.servoy.j2db.persistence.IDataProvider;
import com.servoy.j2db.persistence.IDataProviderLookup;
import com.servoy.j2db.persistence.IFormElement;
import com.servoy.j2db.persistence.IPersist;
import com.servoy.j2db.persistence.IRepository;
import com.servoy.j2db.persistence.ISupportChilds;
import com.servoy.j2db.persistence.ISupportName;
import com.servoy.j2db.persistence.ISupportScrollbars;
import com.servoy.j2db.persistence.ITable;
import com.servoy.j2db.persistence.Media;
import com.servoy.j2db.persistence.Part;
import com.servoy.j2db.persistence.Portal;
import com.servoy.j2db.persistence.PositionComparator;
import com.servoy.j2db.persistence.RectShape;
import com.servoy.j2db.persistence.Relation;
import com.servoy.j2db.persistence.RepositoryException;
import com.servoy.j2db.persistence.RuntimeProperty;
import com.servoy.j2db.persistence.Style;
import com.servoy.j2db.persistence.Tab;
import com.servoy.j2db.persistence.TabPanel;
import com.servoy.j2db.persistence.Table;
import com.servoy.j2db.persistence.ValueList;
import com.servoy.j2db.plugins.IClientPluginAccess;
import com.servoy.j2db.scripting.IReturnedTypesProvider;
import com.servoy.j2db.scripting.IScriptable;
import com.servoy.j2db.scripting.IScriptableProvider;
import com.servoy.j2db.scripting.ScriptObjectRegistry;
import com.servoy.j2db.ui.IButton;
import com.servoy.j2db.ui.IComponent;
import com.servoy.j2db.ui.IDisplayTagText;
import com.servoy.j2db.ui.IFieldComponent;
import com.servoy.j2db.ui.IFormLookupPanel;
import com.servoy.j2db.ui.ILabel;
import com.servoy.j2db.ui.IPortalComponent;
import com.servoy.j2db.ui.IRect;
import com.servoy.j2db.ui.IScriptBaseMethods;
import com.servoy.j2db.ui.IScrollPane;
import com.servoy.j2db.ui.ISplitPane;
import com.servoy.j2db.ui.IStylePropertyChangesRecorder;
import com.servoy.j2db.ui.ISupportOnRenderCallback;
import com.servoy.j2db.ui.ISupportRowStyling;
import com.servoy.j2db.ui.ISupportSecuritySettings;
import com.servoy.j2db.ui.ITabPanel;
import com.servoy.j2db.ui.RenderEventExecutor;
import com.servoy.j2db.ui.scripting.AbstractHTMLSubmitRuntimeLabel;
import com.servoy.j2db.ui.scripting.AbstractRuntimeButton;
import com.servoy.j2db.ui.scripting.AbstractRuntimeValuelistComponent;
import com.servoy.j2db.ui.scripting.RuntimeAccordionPanel;
import com.servoy.j2db.ui.scripting.RuntimeCheckBoxChoice;
import com.servoy.j2db.ui.scripting.RuntimeCheckbox;
import com.servoy.j2db.ui.scripting.RuntimeDataButton;
import com.servoy.j2db.ui.scripting.RuntimeDataCalendar;
import com.servoy.j2db.ui.scripting.RuntimeDataCombobox;
import com.servoy.j2db.ui.scripting.RuntimeDataField;
import com.servoy.j2db.ui.scripting.RuntimeDataLabel;
import com.servoy.j2db.ui.scripting.RuntimeDataLookupField;
import com.servoy.j2db.ui.scripting.RuntimeDataPassword;
import com.servoy.j2db.ui.scripting.RuntimeHTMLArea;
import com.servoy.j2db.ui.scripting.RuntimeListBox;
import com.servoy.j2db.ui.scripting.RuntimeMediaField;
import com.servoy.j2db.ui.scripting.RuntimePortal;
import com.servoy.j2db.ui.scripting.RuntimeRTFArea;
import com.servoy.j2db.ui.scripting.RuntimeRadioButton;
import com.servoy.j2db.ui.scripting.RuntimeRadioChoice;
import com.servoy.j2db.ui.scripting.RuntimeRectangle;
import com.servoy.j2db.ui.scripting.RuntimeScriptButton;
import com.servoy.j2db.ui.scripting.RuntimeScriptLabel;
import com.servoy.j2db.ui.scripting.RuntimeSplitPane;
import com.servoy.j2db.ui.scripting.RuntimeTabPanel;
import com.servoy.j2db.ui.scripting.RuntimeTextArea;
import com.servoy.j2db.util.ComponentFactoryHelper;
import com.servoy.j2db.util.Debug;
import com.servoy.j2db.util.FixedStyleSheet;
import com.servoy.j2db.util.FormatParser;
import com.servoy.j2db.util.OpenProperties;
import com.servoy.j2db.util.OrientationApplier;
import com.servoy.j2db.util.Pair;
import com.servoy.j2db.util.PersistHelper;
import com.servoy.j2db.util.Settings;
import com.servoy.j2db.util.Utils;
import com.servoy.j2db.util.XMLDecoder;
import com.servoy.j2db.util.gui.MyImageIcon;

/**
 * Create UI objects based on repository objects
 * 
 * @author jblok, jcompagner
 */
@SuppressWarnings("nls")
public class ComponentFactory
{
	public static final String WEB_ID_PREFIX = "sv";
	public static final int RTF_AREA = 7;
	public static final int HTML_AREA = 8;

	/**
	 * Grouping of elements in client.
	 */
	public static final String GROUPID_COMPONENT_PROPERTY = "groupId";


	public static boolean paintSampleData = false;
	private static Map<Object, IconHolder> lstIcons = new WeakHashMap<Object, IconHolder>();

	public static final RuntimeProperty<Boolean> MODIFIED_BY_CLIENT = new RuntimeProperty<Boolean>()
	{
	};
	private static final String PARSED_STYLES = "parsedStyles";
	private static ConcurrentMap<Style, FixedStyleSheet> parsedStyles = new ConcurrentHashMap<Style, FixedStyleSheet>();

	private static Boolean element_name_as_uid_prefix;


	public static String getWebID(Form form, IPersist meta)
	{
		StringBuilder prefix = new StringBuilder();
		prefix.append(WEB_ID_PREFIX); //to stay javascript id ref compatible 

		if (element_name_as_uid_prefix == null)
		{
			Settings s = Settings.getInstance();
			element_name_as_uid_prefix = Boolean.valueOf(Utils.getAsBoolean(s.getProperty("servoy.webclient.templates.element_name_as_uid_prefix")));
		}
		if (element_name_as_uid_prefix == Boolean.TRUE && meta instanceof ISupportName)
		{
			String name = ((ISupportName)meta).getName();
			if (name != null && name.trim().length() != 0)
			{
				prefix.append('_');
				prefix.append(name);
			}
		}

		prefix.append('_');
		String uid = meta.getUUID().toString();
		uid = Utils.stringReplace(uid, "-", "_");

		prefix.append(uid);
		return prefix.toString();
	}

	public static String stripIllegalCSSChars(String id)
	{
		return Utils.stringReplace(Utils.stringReplace(id, "-", "_"), "$", "__");
	}


	/**
	 * Create a component
	 * 
	 * @param meta the definition
	 * @param el the event listener such as action,mouse event listeners, can be null (Example:makes possible for button to call script)
	 */
	public static IComponent createComponent(IApplication application, Form form, IPersist meta, IDataProviderLookup dataProviderLookup, IScriptExecuter el,
		boolean printing)
	{
		IComponent c = createComponentEx(application, form, meta, dataProviderLookup, el, printing);

		// set groupID property
		if (meta instanceof IFormElement && ((IFormElement)meta).getGroupID() != null)
		{
			String groupId = ((IFormElement)meta).getGroupID();
			if (groupId != null)
			{
				setComponentProperty(application, c, GROUPID_COMPONENT_PROPERTY, groupId);
			}
		}

		// Extra call so that focusable is user set...
		if (c instanceof Component)
		{
			Component comp = (Component)c;
			if (comp.isFocusable()) comp.setFocusable(true);
			OrientationApplier.setOrientationToAWTComponent(comp, application.getLocale(), application.getSolution().getTextOrientation());
		}
		int access = application.getFlattenedSolution().getSecurityAccess(meta.getUUID());
		if (access != -1)
		{
			boolean b_visible = ((access & IRepository.VIEWABLE) != 0);
			if (!b_visible)
			{
				if (c instanceof ISupportSecuritySettings)
				{
					((ISupportSecuritySettings)c).setViewable(false);
				}
				else
				{
					c.setComponentVisible(false);
				}
			}
			if (c instanceof ISupportSecuritySettings)
			{
				boolean b_accessible = ((access & IRepository.ACCESSIBLE) != 0);
				if (!b_accessible) ((ISupportSecuritySettings)c).setAccessible(false);
			}
		}

		//special case requested by ayton (have own security interface)
		if (c instanceof ITabPanel && meta instanceof TabPanel)
		{
			try
			{
				int i = 0;
				Iterator<IPersist> it = ((TabPanel)meta).getTabs();
				while (it.hasNext())
				{
					Tab t = (Tab)it.next();
					int access1 = application.getFlattenedSolution().getSecurityAccess(t.getUUID());
					if (access1 != -1)
					{
						boolean b_accessible = ((access1 & IRepository.ACCESSIBLE) != 0);
						boolean b_visible = ((access & IRepository.VIEWABLE) != 0);
						if (!b_accessible || !b_visible) ((ITabPanel)c).setTabEnabledAt(i, false);
					}
					i++;
				}
			}
			catch (Exception e)
			{
				Debug.error(e);
			}
		}

		return c;
	}

	public static void setComponentProperty(IApplication applicatiom, Object component, Object key, Serializable value)
	{
		applicatiom.getItemFactory().setComponentProperty(component, key, value);
	}

	public static Serializable getComponentProperty(IApplication application, Object component, Object key)
	{
		return application.getItemFactory().getComponentProperty(component, key);
	}


	protected static IComponent createComponentEx(IApplication application, Form form, IPersist meta, IDataProviderLookup dataProviderLookup,
		IScriptExecuter el, boolean printing)
	{
		IComponent comp = null;
		switch (meta.getTypeID())
		{
			case IRepository.FIELDS :
				comp = createField(application, form, (Field)meta, dataProviderLookup, el, printing);
				break;

			case IRepository.GRAPHICALCOMPONENTS :
				comp = createGraphicalComponent(application, form, (GraphicalComponent)meta, el, dataProviderLookup);
				break;

			case IRepository.RECTSHAPES :
				comp = createRectangle(application, form, (RectShape)meta);
				break;

			case IRepository.PORTALS :
				comp = createPortal(application, form, (Portal)meta, dataProviderLookup, el, printing);
				break;

			case IRepository.PARTS :
				comp = createPart(application, (Part)meta);
				break;

			case IRepository.TABPANELS :
				TabPanel tabPanelMeta = (TabPanel)meta;
				int orient = tabPanelMeta.getTabOrientation();
				if (orient == TabPanel.SPLIT_HORIZONTAL || orient == TabPanel.SPLIT_VERTICAL) comp = createSplitPane(application, form, tabPanelMeta, el);
				else comp = createTabPanel(application, form, tabPanelMeta, el);
				break;

			case IRepository.BEANS :
				comp = createBean(application, form, (Bean)meta, null);
				break;

			default :
				comp = application.getItemFactory().createLabel((meta instanceof ISupportName ? ((ISupportName)meta).getName() : null),
					"ComponentFactory:unkown type " + meta.getTypeID());
		}

		if (comp instanceof JComponent)
		{
			((JComponent)comp).putClientProperty("Id", ComponentFactory.getWebID(form, meta));
		}
		return comp;
	}

	public static FixedStyleSheet getCSSStyle(IServiceProvider sp, Style s)
	{
		if (s == null) return null;

		Map<Style, FixedStyleSheet> parsedStylesMap;
		if (sp != null && Boolean.TRUE.equals(s.getRuntimeProperty(MODIFIED_BY_CLIENT)))
		{
			// style changed by client, cache parsed style per client.
			parsedStylesMap = (ConcurrentMap<Style, FixedStyleSheet>)sp.getRuntimeProperties().get(PARSED_STYLES);
			if (parsedStylesMap == null)
			{
				parsedStylesMap = new ConcurrentHashMap<Style, FixedStyleSheet>();
				sp.getRuntimeProperties().put(PARSED_STYLES, parsedStylesMap);
			}
		}
		else
		{
			// static style, cache globally.
			parsedStylesMap = parsedStyles;
		}

		FixedStyleSheet ss = parsedStylesMap.get(s);
		if (ss == null)
		{
			ss = new FixedStyleSheet();
			try
			{
				ss.addRule(s.getCSSText());
			}
			catch (Exception e)
			{
				Debug.error(e);//parsing can fail in java 1.5
			}
			parsedStylesMap.put(s, ss);
		}
		return ss;
	}

	public static FixedStyleSheet getCSSStyleForForm(IServiceProvider sp, Form form)
	{
		return getCSSStyle(sp, getStyleForForm(sp, form));
	}

//	/**
//	 * Get the overridden stylesheet name
//	 * @return the overridden name or provided form name if not overrridden
//	 */
//	public static String getOverriddenStyleName(IServiceProvider sp, Form form)
//	{
//		if (sp == null) return null;
//
//		Style style = sp.getFlattenedSolution().getStyleForForm(form);
//		if (style == null) return null;
//
//		if (sp.getFlattenedSolution().isUserStyle(style))
//		{
//			return style.getName();
//		}
//		else
//		{
//			return getOverriddenStyleName(sp, style.getName());
//		}
//	}

	/**
	 * Get the overridden stylesheet name
	 * @return the overridden name or provided name if not overrridden
	 */
	public static String getOverriddenStyleName(IServiceProvider sp, String name)
	{
		if (sp == null || name == null) return null;

		@SuppressWarnings("unchecked")
		Map<String, String> overridenStyles = (Map<String, String>)sp.getRuntimeProperties().get(IServiceProvider.RT_OVERRIDESTYLE_CACHE);

		String overridden = null;
		if (overridenStyles != null && (overridden = overridenStyles.get(name)) != null)
		{
			return overridden;
		}
		return name;
	}

	private static Style getStyleForForm(IServiceProvider sp, Form form)
	{
		Style repos_style = null;
		if (sp != null)
		{
			@SuppressWarnings("unchecked")
			Map<String, String> overridenStyles = (Map<String, String>)sp.getRuntimeProperties().get(IServiceProvider.RT_OVERRIDESTYLE_CACHE);
			repos_style = sp.getFlattenedSolution().getStyleForForm(form, overridenStyles);
		}
		else
		{
			repos_style = FlattenedSolution.loadStyleForForm(form);
		}
		return repos_style;
	}

	public static Pair<FixedStyleSheet, javax.swing.text.Style> getStyleForBasicComponent(IServiceProvider sp, BaseComponent bc, Form form)
	{
		return getStyleForBasicComponentInternal(sp, bc, form, new HashSet<Integer>());
	}

	public static Pair<FixedStyleSheet, javax.swing.text.Style> getStyleForBasicComponentInternal(IServiceProvider sp, BaseComponent bc, Form form,
		Set<Integer> visited)
	{
		if (bc == null || sp == null) return null;

		// Protection agains cycle in form inheritance hierarchy.
		if (visited.contains(new Integer(form.getID()))) return null;
		visited.add(new Integer(form.getID()));

		Style repos_style = getStyleForForm(sp, form);
		Pair<FixedStyleSheet, javax.swing.text.Style> pair = null;

		if (repos_style != null)
		{
			FixedStyleSheet ss = getCSSStyle(sp, repos_style);

			String lookupName = getLookupName(bc);

			String formLookup = "";
			ISupportChilds parent = bc.getParent();
			if (parent instanceof Form)
			{
				String styleClass = ((Form)parent).getStyleClass();
				if (styleClass != null && styleClass.length() != 0)
				{
					formLookup = "form." + styleClass;
				}
				else
				{
					formLookup = "form";
				}
			}
			else if (parent instanceof Portal)
			{
				String styleClass = ((Portal)parent).getStyleClass();
				if (styleClass != null && styleClass.length() != 0)
				{
					formLookup = "portal." + styleClass;
				}
				else
				{
					formLookup = "portal";
				}
				parent = ((Portal)parent).getParent();
				if (parent instanceof Form)
				{
					styleClass = ((Form)parent).getStyleClass();
					if (styleClass != null && styleClass.length() != 0)
					{
						formLookup = "form." + styleClass + ' ' + formLookup;
					}
					else
					{
						formLookup = "form " + formLookup;
					}
				}
			}


			javax.swing.text.Style s = null;
			String styleClass = bc.getStyleClass();
			if (lookupName.equals("check") || lookupName.equals("combobox") || "radio".equals(lookupName))
			{
				if (styleClass != null && styleClass.length() != 0)
				{
					lookupName += '.' + styleClass;
				}
				lookupName = formLookup + ' ' + lookupName;
				s = ss.getRule(lookupName);

				if (s.getAttributeCount() > 0) return new Pair<FixedStyleSheet, javax.swing.text.Style>(ss, s);
				else lookupName = "field";
			}

			if (styleClass != null && styleClass.length() != 0)
			{
				lookupName += '.' + styleClass;
			}
			lookupName = formLookup + ' ' + lookupName;
			s = ss.getRule(lookupName);

			pair = new Pair<FixedStyleSheet, javax.swing.text.Style>(ss, s);
			//see BoxPainter for getBorder/getInsets/getLength examples
		}
		if ((pair == null || pair.getRight() == null || (pair.getRight()).getAttributeCount() == 0))
		{
			List<Form> formHierarchy = sp.getFlattenedSolution().getFormHierarchy(form);
			for (int i = 1; i < formHierarchy.size(); i++)
			{
				pair = getStyleForBasicComponentInternal(sp, bc, formHierarchy.get(i), visited);
				if (pair != null && pair.getRight() != null && (pair.getRight()).getAttributeCount() != 0)
				{
					break;
				}
			}
		}
		return pair;
	}

	public static final String[] LOOKUP_NAMES = { "button", "check", "combobox", ISupportRowStyling.CLASS_EVEN, "field", "form", "label", "listbox", ISupportRowStyling.CLASS_ODD, "portal", "radio", ISupportRowStyling.CLASS_SELECTED, "tabpanel" };

	/**
	 * @param bc
	 * @return
	 */
	public static String getLookupName(BaseComponent bc)
	{
		String lookupName = "root";
		if (bc instanceof Field)
		{
			int fieldType = ((Field)bc).getDisplayType();
			switch (fieldType)
			{
				case Field.CHECKS :
					lookupName = "check";
					break;
				case Field.RADIOS :
					lookupName = "radio";
					break;
				case Field.LIST_BOX :
				case Field.MULTI_SELECTION_LIST_BOX :
					lookupName = "listbox";
					break;
				case Field.COMBOBOX :
					lookupName = "combobox";
					break;
				default :
					lookupName = "field";
			}
		}
		else if (bc instanceof Portal)
		{
			lookupName = "portal";
		}
		else if (bc instanceof TabPanel)
		{
			lookupName = "tabpanel";
		}
		else if (bc instanceof GraphicalComponent &&
			(((GraphicalComponent)bc).getOnActionMethodID() > 0 || ((GraphicalComponent)bc).getOnActionMethodID() == -1))
		{
			if (((GraphicalComponent)bc).getShowClick())
			{
				lookupName = "button";
			}
			else
			{
				lookupName = "label";
			}
		}
		else if (bc instanceof GraphicalComponent && ((GraphicalComponent)bc).getOnActionMethodID() <= 0)
		{
			lookupName = "label";
		}
		return lookupName;
	}

	public static void applyBasicComponentProperties(IApplication application, IComponent c, BaseComponent bc,
		Pair<FixedStyleSheet, javax.swing.text.Style> styleInfo)
	{
		// flag for border set by style config
		boolean isBorderStyle = false;

		c.setOpaque(true); // by default it is not transparent
		//apply any style
		if (styleInfo != null)
		{
			FixedStyleSheet ss = styleInfo.getLeft();
			javax.swing.text.Style s = styleInfo.getRight();
			if (ss != null && s != null)
			{
				if (s.getAttribute(CSS.Attribute.COLOR) != null)
				{
					Color cfg = ss.getForeground(s);
					if (cfg != null) c.setForeground(cfg);
				}
				Object sbackground_color = s.getAttribute(CSS.Attribute.BACKGROUND_COLOR);
				if (sbackground_color != null)
				{
					if ("transparent".equals(sbackground_color.toString()))
					{
						c.setOpaque(false);
					}
					else
					{
						Color cbg = ss.getBackground(s);
						if (cbg != null) c.setBackground(cbg);
					}
				}
				//else c.setOpaque(false); // no background-color means transparent
				if (ss.hasFont(s))
				{
					Font f = ss.getFont(s);
					if (f != null) c.setFont(f);
				}
				if (ss.hasBorder(s))
				{
					Border b = ss.getBorder(s);
					if (b != null)
					{
						c.setBorder(b);
						isBorderStyle = true;
					}
				}
				if (ss.hasMargin(s))
				{
					Insets i = ss.getMargin(s);
					if (i != null && c instanceof IButton) ((IButton)c).setMargin(i);
				}
			}
		}

		//We intentionally leave the location setting to DataRenderers, since thats the context and might substract part heights from location!
		java.awt.Dimension dim = bc.getSize();
		if (dim != null) c.setSize(bc.getSize());

		javax.swing.border.Border border = ComponentFactoryHelper.createBorder(bc.getBorderType());
		if ((c instanceof JCheckBox/* DataCheckBox */|| c instanceof JRadioButton/* DataRadioButton */) && (border != null || isBorderStyle))
		{
			((AbstractButton)c).setBorderPainted(true);
			if (c instanceof JCheckBox)
			{
				((JCheckBox)c).setBorderPaintedFlat(false);
			}
		}
		if (border != null)
		{
			if (border instanceof TitledBorder && Utils.isAppleMacOS())
			{
				// apple bug.. i have to set the font again (as new!!)
				TitledBorder tb = (TitledBorder)border;
				Font f = tb.getTitleFont();
				if (f != null)
				{
					tb.setTitleFont(new Font(f.getName(), f.getStyle(), f.getSize()));
				}
				c.setBorder(border);
			}
			else
			{
				c.setBorder(border);
			}
		}


//		if (c instanceof IDelegate)
//		{
//			c = (JComponent)((IDelegate)c).getDelegate(); 
//		}

		String fontString = bc.getFontType();
		if (fontString != null)
		{
			Font f = PersistHelper.createFont(fontString);
			if (f != null) c.setFont(f);
		}
		java.awt.Color bg = bc.getBackground();
		if (bg != null) c.setBackground(bg);
		java.awt.Color fg = bc.getForeground();
		if (fg != null) c.setForeground(fg);
		String name = bc.getName();
		if (name != null) c.setName(name);
		if (bc.getTransparent()) c.setOpaque(false); // only use component property value if it is "checked" to be transparent
		c.setComponentEnabled(bc.getEnabled());
		c.setComponentVisible(bc.getVisible());

		if (application.getApplicationType() == IApplication.CLIENT)
		{
			//special code for smart client LAFs, like BizLaf 
			String delegateStyleClassNamePropertyKey = application.getSettings().getProperty("servoy.smartclient.componentStyleClassDelegatePropertyKey");
			if (delegateStyleClassNamePropertyKey != null && c instanceof JComponent)
			{
				if (c instanceof IScriptableProvider && ((IScriptableProvider)c).getScriptObject() instanceof IScriptBaseMethods)
				{
					//special case since putClientProperty can delegate properties but cannot be overridden we relay on the scripting equivalent
					((IScriptBaseMethods)((IScriptableProvider)c).getScriptObject()).js_putClientProperty(delegateStyleClassNamePropertyKey, bc.getStyleClass());
				}
				else
				{
					((JComponent)c).putClientProperty(delegateStyleClassNamePropertyKey, bc.getStyleClass());
				}
			}
		}
	}

	/**
	 * Returns the bean Instance.
	 * 
	 * @return Object
	 */
	public static Object getBeanInstanceFromXML(IApplication application, String beanClassName, String beanXML) throws Exception
	{
		Object retValue = null;
		ClassLoader bcl = application.getBeanManager().getClassLoader();
		ClassLoader saveCL = null;
		if (bcl != null)
		{
			if (!application.isRunningRemote())
			{
				//must create an temp instace to get it to work
				retValue = bcl.loadClass(beanClassName).newInstance();

				//TODO:remove if sun bug 4676532 is fixed (jdk 1.5)
				saveCL = Thread.currentThread().getContextClassLoader();
				Thread.currentThread().setContextClassLoader(bcl);
			}
		}
		if (beanXML != null && beanXML.length() != 0)
		{
			try
			{
				XMLDecoder decoder = new XMLDecoder(beanXML);
				retValue = decoder.readObject();
				decoder.close();
			}
			catch (Throwable e)
			{
				Debug.error(e);
			}
		}
		else if (retValue == null)
		{
			if (bcl != null) retValue = bcl.loadClass(beanClassName).newInstance();
			else retValue = ComponentFactory.class.getClassLoader().loadClass(beanClassName).newInstance();
		}

		if (!application.isRunningRemote())
		{
			Thread.currentThread().setContextClassLoader(saveCL);
		}
		return retValue;
	}

	public static void updateBeanWithItsXML(final Bean bean, final Object beanDesignInstance)
	{
		//-----save to XML------------------------
		if (beanDesignInstance != null)
		{
			Runnable run = new Runnable()
			{
				/**
				 * @see java.lang.Runnable#run()
				 */
				public void run()
				{
					try
					{
						ByteArrayOutputStream osw = new ByteArrayOutputStream();

						//TODO:remove if sun bug 4676532 is fixed (jdk 1.5)
						ClassLoader saveCL = Thread.currentThread().getContextClassLoader();
						ClassLoader cl = beanDesignInstance.getClass().getClassLoader();
						try
						{
							if (cl != null)
							{
								Thread.currentThread().setContextClassLoader(cl);
							}

							XMLEncoder encoder = new XMLEncoder(osw);
							encoder.writeObject(beanDesignInstance);
							encoder.close();
							Debug.trace("Bean XML: " + osw.toString("UTF-8")); //$NON-NLS-1$ //$NON-NLS-2$
							bean.setBeanXML(osw.toString("UTF-8")); //$NON-NLS-1$
						}
						finally
						{
							Thread.currentThread().setContextClassLoader(saveCL);
						}
					}
					catch (Throwable e)
					{
						//never ever throw something on the event thread, it does not releases locks!
						Debug.error(e);//what todo unable to save the bean...
					}
				}
			};

			try
			{
				if (SwingUtilities.isEventDispatchThread())
				{
					run.run();
				}
				else
				{
					SwingUtilities.invokeAndWait(run);
				}
			}
			catch (Exception e)
			{
				Debug.error(e);
			}
		}
	}

	private static IComponent createRectangle(IApplication application, Form form, RectShape rec)
	{
		RuntimeRectangle scriptable = new RuntimeRectangle(application.getItemFactory().createChangesRecorder(), application);
		IRect panel = application.getItemFactory().createRect(scriptable, getWebID(form, rec), rec.getShapeType());
		scriptable.setComponent(panel);
//		panel.setOpaque(!rec.getTransparent());
		panel.setLineWidth(rec.getLineSize());
		panel.setRadius(rec.getRoundedRadius());
//		JLabel panel = new JLabel("xxx");
		applyBasicComponentProperties(application, panel, rec, getStyleForBasicComponent(application, rec, form));
		return panel;
	}

	@SuppressWarnings("unchecked")
	public static IValueList getRealValueList(IServiceProvider application, ValueList valuelist, boolean useSoftCacheForCustom, int type, String format,
		String dataprovider)
	{
		if (application == null)
		{
			application = J2DBGlobals.getServiceProvider();
		}
		String displayFormat = format == null ? null : new FormatParser(format).getDisplayFormat();
		IValueList list = null;
		if (valuelist != null &&
			(valuelist.getValueListType() == ValueList.CUSTOM_VALUES || (valuelist.getValueListType() == ValueList.DATABASE_VALUES && valuelist.getDatabaseValuesType() == ValueList.TABLE_VALUES)))//reuse,those are static,OTHERS not!
		{
			WeakHashMap<ValueList, Object> hmValueLists = null;
			if (application != null)
			{
				hmValueLists = (WeakHashMap<ValueList, Object>)application.getRuntimeProperties().get(IServiceProvider.RT_VALUELIST_CACHE);
				if (hmValueLists == null)
				{
					hmValueLists = new WeakHashMap<ValueList, Object>();
					application.getRuntimeProperties().put(IServiceProvider.RT_VALUELIST_CACHE, hmValueLists);
				}

				Object object = hmValueLists.get(valuelist);
				if (object instanceof SoftReference< ? >)
				{
					SoftReference<IValueList> sr = (SoftReference<IValueList>)object;
					list = sr.get();
					// if it was inserted by a soft reference but now it can't be softly referenced, put it back in hard.
					if (list != null && !useSoftCacheForCustom)
					{
						hmValueLists.put(valuelist, list);
					}

				}
				else if (object instanceof IValueList)
				{
					list = (IValueList)object;
				}
			}

			if (list == null)
			{
				list = ValueListFactory.createRealValueList(application, valuelist, type, displayFormat);
				if (valuelist.getFallbackValueListID() > 0 && valuelist.getFallbackValueListID() != valuelist.getID())
				{
					ValueList vl = application.getFlattenedSolution().getValueList(valuelist.getFallbackValueListID());
					vl.setDisplayValueType(valuelist.getDisplayValueType());
					list.setFallbackValueList(getRealValueList(application, vl, useSoftCacheForCustom, type, displayFormat, dataprovider));
				}
				if (!useSoftCacheForCustom && valuelist.getValueListType() == ValueList.CUSTOM_VALUES)
				{
					if (hmValueLists != null) hmValueLists.put(valuelist, list);
					if (dataprovider != null)
					{
						((CustomValueList)list).addDataProvider(dataprovider);
					}
				}
				else
				{
					if (hmValueLists != null) hmValueLists.put(valuelist, new SoftReference<IValueList>(list));

					if (dataprovider != null && valuelist.getValueListType() == ValueList.CUSTOM_VALUES)
					{
						((CustomValueList)list).addDataProvider(dataprovider);
					}
				}
			}
			else if (valuelist.getValueListType() == ValueList.CUSTOM_VALUES)
			{
				if (application instanceof IApplication && ((IApplication)application).isInDeveloper())
				{
					int currentType = ((CustomValueList)list).getType();
					if (currentType == Types.OTHER)
					{
						((CustomValueList)list).setType(type);
						if (dataprovider != null)
						{
							((CustomValueList)list).addDataProvider(dataprovider);
						}
					}
					else if (type != Types.OTHER && type != currentType)
					{
						List<String> lst = ((CustomValueList)list).getDataProviders();

						StringBuffer message = new StringBuffer("The valuelist was already created for type: " +
							Column.getDisplayTypeString(((CustomValueList)list).getType()));
						message.append("\n for the dataproviders: ");
						for (int i = 0; i < lst.size(); i++)
						{
							String previousProviders = lst.get(i);
							message.append(previousProviders);
							message.append(",");
						}
						message.setLength(message.length() - 1);
						message.append("\nSo it can't be used also for type: " + Column.getDisplayTypeString(type) + " for the dataprovider: " + dataprovider);
						message.append("\nPlease check these dataproviders of this valuelist: " + valuelist.getName());
						application.reportError("Valuelist: " + list.getName() + " used with different types", message);
					}
				}
			}
		}
		else
		{
			list = ValueListFactory.createRealValueList(application, valuelist, type, displayFormat);
			if (valuelist != null && valuelist.getFallbackValueListID() > 0 && valuelist.getFallbackValueListID() != valuelist.getID())
			{
				ValueList vl = application.getFlattenedSolution().getValueList(valuelist.getFallbackValueListID());
				vl.setDisplayValueType(valuelist.getDisplayValueType());
				list.setFallbackValueList(getRealValueList(application, vl, useSoftCacheForCustom, type, displayFormat, dataprovider));
			}
		}
		return list;
	}

	private static ValueList getValueList(IApplication application, Field field, IDataProviderLookup dataProviderLookup)
	{
		ValueList vl = null;
		try
		{
			vl = application.getFlattenedSolution().getValueList(field.getValuelistID());
			if (vl != null /* && application.getApplicationType() == IServiceProvider.DEVELOPER */&& vl.getValueListType() == ValueList.DATABASE_VALUES &&
				dataProviderLookup != null)
			{
				IDataProvider dp = dataProviderLookup.getDataProvider(field.getDataProviderID());
				if (dp != null)
				{
					int type = IColumnTypes.TEXT;
					String id = null;
					int total = vl.getShowDataProviders();
					if ((total & 7) == 1)
					{
						id = vl.getDataProviderID1();
					}
					else if ((total & 7) == 2)
					{
						id = vl.getDataProviderID2();
					}
					else if ((total & 7) == 4)
					{
						id = vl.getDataProviderID3();
					}
					else
					{
						//result must be string is concatenated
						type = IColumnTypes.TEXT;
					}

					IDataProvider p = application.getFlattenedSolution().getGlobalDataProvider(id);
					if (p == null && id != null)
					{
						ITable t = null;
						if (vl.getDatabaseValuesType() == ValueList.RELATED_VALUES)
						{
							String relationName = vl.getRelationName();
							Relation[] relations = application.getFlattenedSolution().getRelationSequence(relationName);
							if (relations != null) t = relations[relations.length - 1].getForeignTable();
						}
						else
						{
							t = vl.getTable();
						}

						if (t != null)
						{
							p = application.getFlattenedSolution().getDataProviderForTable((Table)t, id);
						}
					}

					if (p != null)
					{
						type = Column.mapToDefaultType(p.getDataProviderType());

//TODO this check has to be removed here because we are calculation the Display type and 
// not the Real type.. Should this check be done at another place????						
//						int dpft = Column.mapToDefaultType(dp.getDataProviderType(dataProviderLookup));
//						boolean incompat = false;
//						if (dpft == Column.NUMBER && (dpt != Column.NUMBER || dpt != Column.INTEGER)) incompat = true;
//						else if (dpft == Column.INTEGER && dpt != Column.INTEGER) incompat = true;
//						else if (dpft == Column.DATETIME && dpt != Column.DATETIME) incompat = true;
//						else if (dpft == Column.MEDIA) incompat = true;
//						
//						if (incompat)
//						{
//							application.reportError("Incompatible valuelist on field","Field "+field.getName()+" ("+field.getDataProviderID()+") has incompatible valuelist "+vl.getName()+"\nvaluelist is returning wrong type for field");
//						}
					}
					vl.setDisplayValueType(type);//store s owe can use it later on
				}
			}
		}
		catch (Exception ex)
		{
			Debug.error(ex);
		}
		return vl;
	}

	public static Pair<String, Integer> getComponentFormat(String format, String dataProviderID, IDataProviderLookup dataProviderLookup,
		IServiceProvider application)
	{
		int type = IColumnTypes.TEXT;
		IDataProvider dp = null;
		if (dataProviderID != null && dataProviderLookup != null)
		{
			try
			{
				dp = dataProviderLookup.getDataProvider(dataProviderID);
				if (dp != null)
				{
					type = dp.getDataProviderType();
					IColumn c = null;
					if (dp instanceof ColumnWrapper)
					{
						c = ((ColumnWrapper)dp).getColumn();
					}
					else if (dp instanceof Column)
					{
						c = (IColumn)dp;
					}


					if (c instanceof Column)
					{
						ColumnInfo ci = ((Column)c).getColumnInfo();
						if (ci != null)
						{
							if (format == null || format.length() == 0)
							{
								if (ci.getDefaultFormat() != null && ci.getDefaultFormat().length() > 0)
								{
									format = ci.getDefaultFormat();
								}
							}
							if (ci.getConverterName() != null && ci.getConverterName().trim().length() != 0)
							{
								IColumnConverter converter = ((FoundSetManager)application.getFoundSetManager()).getColumnConverterManager().getConverter(
									ci.getConverterName());
								if (converter instanceof ITypedColumnConverter)
								{
									try
									{
										OpenProperties props = new OpenProperties();
										if (ci.getConverterProperties() != null) props.load(new StringReader(ci.getConverterProperties()));
										type = ((ITypedColumnConverter)converter).getToObjectType(props);
										if (type == Integer.MAX_VALUE)
										{
											type = c.getDataProviderType();
										}
										else
										{
											type = Column.mapToDefaultType(type);
										}
									}
									catch (IOException e)
									{
										Debug.error(
											"Exception loading properties for converter " + converter.getName() + ", properties: " +
												ci.getConverterProperties(), e);
									}
								}
							}
						}

					}
					if (format == null || format.length() == 0)
					{
						format = TagResolver.getDefaultFormatForType(application.getSettings(), type);
					}
					if ("converter".equals(format))
					{
						format = null;
						type = IColumnTypes.TEXT;
					}

				}
			}
			catch (RepositoryException e)
			{
				Debug.error(e);
			}
		}
		return new Pair<String, Integer>(format, type);
	}

	private static IComponent createField(IApplication application, Form form, Field field, IDataProviderLookup dataProviderLookup, IScriptExecuter el,
		boolean printing)
	{
		ValueList valuelist = null;
		if (field.getValuelistID() > 0) valuelist = getValueList(application, field, dataProviderLookup);
		Pair<String, Integer> fieldFormat = getComponentFormat(field.getFormat(), field.getDataProviderID(), dataProviderLookup, application);
		String format = fieldFormat.getLeft();
		int type = fieldFormat.getRight();

		IDataProvider dp = null;
		if (field.getDataProviderID() != null && dataProviderLookup != null)
		{
			try
			{
				dp = dataProviderLookup.getDataProvider(field.getDataProviderID());
			}
			catch (RepositoryException e)
			{
				Debug.error(e);
			}
		}

		//apply any style
		Insets style_margin = null;
		int style_halign = -1;
		boolean hasBorder = false;
		Pair<FixedStyleSheet, javax.swing.text.Style> styleInfo = getStyleForBasicComponent(application, field, form);
		if (styleInfo != null)
		{
			FixedStyleSheet ss = styleInfo.getLeft();
			javax.swing.text.Style s = styleInfo.getRight();
			if (ss != null && s != null)
			{
				style_margin = ss.getMargin(s);
				style_halign = ss.getHAlign(s);
				hasBorder = ss.hasBorder(s);
			}
		}

		IStylePropertyChangesRecorder jsChangeRecorder = application.getItemFactory().createChangesRecorder();

		IFieldComponent fl = null;
		switch (field.getDisplayType())
		{
			case Field.PASSWORD :
			{
				RuntimeDataPassword scriptable = new RuntimeDataPassword(jsChangeRecorder, application);
				fl = application.getItemFactory().createDataPassword(scriptable, getWebID(form, field));
				scriptable.setComponent(fl);
			}
				break;
			case Field.RTF_AREA :
			{
				RuntimeRTFArea scriptable = new RuntimeRTFArea(jsChangeRecorder, application);
				fl = application.getItemFactory().createDataTextEditor(scriptable, getWebID(form, field), RTF_AREA, field.getEditable());
				scriptable.setComponent(fl);
				if (fl instanceof IScrollPane)
				{
					applyScrollBarsProperty((IScrollPane)fl, field);
				}
			}
				break;
			case Field.HTML_AREA :
			{
				RuntimeHTMLArea scriptable = new RuntimeHTMLArea(jsChangeRecorder, application);
				fl = application.getItemFactory().createDataTextEditor(scriptable, getWebID(form, field), HTML_AREA, field.getEditable());
				scriptable.setComponent(fl);
				if (fl instanceof IScrollPane)
				{
					applyScrollBarsProperty((IScrollPane)fl, field);
				}
			}
				break;
			case Field.TEXT_AREA :
			{
				RuntimeTextArea scriptable = new RuntimeTextArea(jsChangeRecorder, application);
				fl = application.getItemFactory().createDataTextArea(scriptable, getWebID(form, field));
				scriptable.setComponent(fl);
				if (fl instanceof IScrollPane)
				{
					applyScrollBarsProperty((IScrollPane)fl, field);
				}
			}
				break;
			case Field.CHECKS :
			{
				AbstractRuntimeValuelistComponent<IFieldComponent> scriptable;
				if (valuelist != null)
				{
					IValueList list = getRealValueList(application, valuelist, true, type, format, field.getDataProviderID());
					if (isSingleValue(valuelist, list))
					{
						scriptable = new RuntimeCheckbox(jsChangeRecorder, application);
						fl = application.getItemFactory().createSelectBox(scriptable, getWebID(form, field),
							application.getI18NMessageIfPrefixed(field.getText()), list, false);
					}
					else
					{
						scriptable = new RuntimeCheckBoxChoice(jsChangeRecorder, application);
						fl = application.getItemFactory().createDataChoice((RuntimeCheckBoxChoice)scriptable, getWebID(form, field), list, false);
						if (fl instanceof IScrollPane)
						{
							applyScrollBarsProperty((IScrollPane)fl, field);
						}
					}
				}
				else
				{
					scriptable = new RuntimeCheckbox(jsChangeRecorder, application);
					fl = application.getItemFactory().createSelectBox(scriptable, getWebID(form, field), application.getI18NMessageIfPrefixed(field.getText()),
						null, false);
				}
				scriptable.setComponent(fl);
			}
				break;
			case Field.RADIOS :
			{
				AbstractRuntimeValuelistComponent<IFieldComponent> scriptable;
				IValueList list = getRealValueList(application, valuelist, true, type, format, field.getDataProviderID());
				if (isSingleValue(valuelist, list))
				{
					scriptable = new RuntimeRadioButton(jsChangeRecorder, application);
					fl = application.getItemFactory().createSelectBox(scriptable, getWebID(form, field), application.getI18NMessageIfPrefixed(field.getText()),
						list, true);
				}
				else
				{
					scriptable = new RuntimeRadioChoice(jsChangeRecorder, application);
					fl = application.getItemFactory().createDataChoice((RuntimeRadioChoice)scriptable, getWebID(form, field), list, true);
					if (fl instanceof IScrollPane)
					{
						applyScrollBarsProperty((IScrollPane)fl, field);
					}
				}
				scriptable.setComponent(fl);
			}
				break;
			case Field.COMBOBOX :
			{
				RuntimeDataCombobox scriptable = new RuntimeDataCombobox(jsChangeRecorder, application);
				IValueList list = getRealValueList(application, valuelist, true, type, format, field.getDataProviderID());
				fl = application.getItemFactory().createDataComboBox(scriptable, getWebID(form, field), list);
				scriptable.setComponent(fl);
			}
				break;

			case Field.CALENDAR :
			{
				RuntimeDataCalendar scriptable = new RuntimeDataCalendar(jsChangeRecorder, application);
				fl = application.getItemFactory().createDataCalendar(scriptable, getWebID(form, field));
				scriptable.setComponent(fl);
			}
				break;

			case Field.IMAGE_MEDIA :
			{
				RuntimeMediaField scriptable = new RuntimeMediaField(jsChangeRecorder, application);
				fl = application.getItemFactory().createDataImgMediaField(scriptable, getWebID(form, field));
				if (fl instanceof IScrollPane)
				{
					applyScrollBarsProperty((IScrollPane)fl, field);
				}
				scriptable.setComponent(fl);
			}
				break;
			case Field.TYPE_AHEAD :
				if (field.getValuelistID() > 0)
				{
					fl = createTypeAheadWithValueList(application, form, field, dataProviderLookup, type, format, jsChangeRecorder);
					break;
				}
				else if (dp != null && dp.getColumnWrapper() != null && dp.getColumnWrapper().getRelations() == null)//only allow plain columns
				{
					RuntimeDataLookupField scriptable = new RuntimeDataLookupField(jsChangeRecorder, application);
					fl = application.getItemFactory().createDataLookupField(scriptable, getWebID(form, field), form.getServerName(), form.getTableName(),
						dp == null ? field.getDataProviderID() : dp.getDataProviderID());
					scriptable.setComponent(fl);
					break;
				}
				//$FALL-THROUGH$
			case Field.LIST_BOX :
			case Field.MULTI_SELECTION_LIST_BOX :
			{
				boolean multiSelect = (field.getDisplayType() == Field.MULTI_SELECTION_LIST_BOX);
				RuntimeListBox scriptable = new RuntimeListBox(jsChangeRecorder, application, multiSelect);
				IValueList list = getRealValueList(application, valuelist, true, type, format, field.getDataProviderID());
				fl = application.getItemFactory().createListBox(scriptable, getWebID(form, field), list, multiSelect);
				scriptable.setComponent(fl);
			}
				break;
			//$FALL-THROUGH$ else treat as the default case: TEXT_FIELD
			default ://Field.TEXT_FIELD 
				if (field.getValuelistID() > 0)
				{
					fl = createTypeAheadWithValueList(application, form, field, dataProviderLookup, type, format, jsChangeRecorder);
				}
				else
				{
					RuntimeDataField scriptable = new RuntimeDataField(jsChangeRecorder, application);
					fl = application.getItemFactory().createDataField(scriptable, getWebID(form, field));
					scriptable.setComponent(fl);
				}
				break;
		}

		if (fl instanceof ISupportAsyncLoading)
		{
			((ISupportAsyncLoading)fl).setAsyncLoadingEnabled(!printing);
		}
		fl.setSelectOnEnter(field.getSelectOnEnter());
		fl.setEditable(field.getEditable());
		try
		{
			int halign = field.getHorizontalAlignment();
			if (halign != -1)
			{
				fl.setHorizontalAlignment(halign);
			}
			else if (style_halign != -1)
			{
				fl.setHorizontalAlignment(style_halign);
			}
		}
		catch (RuntimeException e)
		{
			//just ignore...Debug.error(e);
		}
		fl.setToolTipText(application.getI18NMessageIfPrefixed(field.getToolTipText()));
		fl.setTitleText(application.getI18NMessageIfPrefixed(field.getText()));
		fl.setDataProviderID(dp == null ? field.getDataProviderID() : dp.getDataProviderID());
		if (field.getDataProviderID() != null && dataProviderLookup != null)
		{
			fl.setFormat(type, application.getI18NMessageIfPrefixed(format));

			if (dp != null)
			{
				//if (valuelist != null && valuelist.getValueListType() != ValueList.CUSTOM_VALUES) type = valuelist.getDisplayValueType();
				int l = dp.getLength();
				int defaultType = Column.mapToDefaultType(type);
				if (l > 0 && (defaultType == IColumnTypes.TEXT || defaultType == IColumnTypes.MEDIA))
				{
					fl.setMaxLength(l);
				}
			}
		}
//		fl.setOpaque(!field.getTransparent());
		if (field.getDisplaysTags())
		{
			fl.setNeedEntireState(true);
			if (field.getDataProviderID() == null && field.getText() != null && fl instanceof IDisplayTagText)
			{
				((IDisplayTagText)fl).setTagText(field.getText());
			}
		}
		if (el != null) // el is an ActionListener
		{
			fl.addScriptExecuter(el);
			Object[] cmds = combineMethodsToCommands(form, form.getOnElementFocusGainedMethodID(), "onElementFocusGainedMethodID", field,
				field.getOnFocusGainedMethodID(), "onFocusGainedMethodID");
			if (cmds != null) fl.setEnterCmds((String[])cmds[0], (Object[][])cmds[1]);
			cmds = combineMethodsToCommands(form, form.getOnElementFocusLostMethodID(), "onElementFocusLostMethodID", field, field.getOnFocusLostMethodID(),
				"onFocusLostMethodID");
			if (cmds != null) fl.setLeaveCmds((String[])cmds[0], (Object[][])cmds[1]);
			if (field.getOnActionMethodID() > 0) fl.setActionCmd(Integer.toString(field.getOnActionMethodID()),
				Utils.parseJSExpressions(field.getInstanceMethodArguments("onActionMethodID")));
			if (field.getOnDataChangeMethodID() > 0) fl.setChangeCmd(Integer.toString(field.getOnDataChangeMethodID()),
				Utils.parseJSExpressions(field.getInstanceMethodArguments("onDataChangeMethodID")));
			if (field.getOnRightClickMethodID() > 0) fl.setRightClickCommand(Integer.toString(field.getOnRightClickMethodID()),
				Utils.parseJSExpressions(field.getInstanceMethodArguments("onRightClickMethodID")));
		}

		int onRenderMethodID = field.getOnRenderMethodID();
		if (onRenderMethodID <= 0) onRenderMethodID = form.getOnRenderMethodID();
		if (onRenderMethodID > 0)
		{
			IScriptable scriptable = fl.getScriptObject();

			if (scriptable instanceof ISupportOnRenderCallback)
			{
				RenderEventExecutor renderEventExecutor = ((ISupportOnRenderCallback)scriptable).getRenderEventExecutor();
				renderEventExecutor.setRenderCallback(Integer.toString(onRenderMethodID));

				IForm rendererForm = application.getFormManager().getForm(form.getName());
				IScriptExecuter rendererScriptExecuter = rendererForm instanceof FormController ? ((FormController)rendererForm).getScriptExecuter() : null;
				renderEventExecutor.setRenderScriptExecuter(rendererScriptExecuter);
			}
		}


		applyBasicComponentProperties(application, fl, field, styleInfo);

		if (fl instanceof INullableAware)
		{
			INullableAware nullAware = (INullableAware)fl;
			boolean allowNull = true;
			// for example if it is a check box linked to a non-null integer table column, it must force null to
			// become 0 (because it is unchecked) so that the user does not need to check/uncheck it for save
			try
			{
				if (dataProviderLookup != null && dataProviderLookup.getTable() != null && field.getDataProviderID() != null)
				{
					String dataproviderId = dp == null ? field.getDataProviderID() : dp.getDataProviderID();
					if (dataProviderLookup.getTable().getColumn(dataproviderId) != null)
					{
						allowNull = dataProviderLookup.getTable().getColumn(dataproviderId).getAllowNull();
					}
				}
			}
			catch (RepositoryException e)
			{
				// maybe this field is not linked to a table column... so leave it true
			}
			nullAware.setAllowNull(allowNull);
		}

		Insets m = field.getMargin();
		if (m == null) m = style_margin;

		if (m != null)
		{
			fl.setMargin(m);
			if (fl instanceof IMarginAwareBorder)
			{
				if (field.getBorderType() != null || hasBorder)
				{
					Border b = fl.getBorder();
					if (b != null)
					{
						fl.setBorder(BorderFactory.createCompoundBorder(b, BorderFactory.createEmptyBorder(m.top, m.left, m.bottom, m.right)));
					}
				}
			}
		}

		return fl;
	}

	private static Object[] combineMethodsToCommands(AbstractBase persist1, int method1, String methodKey1, AbstractBase persist2, int method2,
		String methodKey2)
	{
		if (method1 <= 0 && method2 <= 0)
		{
			return null;
		}
		if (method1 > 0 && method2 > 0)
		{
			return new Object[] { new String[] { String.valueOf(method1), String.valueOf(method2) }, new Object[][] { Utils.parseJSExpressions(persist1.getInstanceMethodArguments(methodKey1)), Utils.parseJSExpressions(persist2.getInstanceMethodArguments(methodKey2)) } };
		}
		return new Object[] { new String[] { String.valueOf(method1 <= 0 ? method2 : method1) }, new Object[][] { Utils.parseJSExpressions((method1 <= 0
			? persist2 : persist1).getInstanceMethodArguments(method1 <= 0 ? methodKey2 : methodKey1)) } };
	}

	/**
	 * @param application
	 * @param field
	 * @param dataProviderLookup
	 * @param fl
	 * @return
	 */
	private static IFieldComponent createTypeAheadWithValueList(IApplication application, Form form, Field field, IDataProviderLookup dataProviderLookup,
		int type, String format, IStylePropertyChangesRecorder jsChangeRecorder)
	{
		RuntimeDataField scriptable;
		IFieldComponent fl;
		ValueList valuelist = getValueList(application, field, dataProviderLookup);
		if (valuelist == null)
		{
			scriptable = new RuntimeDataField(jsChangeRecorder, application);
			fl = application.getItemFactory().createDataField(scriptable, getWebID(form, field));
		}
		else
		{
			scriptable = new RuntimeDataLookupField(jsChangeRecorder, application);
			if (valuelist.getValueListType() == ValueList.DATABASE_VALUES)
			{
				LookupValueList lookupValueList = null;
				try
				{
					lookupValueList = new LookupValueList(valuelist, application);
					fl = application.getItemFactory().createDataLookupField((RuntimeDataLookupField)scriptable, getWebID(form, field), lookupValueList);
				}
				catch (Exception e1)
				{
					Debug.error(e1);
					return null;
				}
			}
			else if (valuelist.getValueListType() == ValueList.CUSTOM_VALUES || valuelist.getValueListType() == ValueList.GLOBAL_METHOD_VALUES)
			{
				fl = application.getItemFactory().createDataLookupField((RuntimeDataLookupField)scriptable, getWebID(form, field),
					(CustomValueList)getRealValueList(application, valuelist, true, type, format, field.getDataProviderID()));
			}
			else
			{
				return null;
			}
		}
		scriptable.setComponent(fl);
		return fl;
	}

	private static IComponent createGraphicalComponent(IApplication application, Form form, GraphicalComponent label, IScriptExecuter el,
		IDataProviderLookup dataProviderLookup)
	{
		int style_halign = -1;
		int style_valign = -1;
		int textTransform = 0;
		int mediaid = 0;
		Pair<FixedStyleSheet, javax.swing.text.Style> styleInfo = getStyleForBasicComponent(application, label, form);
		if (styleInfo != null)
		{
			FixedStyleSheet ss = styleInfo.getLeft();
			javax.swing.text.Style s = styleInfo.getRight();
			if (ss != null && s != null)
			{
				style_valign = ss.getVAlign(s);
				style_halign = ss.getHAlign(s);

				boolean parseMedia = true;
				// only parse and set the media id for the webclient when both repeat and position properties are not specified
				// anything else then then the css through the templategenerator is used. (See TemplateGenerator.createGraphicalComponentHTML)
				if (application.getApplicationType() == IApplication.WEB_CLIENT)
				{
					parseMedia = s.getAttribute(CSS.Attribute.BACKGROUND_REPEAT) == null && s.getAttribute(CSS.Attribute.BACKGROUND_POSITION) == null;
				}
				if (parseMedia)
				{
					Object mediaUrl = s.getAttribute(CSS.Attribute.BACKGROUND_IMAGE);
					if (mediaUrl != null && mediaUrl.toString() != null)
					{
						String mediaUrlString = mediaUrl.toString();
						int start = mediaUrlString.indexOf(MediaURLStreamHandler.MEDIA_URL_DEF);
						if (start != -1)
						{
							String name = mediaUrlString.substring(start + MediaURLStreamHandler.MEDIA_URL_DEF.length());
							if (name.endsWith("')") || name.endsWith("\")")) name = name.substring(0, name.length() - 2);
							if (name.endsWith(")")) name = name.substring(0, name.length() - 1);
							Media media = application.getFlattenedSolution().getMedia(name);
							if (media != null)
							{
								mediaid = media.getID();
							}
						}
					}
				}


				String transform = (String)s.getAttribute(CSS.getAttribute("text-transform"));
				if (transform != null)
				{
					if ("uppercase".equals(transform))
					{
						textTransform = ILabel.UPPERCASE;
					}
					else if ("lowercase".equals(transform))
					{
						textTransform = ILabel.LOWERCASE;
					}
					else if ("capitalize".equals(transform))
					{
						textTransform = ILabel.CAPITALIZE;
					}
				}
			}

		}

		ILabel l;
		IStylePropertyChangesRecorder jsChangeRecorder = application.getItemFactory().createChangesRecorder();
		if (label.getOnActionMethodID() != 0 && label.getShowClick())
		{
			AbstractRuntimeButton<IButton> scriptable;
			IButton button;
			if (label.getDataProviderID() == null && !label.getDisplaysTags())
			{
				scriptable = new RuntimeScriptButton(jsChangeRecorder, application);
				button = application.getItemFactory().createScriptButton((RuntimeScriptButton)scriptable, getWebID(form, label));
			}
			else
			{
				scriptable = new RuntimeDataButton(jsChangeRecorder, application);
				button = application.getItemFactory().createDataButton((RuntimeDataButton)scriptable, getWebID(form, label));
				IDataProvider dp = null;
				try
				{
					dp = dataProviderLookup == null ? null : dataProviderLookup.getDataProvider(label.getDataProviderID());
				}
				catch (RepositoryException e)
				{
					Debug.error(e);
				}
				((IDisplayData)button).setDataProviderID(dp == null ? label.getDataProviderID() : dp.getDataProviderID());
				((IDisplayTagText)button).setTagText(application.getI18NMessageIfPrefixed(label.getText()));
				((IDisplayData)button).setNeedEntireState(label.getDisplaysTags());
			}
			scriptable.setComponent(button);
			button.setMediaOption(label.getMediaOptions());
			if (label.getRolloverImageMediaID() > 0)
			{
				try
				{
					button.setRolloverIcon(label.getRolloverImageMediaID());
					button.setRolloverEnabled(true);
				}
				catch (Exception ex)
				{
					Debug.error(ex);
				}
			}
			l = button;
		}
		else
		{
			AbstractHTMLSubmitRuntimeLabel<ILabel> scriptable;
			if (label.getDataProviderID() == null && !label.getDisplaysTags())
			{
				scriptable = new RuntimeScriptLabel(jsChangeRecorder, application);
				l = application.getItemFactory().createScriptLabel((RuntimeScriptLabel)scriptable, getWebID(form, label), (label.getOnActionMethodID() > 0));
			}
			else
			{
				scriptable = new RuntimeDataLabel(jsChangeRecorder, application);
				l = application.getItemFactory().createDataLabel((RuntimeDataLabel)scriptable, getWebID(form, label), (label.getOnActionMethodID() > 0));
				IDataProvider dp = null;
				try
				{
					dp = dataProviderLookup == null ? null : dataProviderLookup.getDataProvider(label.getDataProviderID());
				}
				catch (RepositoryException e)
				{
					Debug.error(e);
				}
				((IDisplayData)l).setDataProviderID(dp == null ? label.getDataProviderID() : dp.getDataProviderID());
				((IDisplayTagText)l).setTagText(application.getI18NMessageIfPrefixed(label.getText()));
				((IDisplayData)l).setNeedEntireState(label.getDisplaysTags());
			}
			scriptable.setComponent(l);
			l.setMediaOption(label.getMediaOptions());
			if (label.getRolloverImageMediaID() > 0)
			{
				try
				{
					l.setRolloverIcon(label.getRolloverImageMediaID());
				}
				catch (Exception ex)
				{
					Debug.error(ex);
				}
			}
		}
		String mnemonic = application.getI18NMessageIfPrefixed(label.getMnemonic());
		if (mnemonic != null && mnemonic.length() > 0)
		{
			l.setDisplayedMnemonic(mnemonic.charAt(0));
		}
		l.setTextTransform(textTransform);
		if (el != null && (label.getOnActionMethodID() > 0 || label.getOnDoubleClickMethodID() > 0 || label.getOnRightClickMethodID() > 0))
		{
			l.addScriptExecuter(el);
			if (label.getOnActionMethodID() > 0) l.setActionCommand(Integer.toString(label.getOnActionMethodID()),
				Utils.parseJSExpressions(label.getInstanceMethodArguments("onActionMethodID")));
			if (label.getOnDoubleClickMethodID() > 0) l.setDoubleClickCommand(Integer.toString(label.getOnDoubleClickMethodID()),
				Utils.parseJSExpressions(label.getInstanceMethodArguments("onDoubleClickMethodID")));
			if (label.getOnRightClickMethodID() > 0) l.setRightClickCommand(Integer.toString(label.getOnRightClickMethodID()),
				Utils.parseJSExpressions(label.getInstanceMethodArguments("onRightClickMethodID")));
		}

		if (label.getLabelFor() == null || (form.getView() != FormController.TABLE_VIEW && form.getView() != FormController.LOCKED_TABLE_VIEW))
		{
			int onRenderMethodID = label.getOnRenderMethodID();
			if (onRenderMethodID <= 0) onRenderMethodID = form.getOnRenderMethodID();
			if (onRenderMethodID > 0)
			{
				IScriptable scriptable = l.getScriptObject();

				if (scriptable instanceof ISupportOnRenderCallback)
				{
					RenderEventExecutor renderEventExecutor = ((ISupportOnRenderCallback)scriptable).getRenderEventExecutor();
					renderEventExecutor.setRenderCallback(Integer.toString(onRenderMethodID));

					IForm rendererForm = application.getFormManager().getForm(form.getName());
					IScriptExecuter rendererScriptExecuter = rendererForm instanceof FormController ? ((FormController)rendererForm).getScriptExecuter() : null;
					renderEventExecutor.setRenderScriptExecuter(rendererScriptExecuter);
				}
			}
		}

		l.setRotation(label.getRotation());
		l.setFocusPainted(label.getShowFocus());
		l.setCursor(Cursor.getPredefinedCursor(label.getRolloverCursor()));
		try
		{
			int halign = label.getHorizontalAlignment();
			if (halign != -1)
			{
				l.setHorizontalAlignment(halign);
			}
			else if (style_halign != -1)
			{
				l.setHorizontalAlignment(style_halign);
			}
		}
		catch (RuntimeException e)
		{
			//just ignore...Debug.error(e);
		}

		int valign = label.getVerticalAlignment();
		if (valign != -1)
		{
			l.setVerticalAlignment(valign);
		}
		else if (style_valign != -1)
		{
			l.setVerticalAlignment(style_valign);
		}

		try
		{
			if (!label.getDisplaysTags())
			{
				l.setText(application.getI18NMessageIfPrefixed(label.getText()));
			}
		}
		catch (RuntimeException e1)
		{
			// ignore
		}
		l.setToolTipText(application.getI18NMessageIfPrefixed(label.getToolTipText()));
		if (label.getImageMediaID() > 0)
		{
			try
			{
				l.setMediaIcon(label.getImageMediaID());
			}
			catch (Exception e)
			{
				Debug.error(e);
			}
		}
		else if (mediaid > 0)
		{
			try
			{
				l.setMediaIcon(mediaid);
			}
			catch (Exception e)
			{
				Debug.error(e);
			}
		}

		if (label.getDataProviderID() != null)
		{
			Pair<String, Integer> fieldFormat = getComponentFormat(label.getFormat(), label.getDataProviderID(), dataProviderLookup, application);
			String format = fieldFormat.getLeft();
			int type = fieldFormat.getRight();
			l.setFormat(type, format);
		}
		applyBasicComponentProperties(application, l, label, styleInfo);

		Border border = null;
		Insets insets = null;

		if (label.getBorderType() != null)
		{
			border = ComponentFactoryHelper.createBorder(label.getBorderType());
		}
		if (label.getMargin() != null)
		{
			insets = label.getMargin();
		}

		if (styleInfo != null && (border == null || insets == null))
		{
			FixedStyleSheet ss = styleInfo.getLeft();
			javax.swing.text.Style s = styleInfo.getRight();
			if (ss != null && s != null)
			{
				if (border == null && ss.hasBorder(s))
				{
					border = ss.getBorder(s);
				}
				if (insets == null && ss.hasMargin(s))
				{
					insets = ss.getMargin(s);
				}
			}
		}


		if (border != null && insets != null)
		{
			l.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(insets.top, insets.left, insets.bottom, insets.right)));
		}
		else if (border == null && insets != null && l instanceof IButton)
		{
			((IButton)l).setMargin(insets);
		}
		// If there is no border, but there are margins, and we don't have a child of JButton (which
		// supports setMargin, then fake the margins through empty border. (issue 166391)
		else if (border == null && insets != null)
		{
			l.setBorder(BorderFactory.createEmptyBorder(insets.top, insets.left, insets.bottom, insets.right));
		}

//		l.setOpaque(!label.getTransparent());

		return l;
	}

	public static byte[] loadIcon(FlattenedSolution solution, Integer key)
	{
		Media m = solution.getMedia(key.intValue());
		if (m != null)
		{
			return m.getMediaData();
		}
		return null;
	}

	private static IComponent createPortal(IApplication application, Form form, Portal meta, IDataProviderLookup dataProviderLookup, IScriptExecuter el,
		boolean printing)
	{
		RuntimePortal scriptable = new RuntimePortal(application.getItemFactory().createChangesRecorder(), application);
		IPortalComponent portalComponent = application.getItemFactory().createPortalComponent(scriptable, meta, form, dataProviderLookup, el, printing);
		scriptable.setComponent(portalComponent);
		FixedStyleSheet ss = ComponentFactory.getCSSStyleForForm(application, form);
		if (ss != null)
		{
			String lookupname = "portal";
			if (meta.getStyleClass() != null && !"".equals(meta.getStyleClass()))
			{
				lookupname += '.' + meta.getStyleClass();
			}
			portalComponent.setRowStyles(ss, ss.getRule(lookupname + " " + ISupportRowStyling.CLASS_ODD),
				ss.getRule(lookupname + " " + ISupportRowStyling.CLASS_EVEN), ss.getRule(lookupname + " " + ISupportRowStyling.CLASS_SELECTED));
		}
		return portalComponent;
	}

	private static IComponent createPart(IApplication application, Part meta)
	{
		IComponent part = application.getItemFactory().createPanel();
		java.awt.Color bg = meta.getBackground();
		if (bg != null) part.setBackground(bg);
		part.setSize(new Dimension(((Form)meta.getParent()).getSize().width, meta.getHeight()));
		return part;
	}

	private static IComponent createSplitPane(IApplication application, Form form, TabPanel meta, IScriptExecuter el)
	{
		RuntimeSplitPane scriptable = new RuntimeSplitPane(application.getItemFactory().createChangesRecorder(), application);
		ISplitPane splitPane = application.getItemFactory().createSplitPane(scriptable, getWebID(form, meta), meta.getTabOrientation());
		scriptable.setComponent(splitPane);
		applyBasicComponentProperties(application, splitPane, meta, getStyleForBasicComponent(application, meta, form));
		try
		{
			int index = 0;
			Iterator<IPersist> it = meta.getTabs();
			while (it.hasNext() && index < 2)
			{
				Tab tab = (Tab)it.next();
				Form f = application.getFlattenedSolution().getForm(tab.getContainsFormID());
				if (f != null)
				{
					IFormLookupPanel flp = splitPane.createFormLookupPanel(tab.getName(), tab.getRelationName(), f.getName());
					if (index < 1) splitPane.setLeftForm(flp);
					else splitPane.setRightForm(flp);
					index++;
				}
			}
		}
		catch (Exception ex)
		{
			Debug.error(ex);
		}

		splitPane.setRuntimeDividerLocation(meta.getTabOrientation() == TabPanel.SPLIT_HORIZONTAL ? splitPane.getSize().width / 2
			: splitPane.getSize().height / 2);
		if (el != null && meta.getOnChangeMethodID() > 0)
		{
			splitPane.setOnDividerChangeMethodCmd((Integer.toString(meta.getOnChangeMethodID())));
			splitPane.addScriptExecuter(el);
		}
		return splitPane;
	}

	private static IComponent createTabPanel(IApplication application, Form form, TabPanel meta, IScriptExecuter el)
	{
		//HACK:To set the selected color on a tabpanel
		Color oldColor = null;
		if (meta.getSelectedTabColor() != null)
		{
			oldColor = UIManager.getColor("TabbedPane.selected");
			UIManager.put("TabbedPane.selected", meta.getSelectedTabColor());
		}
		int orient = meta.getTabOrientation();
		RuntimeAccordionPanel scriptable = null;
		if (meta.getTabOrientation() == TabPanel.ACCORDION_PANEL)
		{
			scriptable = new RuntimeAccordionPanel(application.getItemFactory().createChangesRecorder(), application);
		}
		else
		{
			scriptable = new RuntimeTabPanel(application.getItemFactory().createChangesRecorder(), application);
		}
		ITabPanel tabs = application.getItemFactory().createTabPanel(scriptable, getWebID(form, meta), orient, meta.hasOneTab());
		scriptable.setComponent(tabs);
		if (meta.getScrollTabs())
		{
			tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		}
		else
		{
			tabs.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		}

		if (el != null && meta.getOnTabChangeMethodID() > 0)
		{
			tabs.setOnTabChangeMethodCmd(Integer.toString(meta.getOnTabChangeMethodID()),
				Utils.parseJSExpressions(meta.getInstanceMethodArguments("onTabChangeMethodID")));
			tabs.addScriptExecuter(el);
		}

		applyBasicComponentProperties(application, tabs, meta, getStyleForBasicComponent(application, meta, form));

		if (meta.getHorizontalAlignment() >= 0)
		{
			tabs.setHorizontalAlignment(meta.getHorizontalAlignment());
		}

		//HACK:restore so not all tabpanel get that color!
		if (meta.getSelectedTabColor() != null) UIManager.put("TabbedPane.selected", oldColor);

		try
		{
			int index = 0;
			Iterator<IPersist> it = meta.getTabs();
			while (it.hasNext())
			{
				Tab tab = (Tab)it.next();
				Form f = application.getFlattenedSolution().getForm(tab.getContainsFormID());
				if (f != null)
				{
					IFormLookupPanel flp = tabs.createFormLookupPanel(tab.getName(), tab.getRelationName(), f.getName());

					tabs.addTab(application.getI18NMessageIfPrefixed(tab.getText()), tab.getImageMediaID(), flp,
						application.getI18NMessageIfPrefixed(tab.getToolTipText()));

					Color fg = tab.getForeground();
					Color bg = tab.getBackground();
					if (fg != null) tabs.setTabForegroundAt(index, fg);
					if (bg != null) tabs.setTabBackgroundAt(index, bg);
					index++;
				}
			}
		}
		catch (Exception ex)
		{
			Debug.error(ex);
		}
		return tabs;
	}

	public static void flushStyle(IServiceProvider sp, Style style)
	{
		Map<Style, FixedStyleSheet> parsedStylesMap;
		if (sp != null && Boolean.TRUE.equals(style.getRuntimeProperty(MODIFIED_BY_CLIENT)))
		{
			parsedStylesMap = (ConcurrentMap<Style, FixedStyleSheet>)sp.getRuntimeProperties().get(PARSED_STYLES);
		}
		else
		{
			// static style, cache globally.
			parsedStylesMap = parsedStyles;
		}
		if (parsedStylesMap != null)
		{
			parsedStylesMap.remove(style);
		}
	}

	@SuppressWarnings("unchecked")
	public static void flushValueList(ValueList vl)
	{
		WeakHashMap<ValueList, Object> hmValueLists = (WeakHashMap<ValueList, Object>)J2DBGlobals.getServiceProvider().getRuntimeProperties().get(
			IServiceProvider.RT_VALUELIST_CACHE);
		if (hmValueLists != null)
		{
			hmValueLists.remove(vl);
		}
	}

	@SuppressWarnings("unchecked")
	public static void flushCachedItems()
	{
		parsedStyles = new ConcurrentHashMap<Style, FixedStyleSheet>();
		J2DBGlobals.getServiceProvider().getRuntimeProperties().put(IServiceProvider.RT_VALUELIST_CACHE, null);
		J2DBGlobals.getServiceProvider().getRuntimeProperties().put(IServiceProvider.RT_OVERRIDESTYLE_CACHE, null);

		Iterator<IconHolder> it = lstIcons.values().iterator();
		while (it.hasNext())
		{
			IconHolder ih = it.next();
			Icon icon = ih.icon.get();
			if (icon instanceof ImageIcon)
			{
				ImageIcon imageIcon = (ImageIcon)icon;
				if (imageIcon.getImage() != null)
				{
					imageIcon.getImage().flush();
				}
				imageIcon.setImageObserver(null);
			}
			else if (icon instanceof MyImageIcon)
			{
				((MyImageIcon)icon).flush();
			}
		}
		lstIcons = new WeakHashMap<Object, IconHolder>();
	}

	public static void applyScrollBarsProperty(IScrollPane pane, ISupportScrollbars element)
	{
		int scroll = element.getScrollbars();
		if (scroll != ISupportScrollbars.SCROLLBARS_WHEN_NEEDED)
		{
			if ((scroll & ISupportScrollbars.VERTICAL_SCROLLBAR_AS_NEEDED) == ISupportScrollbars.VERTICAL_SCROLLBAR_AS_NEEDED)
			{
				pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			}
			else
			{
				if ((scroll & ISupportScrollbars.VERTICAL_SCROLLBAR_ALWAYS) == ISupportScrollbars.VERTICAL_SCROLLBAR_ALWAYS)
				{
					pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				}
				else if ((scroll & ISupportScrollbars.VERTICAL_SCROLLBAR_NEVER) == ISupportScrollbars.VERTICAL_SCROLLBAR_NEVER)
				{
					pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
				}
			}
			if ((scroll & ISupportScrollbars.HORIZONTAL_SCROLLBAR_AS_NEEDED) == ISupportScrollbars.HORIZONTAL_SCROLLBAR_AS_NEEDED)
			{
				pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			}
			else
			{
				if ((scroll & ISupportScrollbars.HORIZONTAL_SCROLLBAR_ALWAYS) == ISupportScrollbars.HORIZONTAL_SCROLLBAR_ALWAYS)
				{
					pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
				}
				else if ((scroll & ISupportScrollbars.HORIZONTAL_SCROLLBAR_NEVER) == ISupportScrollbars.HORIZONTAL_SCROLLBAR_NEVER)
				{
					pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				}
			}
		}
	}

	public static void registerIcon(Icon icon)
	{
		if (icon == null) return;
		Object image;
		if (icon instanceof ImageIcon)
		{
			image = ((ImageIcon)icon).getImage();
		}
		else if (icon instanceof MyImageIcon)
		{
			ImageIcon myIcon = ((MyImageIcon)icon).getOriginal();
			if (myIcon != null)
			{
				image = myIcon.getImage();
			}
			else
			{
				image = icon;
			}
		}
		else image = null;

		IconHolder ih = lstIcons.get(image);
		if (ih == null)
		{
			lstIcons.put(image, new IconHolder(icon));
		}
		else
		{
			ih.counter++;
		}
	}

	public static void deregisterIcon(final Icon icon)
	{
		if (icon == null) return;
		Object image;
		if (icon instanceof ImageIcon)
		{
			image = ((ImageIcon)icon).getImage();
		}
		else if (icon instanceof MyImageIcon)
		{
			ImageIcon myIcon = ((MyImageIcon)icon).getOriginal();
			if (myIcon != null)
			{
				image = myIcon.getImage();
			}
			else
			{
				image = icon;
			}
		}
		else image = null;

		if (image != null)
		{
			IconHolder ih = lstIcons.get(image);
			if (ih != null)
			{
				ih.counter--;
				if (ih.counter == 0)
				{
					lstIcons.remove(image);
					final Object img = image;
					SwingUtilities.invokeLater(new Runnable()
					{
						public void run()
						{
							if (icon instanceof ImageIcon)
							{
								ImageIcon imageIcon = (ImageIcon)icon;
								if (!lstIcons.containsKey(img))
								{
									imageIcon.setImageObserver(null);
								}
							}
							else if (icon instanceof MyImageIcon)
							{
								if (img == null || !lstIcons.containsKey(img))
								{
									((MyImageIcon)icon).flush();
								}
							}
						}
					});
				}
			}
		}
	}

	static class IconHolder
	{
		public IconHolder(Icon icon)
		{
			this.icon = new WeakReference<Icon>(icon);
			this.counter = 1;
		}

		private final WeakReference<Icon> icon;
		private int counter;
	}

	/**
	 * @param originalStyleName
	 * @param newStyleName
	 */
	@SuppressWarnings("unchecked")
	public static void overrideStyle(String originalStyleName, String newStyleName)
	{
		HashMap<String, String> overridenStyles = (HashMap<String, String>)J2DBGlobals.getServiceProvider().getRuntimeProperties().get(
			IServiceProvider.RT_OVERRIDESTYLE_CACHE);
		if (overridenStyles == null)
		{
			overridenStyles = new HashMap<String, String>();
			J2DBGlobals.getServiceProvider().getRuntimeProperties().put(IServiceProvider.RT_OVERRIDESTYLE_CACHE, overridenStyles);
		}
		overridenStyles.put(originalStyleName, newStyleName);
	}

	/**
	 * Return a new list with the elements of the input list sorted on position. Grouped elements are placed together.
	 * @param elements
	 */
	public static List<IPersist> sortElementsOnPositionAndGroup(List<IPersist> elements)
	{
		if (elements == null) return null;

		// first sort on position, then move all grouped elements together
		List<IPersist> lst = new ArrayList<IPersist>(elements);
		Collections.sort(lst, PositionComparator.XY_PERSIST_COMPARATOR);
		for (int i = 0; i < lst.size(); i++)
		{
			IPersist element = lst.get(i);
			if (element instanceof IFormElement && ((IFormElement)element).getGroupID() != null)
			{
				// find other group elements, move them to the left
				for (int j = i + 2; j < lst.size(); j++)
				{
					IPersist element2 = lst.get(j);
					if (element2 instanceof IFormElement && ((IFormElement)element).getGroupID().equals(((IFormElement)element2).getGroupID()))
					{
						// same group, move to the left
						lst.add(i + 1, lst.remove(j));
						i++;
					}
				}
			}
		}
		return lst;
	}

	protected static IComponent createBean(IApplication application, Form form, Bean bean, FlattenedSolution flattenedSolution)
	{
		IComponent c = null;
		try
		{
			Object obj = getBeanInstanceFromXML(application, bean.getBeanClassName(), bean.getBeanXML());

			if (flattenedSolution != null && obj != null)
			{
				obj = flattenedSolution.setBeanDesignInstance(bean, obj);
			}

			if (obj instanceof Component)
			{
				((Component)obj).setName(bean.getName());
			}
			else if (obj instanceof IComponent)
			{
				((IComponent)obj).setName(bean.getName());
			}

			if (obj instanceof IServoyAwareBean)
			{
				((IServoyAwareBean)obj).initialize((IClientPluginAccess)application.getPluginAccess());
			}

			if (obj instanceof IServoyBeanFactory)
			{
				testReturnTypesForBean(application, obj);
				obj = ((IServoyBeanFactory)obj).getBeanInstance(application.getApplicationType(), (IClientPluginAccess)application.getPluginAccess(),
					new Object[] { ComponentFactory.getWebID(form, bean), form.getName(), form.getStyleName() });
			}
			testReturnTypesForBean(application, obj);
			if (obj instanceof Applet)
			{
				((FormManager)application.getFormManager()).initializeApplet((Applet)obj, bean.getSize());
			}

			if (obj == null)
			{
				c = application.getItemFactory().createLabel(ComponentFactory.getWebID(form, bean), "bean missing " + bean.getBeanClassName());
			}
			else if (!(obj instanceof java.awt.Component) && !(obj instanceof IComponent))
			{
				c = application.getItemFactory().createInvisibleBean(ComponentFactory.getWebID(form, bean), obj);
			}
			else if (!(obj instanceof IComponent))
			{
				c = application.getItemFactory().createBeanHolder(ComponentFactory.getWebID(form, bean), (Component)obj, bean.getAnchors());
			}
			else
			{
				c = (IComponent)obj;
			}

			// beans do not store the transparent property, keep the value from component
			boolean isOpaque = c.isOpaque();
			applyBasicComponentProperties(application, c, bean, null);
			c.setOpaque(isOpaque);
		}
		catch (Throwable e)//sometimes setting size or location throws exception or even error...create label instead
		{
			Debug.error(e);
			c = application.getItemFactory().createLabel(bean.getName(), "error acessing bean" + bean.getBeanClassName());
			java.awt.Dimension dim = bean.getSize();
			if (dim != null) c.setSize(bean.getSize());
		}

		return c;
	}

	private static void testReturnTypesForBean(IApplication application, Object beanInstance)
	{
		if (beanInstance instanceof IReturnedTypesProvider && ((IReturnedTypesProvider)beanInstance).getAllReturnedTypes() != null &&
			application.getScriptEngine() != null)
		{
			for (Class< ? > clz : ((IReturnedTypesProvider)beanInstance).getAllReturnedTypes())
			{
				ScriptObjectRegistry.getJavaMembers(clz, application.getScriptEngine().getGlobalScope());
			}
		}
	}

	public static boolean isSingleValue(ValueList valuelist, IValueList list)
	{
		if (list != null && valuelist != null)
		{
			return !(valuelist.getValueListType() == ValueList.DATABASE_VALUES && valuelist.getDatabaseValuesType() == ValueList.RELATED_VALUES) &&
				(list.getSize() == 1) && (valuelist.getAddEmptyValue() != ValueList.EMPTY_VALUE_ALWAYS);
		}
		return false;
	}

	public static boolean isButton(GraphicalComponent label)
	{
		return (label.getOnActionMethodID() != 0) && label.getShowClick();
	}
}
