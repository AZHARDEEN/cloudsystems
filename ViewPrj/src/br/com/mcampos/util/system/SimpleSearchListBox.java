package br.com.mcampos.util.system;


import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.util.locator.ServiceLocator;
import br.com.mcampos.util.locator.ServiceLocatorException;

import java.io.Serializable;

import java.util.List;

import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.South;
import org.zkoss.zul.Window;


public abstract class SimpleSearchListBox extends Window
{
    private static final long serialVersionUID = 8109634704496621100L;
    private Listbox listbox;
    private int _height = 400;
    private int _width = 300;
    private Serializable selected;


    protected String _title;
    protected String _listHeader1;


    protected abstract List getList();

    public SimpleSearchListBox( String title, String border, boolean closable )
    {
        super( title, border, closable );
    }


    protected void createBox()
    {

        // Window
        this.setWidth( String.valueOf( _width ) + "px" );
        this.setHeight( String.valueOf( _height ) + "px" );
        this.setTitle( _title );
        this.setVisible( true );
        this.setClosable( true );

        // Borderlayout
        Borderlayout bl = new Borderlayout();
        bl.setHeight( "100%" );
        bl.setWidth( "100%" );
        bl.setParent( this );

        Center center = new Center();
        center.setFlex( true );
        center.setParent( bl );

        South south = new South();
        south.setHeight( "26px" );
        south.setParent( bl );

        // Button
        Button btnOK = new Button();
        btnOK.setLabel( "OK" );
        btnOK.addEventListener( "onClick", new OnCloseListener() );
        btnOK.setParent( south );

        // Listbox
        listbox = new Listbox();
        listbox.setStyle( "border: none;" );
        listbox.setHeight( "100%" );
        listbox.setVisible( true );
        listbox.setParent( center );
        listbox.setItemRenderer( new SearchBoxItemRenderer() );

        Listhead listhead = new Listhead();
        listhead.setParent( listbox );
        Listheader listheader = new Listheader();
        listheader.setParent( listhead );
        listheader.setLabel( _listHeader1 );

        // Model
        List list = getList();
        listbox.setModel( new ListModelList( list, true ) );
        try {
            if ( SysUtils.isEmpty( list ) == false ) {
                listbox.setFocus( true );
            }
            doModal();
        }
        catch ( SuspendNotAllowedException e ) {
            this.detach();
        }
        catch ( InterruptedException e ) {
            this.detach();
        }
    }

    private Serializable getValue()
    {
        if ( listbox == null || listbox.getSelectedItem() == null )
            return null;
        return ( Serializable )listbox.getSelectedItem().getValue();
    }

    public void onDoubleClicked( Event event )
    {

        if ( listbox.getSelectedItem() != null ) {
            setSelected( getValue() );
            this.onClose();
        }
    }

    protected void setSelected( Serializable selected )
    {
        this.selected = selected;
    }

    public Object getSelected()
    {
        return selected;
    }

    final class OnCloseListener implements EventListener
    {
        @Override
        public void onEvent( Event event ) throws Exception
        {
            if ( listbox.getSelectedItem() != null ) {
                setSelected( getValue() );
            }
            onClose();
        }
    }

    final class SearchBoxItemRenderer implements ListitemRenderer
    {

        @Override
        public void render( Listitem item, Object data ) throws Exception
        {
            Listcell lc = new Listcell( data.toString() );
            lc.setParent( item );
            item.setValue( data );
            ComponentsCtrl.applyForward( item, "onDoubleClick=onDoubleClicked" );
        }
    }

    protected Object getRemoteSession( Class remoteClass )
    {
        try {
            return ServiceLocator.getInstance().getRemoteSession( remoteClass );
        }
        catch ( ServiceLocatorException e ) {
            throw new NullPointerException( "Invalid EJB Session (possible null)" );
        }
    }

}
