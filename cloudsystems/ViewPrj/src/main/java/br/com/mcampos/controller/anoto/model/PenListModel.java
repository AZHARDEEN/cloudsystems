package br.com.mcampos.controller.anoto.model;

import java.util.Collections;
import java.util.List;

import br.com.mcampos.controller.admin.users.model.AbstractPagingListModel;
import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.facade.AnotoPenFacade;
import br.com.mcampos.sysutils.ServiceLocator;

public class PenListModel extends AbstractPagingListModel<PenDTO>
{
	private AnotoPenFacade session;

	public PenListModel( int startPageNumber, int pageSize )
	{
		super( startPageNumber, pageSize );
	}

	@Override
	public int getTotalSize( )
	{
		try {
			return getSession( ).count( );
		}
		catch ( ApplicationException e ) {
			return 0;
		}
	}

	@Override
	public List<PenDTO> getPageData( int itemStartNumber, int pageSize )
	{
		try {
			return getSession( ).getPens( itemStartNumber, pageSize );
		}
		catch ( ApplicationException e ) {
			return Collections.emptyList( );
		}
	}

	private AnotoPenFacade getSession( )
	{
		if ( session == null )
			session = (AnotoPenFacade) getRemoteSession( AnotoPenFacade.class );
		return session;
	}

	private Object getRemoteSession( Class remoteClass )
	{
		try {
			return ServiceLocator.getInstance( ).getRemoteSession( remoteClass, ServiceLocator.EJB_NAME[ 1 ] );
		}
		catch ( Exception e ) {
			throw new NullPointerException( "Invalid EJB Session (possible null)" );
		}
	}

	@Override
	public int getSize( )
	{
		return getTotalSize( );
	}
}
