package br.com.mcampos.controller.admin.tables.AccessLogType;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.user.login.AccessLogTypeDTO;
import br.com.mcampos.ejb.cloudsystem.security.accesslog.AccessLogTypeFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;


public class AccessLogTypeController extends SimpleTableController<AccessLogTypeDTO>
{
	private transient AccessLogTypeFacade session;

	public AccessLogTypeFacade getSession()
	{
		if ( session == null )
			session = ( AccessLogTypeFacade )getRemoteSession( AccessLogTypeFacade.class );
		return session;
	}

	public AccessLogTypeController()
	{
		super();
	}

	@Override
	protected Object createNewRecord()
	{
		return new AccessLogTypeDTO();
	}

	@Override
	protected Integer getNextId() throws ApplicationException
	{
		return getSession().getNextId( getLoggedInUser() );
	}

	@Override
	protected List getRecordList() throws ApplicationException
	{
		return getSession().getAll( getLoggedInUser() );
	}

	@Override
	protected void delete( Object currentRecord ) throws ApplicationException
	{
		getSession().delete( getLoggedInUser(), ( AccessLogTypeDTO )currentRecord );
	}

	@Override
	protected void persist( Object e ) throws ApplicationException
	{
		AccessLogTypeDTO dto = ( AccessLogTypeDTO )e;


		if ( isAddNewOperation() )
			getSession().add( getLoggedInUser(), dto );
		else
			getSession().update( getLoggedInUser(), dto );
	}
}
