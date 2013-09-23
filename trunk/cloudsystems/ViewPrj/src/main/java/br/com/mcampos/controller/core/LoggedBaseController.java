package br.com.mcampos.controller.core;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import br.com.mcampos.controller.user.person.MyRecord;
import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.dto.user.attributes.UserStatusDTO;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.util.business.LoginLocator;
import br.com.mcampos.util.system.ImageUtil;

public class LoggedBaseController<T extends Component> extends BaseController<T>
{
	protected static String alternativePath = "/private/index.zul";
	protected static String errorTitleI3 = "ErrorTitle";
	protected LoginLocator loginLocator;

	private Image imageClienteLogo;
	private Image imageCompanyLogo;

	protected Label labelTitle;

	public LoggedBaseController( )
	{
		super( );
	}

	public LoggedBaseController( char c )
	{
		super( c );
	}

	@Override
	public ComponentInfo doBeforeCompose( Page page, Component parent, ComponentInfo compInfo )
	{
		if ( isUserLogged( ) == false ) {
			redirect( "/index.zul" );
			return null;
		}
		else {
			AuthenticationDTO user = getLoggedInUser( );
			int status;

			try {
				status = getLoginLocator( ).getStatus( user );
				switch ( status ) {
				case UserStatusDTO.statusFullfillRecord:
					String path = page.getRequestPath( );
					if ( path.endsWith( "myrecord.zul" ) == false && ( this instanceof MyRecord ) == false ) {
						redirect( "/private/user/person/myrecord.zul" );
						return null;
					}
					else
						return super.doBeforeCompose( page, parent, compInfo );
				case UserStatusDTO.statusExpiredPassword:
					redirect( "/private/change_password.zul" );
					break;
				default:
					return super.doBeforeCompose( page, parent, compInfo );
				}
			}
			catch ( ApplicationException e ) {
				showErrorMessage( e.getMessage( ), "Erro ao obter status do usuário" );
				redirect( "/index.zul" );
			}
		}
		return null;
	}

	public LoginLocator getLoginLocator( )
	{
		if ( loginLocator == null )
			loginLocator = new LoginLocator( );
		return loginLocator;
	}

	protected void showErrorMessage( String description )
	{
		showErrorMessage( description, errorTitleI3 );
	}

	protected void showErrorMessage( String description, String titleId )
	{
		String title = getLabel( titleId );
		if ( SysUtils.isEmpty( title ) )
			title = getLabel( errorTitleI3 );
		Messagebox.show( description, SysUtils.isEmpty( title ) ? "Error" : title, Messagebox.OK, Messagebox.ERROR );
	}

	public void onNotify( Event evt ) throws ApplicationException
	{
		if ( evt.getData( ) instanceof String ) {
			String message = (String) evt.getData( );
			if ( message.equals( "changeLogo" ) )
				showLogo( );
		}
	}

	@Override
	public void doAfterCompose( T comp ) throws Exception
	{
		super.doAfterCompose( comp );
		showLogo( );
		if ( labelTitle != null )
			labelTitle.setValue( getPageTitle( ) );
	}

	protected String getPageTitle( )
	{
		return "";
	}

	private void showLogo( )
	{
		if ( imageClienteLogo == null && imageCompanyLogo == null )
			return;

		MediaDTO[ ] medias = getLoginLocator( ).getLogo( getLoggedInUser( ) );
		if ( medias != null ) {
			if ( imageClienteLogo != null && medias[ 0 ] != null && medias[ 0 ].getObject( ) != null )
				ImageUtil.loadImage( imageClienteLogo, medias[ 0 ] );
			if ( imageCompanyLogo != null && medias[ 1 ] != null && medias[ 1 ].getObject( ) != null )
				ImageUtil.loadImage( imageCompanyLogo, medias[ 1 ] );
		}
	}

	protected void setLabel( Columns cols )
	{
		if ( cols != null && cols.getChildren( ).size( ) > 0 )
			for ( int index = 0; index < cols.getChildren( ).size( ); index++ ) {
				Column col = (Column) cols.getChildren( ).get( index );
				if ( col != null )
					setLabel( col );
			}
	}
}