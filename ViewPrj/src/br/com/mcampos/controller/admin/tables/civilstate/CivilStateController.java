package br.com.mcampos.controller.admin.tables.civilstate;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.user.attributes.CivilStateDTO;
import br.com.mcampos.ejb.cloudsystem.user.civilstate.facade.CivilStateFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;


public class CivilStateController extends SimpleTableController<CivilStateDTO>
{
	private CivilStateFacade session;

	protected Integer getNextId() throws ApplicationException
	{
		return getSession().nextId( getLoggedInUser() );
	}

	protected List getRecordList() throws ApplicationException
	{
		return getSession().getAll();
	}

	protected void delete( Object currentRecord ) throws ApplicationException
	{
		CivilStateDTO dto = ( CivilStateDTO )currentRecord;
		delete( dto.getId() );
	}

	protected Object createNewRecord()
	{
		return new CivilStateDTO();
	}

	protected void persist( Object e ) throws ApplicationException
	{
		if ( isAddNewOperation() ) {
			getSession().add( getLoggedInUser(), ( CivilStateDTO )e );
		}
		else
			getSession().update( getLoggedInUser(), ( CivilStateDTO )e );
	}

	public CivilStateFacade getSession()
	{
		if ( session == null )
			session = ( CivilStateFacade )getRemoteSession( CivilStateFacade.class );
		return session;
	}
}


