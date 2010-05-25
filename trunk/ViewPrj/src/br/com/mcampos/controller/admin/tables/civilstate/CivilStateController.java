package br.com.mcampos.controller.admin.tables.civilstate;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.user.attributes.CivilStateDTO;

import java.util.Collections;
import java.util.List;


public class CivilStateController extends SimpleTableController<CivilStateDTO>
{
	protected Integer getNextId()
	{
		return null;
	}

	protected List getRecordList()
	{
		return Collections.emptyList();
	}

	protected void delete( Object currentRecord )
	{
	}

	protected Object createNewRecord()
	{
		return null;
	}

	protected void persist( Object e )
	{
	}
}


