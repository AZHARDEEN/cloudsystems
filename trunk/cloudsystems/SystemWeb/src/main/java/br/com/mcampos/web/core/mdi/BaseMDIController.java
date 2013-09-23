package br.com.mcampos.web.core.mdi;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.BookmarkEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Window;

import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseController;
import br.com.mcampos.web.core.bookmark.Bookmark;
import br.com.mcampos.web.core.bookmark.BookmarkSession;

public abstract class BaseMDIController extends BaseController<Window>
{
	private static final long serialVersionUID = 7696453868326572168L;

	private BookmarkSession session;

	@Wire( "div#mdiApplication" )
	private Div mdiApplication;

	public BaseMDIController( )
	{
		super( );
	}

	protected Component getMdiApplication( )
	{
		return this.mdiApplication;
	}

	protected void loadPage( String page, boolean mustClear )
	{
		gotoPage( page, getMdiApplication( ), null, mustClear );
		BookmarkSession session = getBookmarkSession( );
		Integer nIndex = session.set( new Bookmark( page, null ) );
		Executions.getCurrent( ).getDesktop( ).setBookmark( nIndex.toString( ) );
	}

	@Listen( "onBookmarkChange = window" )
	public void onBookmark( BookmarkEvent event )
	{
		String str = event.getBookmark( );
		if ( SysUtils.isEmpty( str ) == false ) {
			BookmarkSession session = getBookmarkSession( );
			Integer nIndex = Integer.parseInt( str );
			Bookmark bookmark = session.get( nIndex );
			if ( bookmark != null ) {
				gotoPage( bookmark.getUri( ), getMdiApplication( ), bookmark.getParameters( ), true );
			}
			else {
				Executions.getCurrent( ).getDesktop( ).setBookmark( "" );
			}
		}
	}

	private BookmarkSession getBookmarkSession( )
	{
		if ( this.session == null ) {
			this.session = new BookmarkSession( );
		}
		return this.session;
	}
}
