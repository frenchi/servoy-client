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

package com.servoy.j2db.scripting.solutionmodel;

import org.mozilla.javascript.annotations.JSGetter;
import org.mozilla.javascript.annotations.JSSetter;

import com.servoy.j2db.documentation.ServoyDocumented;
import com.servoy.j2db.persistence.Bean;
import com.servoy.j2db.scripting.IJavaScriptType;
import com.servoy.j2db.solutionmodel.ISMBean;

/**
 * @author lvostinar
 *
 */
@ServoyDocumented(category = ServoyDocumented.RUNTIME, extendsComponent = "JSComponent")
public class JSBean extends JSComponent<Bean> implements IJavaScriptType, ISMBean
{
	public JSBean(IJSParent< ? > parent, Bean baseComponent, boolean isNew)
	{
		super(parent, baseComponent, isNew);
	}

	/**
	 * The bean class name.
	 * 
	 * @sample
	 * var bean = form.getBean('mybean');
	 * application.output(bean.className);
	 */
	@JSGetter
	public String getClassName()
	{
		return getBaseComponent(false).getBeanClassName();
	}

	@JSSetter
	public void setClassName(String className)
	{
		getBaseComponent(true).setBeanClassName(className);
	}

	/**
	 * @sameas com.servoy.j2db.scripting.solutionmodel.JSComponent#getBackground()
	 * 
	 * @deprecated please refer to JSComponent.background
	 */
	@Override
	@Deprecated
	@JSGetter
	public String getBackground()
	{
		return super.getBackground();
	}

	/**
	 * @sameas com.servoy.j2db.scripting.solutionmodel.JSComponent#getBorderType()
	 * 
	 * @deprecated please refer to JSComponent.borderType
	 */
	@Override
	@Deprecated
	@JSGetter
	public String getBorderType()
	{
		return super.getBorderType();
	}

	/**
	 * @sameas com.servoy.j2db.scripting.solutionmodel.JSComponent#getFontType()
	 * 
	 * @deprecated please refer to JSComponent.fontType
	 */
	@Override
	@Deprecated
	@JSGetter
	public String getFontType()
	{
		return super.getFontType();
	}

	/**
	 * @sameas com.servoy.j2db.scripting.solutionmodel.JSComponent#getForeground()
	 * 
	 * @deprecated please refer to JSComponent.foreground
	 */
	@Override
	@Deprecated
	@JSGetter
	public String getForeground()
	{
		return super.getForeground();
	}

	/**
	 * @sameas com.servoy.j2db.scripting.solutionmodel.JSComponent#getPrintSliding()
	 * 
	 * @deprecated please refer to JSComponent.printSliding
	 */
	@Override
	@Deprecated
	@JSGetter
	public int getPrintSliding()
	{
		return super.getPrintSliding();
	}

	/**
	 * @sameas com.servoy.j2db.scripting.solutionmodel.JSComponent#getStyleClass()
	 * 
	 * @deprecated please refer to JSComponent.styleClass
	 */
	@Override
	@Deprecated
	@JSGetter
	public String getStyleClass()
	{
		return super.getStyleClass();
	}

	/**
	 * @sameas com.servoy.j2db.scripting.solutionmodel.JSComponent#getTransparent()
	 * 
	 * @deprecated please refer to JSComponent.transparent
	 */
	@Override
	@Deprecated
	@JSGetter
	public boolean getTransparent()
	{
		return super.getTransparent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servoy.j2db.scripting.solutionmodel.ISMBeanx#toString()
	 */
	@SuppressWarnings("nls")
	@Override
	public String toString()
	{
		return "JSBean[name:" + getName() + ",classname:" + getClassName() + ']';
	}
}
