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
package com.servoy.j2db.dataprocessing;


import javax.swing.ListModel;

public interface IValueList extends ListModel
{
	/**
	 * Constant for the value that should be interpreted as a separator when possible by fields that display the valuelist.
	 */
	public static final Object SEPARATOR_VALUE = "-";

	public Object getRealElementAt(int row);//real value, getElementAt is display value

	public String getRelationName();

	public void fill(IRecordInternal parentState);//to create all the rows

	public int realValueIndexOf(Object obj);

	public int indexOf(Object elem);

	public void deregister();

	public boolean getAllowEmptySelection();

	public String getName();

	/**
	 * @return
	 */
	public boolean hasRealValues();

	public void setFallbackValueList(IValueList list);

	public IValueList getFallbackValueList();
}
