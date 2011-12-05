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
package com.servoy.j2db.server.headlessclient;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.AppendingStringBuffer;

/**
 * Web-client DIV window customised to Servoy needs.
 * @author acostescu
 */
public class ServoyDivDialog extends DivWindow
{

	private boolean closeAll = false; // for legacy modal dialog behavior (when multiple forms were shown in same dialog and close would either close one at a time or all)
	private final String name;

	public ServoyDivDialog(String id, boolean isInsideIFrame, String name)
	{
		super(id, isInsideIFrame);
		this.name = name;
	}

	public ServoyDivDialog(String id, IModel< ? > model, boolean isInsideIFrame, String name)
	{
		super(id, model, isInsideIFrame);
		this.name = name;
	}

	public void setCloseAll(boolean closeAll)
	{
		this.closeAll = closeAll;
	}

	public boolean getCloseAll()
	{
		return closeAll;
	}

	@Override
	protected void onAfterRender()
	{
		super.onAfterRender();
		Page parentPage = getPage();
		if (parentPage instanceof MainPage) ((MainPage)parentPage).setShowPageInDialogDelayed(false);
	}

	@Override
	public void show(AjaxRequestTarget target)
	{
		if (!isShown())
		{
			target.appendJavascript("Wicket.Window.unloadConfirmation = false;");
		}
		super.show(target);
	}


	@Override
	@SuppressWarnings("nls")
	protected AppendingStringBuffer postProcessSettings(AppendingStringBuffer settings)
	{
		AppendingStringBuffer buffer = super.postProcessSettings(settings);
		buffer.append("var userOnCloseButton = settings.onCloseButton;\n");
		buffer.append("if (userOnCloseButton){\n");
		buffer.append("var cb = null;\n");
		buffer.append("settings.onCloseButton = function() { if(!cb) { cb=this.caption.getElementsByTagName('a')[0];Wicket.Event.add(cb, 'blur', function() {setTimeout(userOnCloseButton, 500); }); } cb.focus();cb.blur();};\n");
		buffer.append("}");
		return buffer;
	}

	public String getName()
	{
		return name;
	}
}