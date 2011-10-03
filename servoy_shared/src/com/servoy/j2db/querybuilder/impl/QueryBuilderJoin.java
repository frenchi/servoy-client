/*
 This file belongs to the Servoy development and deployment environment, Copyright (C) 1997-2011 Servoy BV

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

package com.servoy.j2db.querybuilder.impl;

import com.servoy.j2db.persistence.RepositoryException;
import com.servoy.j2db.query.QueryJoin;
import com.servoy.j2db.query.QueryTable;
import com.servoy.j2db.querybuilder.IQueryBuilderJoin;
import com.servoy.j2db.scripting.annotations.JSReadonlyProperty;

/**
 * @author rgansevles
 *
 */
public class QueryBuilderJoin extends QueryBuilderTableClause implements IQueryBuilderJoin
{
	private final QueryJoin join;

	private QueryBuilderLogicalCondition on;

	QueryBuilderJoin(QueryBuilder root, QueryBuilderTableClause parent, String dataSource, QueryJoin join)
	{
		super(root, parent, dataSource);
		this.join = join;
	}

	@Override
	QueryTable getQueryTable() throws RepositoryException
	{
		return join.getForeignTable();
	}

	@JSReadonlyProperty
	public QueryBuilderLogicalCondition on()
	{
		if (on == null)
		{
			on = new QueryBuilderLogicalCondition(getRoot(), this, join.getCondition());
		}
		return on;
	}
}
