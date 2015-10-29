/*
 This file belongs to the Servoy development and deployment environment, Copyright (C) 1997-2015 Servoy BV

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

package com.servoy.j2db.server.ngclient.utils;

import com.servoy.j2db.IApplication;
import com.servoy.j2db.persistence.ISupportChilds;
import com.servoy.j2db.persistence.WebComponent;
import com.servoy.j2db.scripting.solutionmodel.IJSParent;
import com.servoy.j2db.scripting.solutionmodel.JSWebComponent;
import com.servoy.j2db.server.ngclient.persistence.WebComponentImpl;
import com.servoy.j2db.server.ngclient.scripting.solutionmodel.JSWebComponentImpl;
import com.servoy.j2db.server.shared.IWebComponentImplFactory;
import com.servoy.j2db.util.UUID;

/**
 * @author gboros
 *
 */
public class NGWebComponentImplFactory implements IWebComponentImplFactory
{
	@Override
	public WebComponent newWebComponent(ISupportChilds parent, int element_id, UUID uuid)
	{
		return new WebComponentImpl(parent, element_id, uuid);
	}

	@Override
	public JSWebComponent newJSWebComponent(IJSParent< ? > parent, WebComponent baseComponent, IApplication application, boolean isNew)
	{
		return new JSWebComponentImpl(parent, baseComponent, application, isNew);
	}
}
