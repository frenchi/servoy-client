/*
 This file belongs to the Servoy development and deployment environment, Copyright (C) 1997-2012 Servoy BV

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

package com.servoy.j2db.scripting.solutionhelper;


/**
 * @author acostescu
 */
@SuppressWarnings("nls")
public interface IMobileProperties
{

	public static class MobileProperty<T>
	{
		public final String propertyName;
		public final T defaultValue;

		private MobileProperty(String propertyName, T defaultValue)
		{
			this.propertyName = propertyName;
			this.defaultValue = defaultValue;
		}
	}

	public final static MobileProperty<Boolean> MOBILE_FORM = new MobileProperty<Boolean>("mobileform", Boolean.FALSE);
	public final static MobileProperty<Boolean> HEADER_LEFT_BUTTON = new MobileProperty<Boolean>("headerLeftButton", Boolean.FALSE);
	public final static MobileProperty<Boolean> HEADER_RIGHT_BUTTON = new MobileProperty<Boolean>("headerRightButton", Boolean.FALSE);
	public final static MobileProperty<Boolean> HEADER_TEXT = new MobileProperty<Boolean>("headerText", Boolean.FALSE);
	public final static MobileProperty<Boolean> FOOTER_ITEM = new MobileProperty<Boolean>("footeritem", Boolean.FALSE);
	public final static MobileProperty<Boolean> FORM_TAB_PANEL = new MobileProperty<Boolean>("formtabpanel", Boolean.FALSE);
	public final static MobileProperty<Boolean> LIST_TAB_PANEL = new MobileProperty<Boolean>("list", Boolean.FALSE);
	public final static MobileProperty<Boolean> LIST_ITEM_BUTTON = new MobileProperty<Boolean>("listitemButton", Boolean.FALSE);
	public final static MobileProperty<Boolean> LIST_ITEM_SUBTEXT = new MobileProperty<Boolean>("listitemSubtext", Boolean.FALSE);
	public final static MobileProperty<Boolean> LIST_ITEM_COUNT = new MobileProperty<Boolean>("listitemCount", Boolean.FALSE);
	public final static MobileProperty<Boolean> LIST_ITEM_IMAGE = new MobileProperty<Boolean>("listitemImage", Boolean.FALSE);
	public final static MobileProperty<Boolean> LIST_ITEM_HEADER = new MobileProperty<Boolean>("listitemHeader", Boolean.FALSE);
	public final static MobileProperty<Integer> HEADER_SIZE = new MobileProperty<Integer>("headerSize", Integer.valueOf(4));
	public final static MobileProperty<Integer> RADIO_STYLE = new MobileProperty<Integer>("radioStyle", Integer.valueOf(0));
	public final static MobileProperty<String> DATA_ICON = new MobileProperty<String>("dataIcon", null);

	<T> void setPropertyValue(MobileProperty<T> property, T value);

	<T> T getPropertyValue(MobileProperty<T> property);

}
