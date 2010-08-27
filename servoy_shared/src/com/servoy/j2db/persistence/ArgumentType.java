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

package com.servoy.j2db.persistence;

/**
 * Models a datatype used especially with method templates.
 * 
 * @see com.servoy.j2db.persistence.MethodTemplate
 */
public class ArgumentType
{
	public static final ArgumentType String = new ArgumentType("String"); //$NON-NLS-1$
	public static final ArgumentType Number = new ArgumentType("Number"); //$NON-NLS-1$
	public static final ArgumentType Boolean = new ArgumentType("Boolean"); //$NON-NLS-1$
	public static final ArgumentType Color = new ArgumentType("Color"); //$NON-NLS-1$
	public static final ArgumentType Exception = new ArgumentType("Exception"); //$NON-NLS-1$
	public static final ArgumentType JSRecord = new ArgumentType("JSRecord"); //$NON-NLS-1$
	public static final ArgumentType JSEvent = new ArgumentType("JSEvent"); //$NON-NLS-1$
	public static final ArgumentType JSDataSet = new ArgumentType("JSDataSet"); //$NON-NLS-1$
	public static final ArgumentType Object = new ArgumentType("Object"); //$NON-NLS-1$
	public static final ArgumentType Date = new ArgumentType("Date"); //$NON-NLS-1$

	private final String name;

	/**
	 * @param string2
	 */
	private ArgumentType(String name)
	{
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	@Override
	public String toString()
	{
		return getName();
	}

	/**
	 * @param typeStr
	 * @return
	 */
	public static ArgumentType valueOf(String type)
	{
		if (type == null || Object.getName().equals(type)) return Object;
		if (String.getName().equals(type)) return String;
		if (Number.equals(type)) return Number;
		if (Boolean.getName().equals(type)) return Boolean;
		if (Color.getName().equals(type)) return Color;
		if (Exception.getName().equals(type)) return Exception;
		if (JSRecord.getName().equals(type)) return JSRecord;
		if (JSEvent.getName().equals(type)) return JSEvent;
		if (JSDataSet.getName().equals(type)) return JSDataSet;
		if (Date.getName().equals(type)) return Date;
		return new ArgumentType(type);
	}

	public static ArgumentType convertFromColumnType(int columnType, String typeSuggestion)
	{
		if (columnType == IColumnTypes.DATETIME) return Date;
		else if (columnType == IColumnTypes.INTEGER || columnType == IColumnTypes.NUMBER) return Number;
		else if (columnType == IColumnTypes.TEXT) return String;
		else
		{
			if (typeSuggestion != null) return valueOf(typeSuggestion);
			else return Object;
		}
	}
}
