package br.com.mcampos.controller.user.person;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;

import br.com.mcampos.controller.commom.user.PersonController;
import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.ejb.cloudsystem.user.person.facade.PersonFacade;

public class MyRecord extends PersonController
{
	private static final long serialVersionUID = 1066477294903546137L;
	private PersonFacade session;
	private Label labelMyRecordTitle;

	public MyRecord( )
	{
		super( );
	}

	public MyRecord( char c )
	{
		super( c );
	}

	@Override
	public void doAfterCompose( Component comp ) throws Exception
	{
		super.doAfterCompose( comp );
		PersonDTO dto = this.getSession( ).get( this.getLoggedInUser( ) );
		this.showInfo( dto );
		this.setLabel( this.labelMyRecordTitle );
	}

	private PersonFacade getSession( )
	{
		if( this.session == null ) {
			this.session = (PersonFacade) this.getRemoteSession( PersonFacade.class );
		}
		return this.session;
	}

	@Override
	protected Boolean persist( ) throws ApplicationException
	{
		if( !super.persist( ) ) {
			return false;
		}
		PersonDTO dto = this.getCurrentDTO( );
		Integer oldStatus = this.getSession( ).getLoginStatus( this.getLoggedInUser( ) );
		this.getSession( ).updateMyRecord( this.getLoggedInUser( ), dto );
		if( oldStatus.equals( 5 ) ) {
			this.redirect( "/private/index.zul" );
		}
		return true;
	}

}
