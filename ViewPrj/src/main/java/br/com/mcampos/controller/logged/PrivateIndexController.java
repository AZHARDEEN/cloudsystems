package br.com.mcampos.controller.logged;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Center;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Window;

import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.sysutils.SysUtils;

public class PrivateIndexController extends LoggedBaseController<Window>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4639219683756455328L;

	protected Center mdiApplication;

	protected Menubar mainMenu;

	protected static final String attrMenu = "dto";

	public PrivateIndexController( )
	{
		super( );
	}

	public PrivateIndexController( char c )
	{
		super( c );
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		// AddBookmarkEventListener();
		System.out.println( getClass( ).getClassLoader( ).getClass( ).getName( ) );
	}

	private void clear( )
	{
		try {
			mdiApplication.getChildren( ).clear( );
		}
		catch ( Exception e ) {
			showErrorMessage( e.getMessage( ) );
		}
	}

	public void onMenuClick( Event evt ) throws Exception
	{
		Menuitem item;

		if ( evt.getTarget( ) instanceof Menuitem ) {
			item = (Menuitem) evt.getTarget( );
			Object obj = item.getAttribute( attrMenu );

			if ( obj != null && obj instanceof MenuDTO ) {
				MenuDTO dto = (MenuDTO) obj;
				if ( SysUtils.isEmpty( dto.getTargetURL( ) ) == false )
					gotoPage( dto.getTargetURL( ), mdiApplication );
				evt.stopPropagation( );
			}
		}
	}

	@Override
	public void onNotify( Event evt ) throws ApplicationException
	{
		if ( evt.getData( ) instanceof CompanyDTO ) {
			if ( mdiApplication != null && mdiApplication.getChildren( ) != null )
				clear( );
			if ( mainMenu == null )
				return;
		}
	}

	public void onClick$mnuLogout( Event evt )
	{
		Sessions.getCurrent( ).invalidate( );
		Executions.sendRedirect( "/index.zul" );
		if ( evt != null )
			evt.stopPropagation( );
	}

	public void onClick$mnuAnotoForm( Event evt )
	{
		gotoPage( "/private/admin/anoto/anoto_form.zul", mdiApplication );
	}

	public void onClick$mnuAnotoField( Event evt )
	{
		gotoPage( "/private/admin/anoto/anoto_field.zul", mdiApplication );
	}

	public void onClick$mnuAnotoPen( Event evt )
	{
		gotoPage( "/private/admin/anoto/anoto_pen.zul", mdiApplication );
	}

	public void onClick$mnuAnotoPenUser( Event evt )
	{
		gotoPage( "/private/admin/anoto/anoto_pen_user.zul", mdiApplication );
	}

	public void onClick$mnuAnotoView( Event evt )
	{
		gotoPage( "/private/admin/anoto/anoto_view2.zul", mdiApplication );
	}

	public void onClick$mnuAnotoQuality( Event evt )
	{
		gotoPage( "/private/admin/anoto/anoto_quality.zul", mdiApplication );
	}

	public void onClick$mnuPcg( Event evt )
	{
		gotoPage( "/private/admin/anoto/anoto_pcg.zul", mdiApplication );
	}

}
