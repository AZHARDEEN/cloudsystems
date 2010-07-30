package br.com.mcampos.controller.user.person;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.LoginPropertyDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.ejb.cloudsystem.system.loginproperty.facade.LoginPropertyFacade;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.util.locator.ServiceLocator;
import br.com.mcampos.util.locator.ServiceLocatorException;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.impl.api.InputElement;


public class SystemUserPropertyGridRenderer implements RowRenderer
{
    private AuthenticationDTO currentUser;
    private LoginPropertyFacade session;

    public SystemUserPropertyGridRenderer( AuthenticationDTO loggedUser )
    {
        super();
        this.currentUser = loggedUser;
    }

    public void render( Row row, Object data ) throws Exception
    {
        row.setValue( data );
        LoginPropertyDTO dto = ( LoginPropertyDTO )data;
        row.appendChild( new Label( dto.getProperty().getDescription() ) );
        if ( dto.getProperty().getType().getId().equals( 1 ) ) {
            createMenuOptions( dto, row );
        }
        else {
            createBasicControls( dto, row );
        }
    }

    private void createMenuOptions( LoginPropertyDTO dto, Row row )
    {
        Combobox combo = new Combobox();
        combo.setWidth( "95%" );
        combo.setReadonly( true );
        try {
            loadMenu( combo, getSession().getMenus( currentUser ), dto.getValue() );
            combo.addEventListener( Events.ON_SELECT, new EventListener()
                {
                    public void onEvent( Event event ) throws Exception
                    {
                        onSelectComboMenu( ( ( SelectEvent )event ) );
                    }
                } );

        }
        catch ( ApplicationException e ) {
            e = null;
        }
        row.appendChild( combo );
    }

    private void createBasicControls( LoginPropertyDTO dto, Row row )
    {
        int type = dto.getProperty().getType().getId();
        InputElement control = null;
        switch ( type ) {
        case 1:
            control = new Textbox( dto.getValue() );
            control.setWidth( "95%" );
            break;
        case 2:
            control = new Intbox( parseInteger( dto.getValue() ) );
            break;
        case 3:
            control = new Datebox( parseDate( dto.getValue() ) );
            break;
        case 4:
            control = new Timebox( parseTime( dto.getValue() ) );
            break;
        case 5:
            control = new Decimalbox( parseDouble( dto.getValue() ) );
            break;
        case 6:
            Checkbox item = new Checkbox();
            item.setChecked( parseBoolean( dto.getValue() ) );
            row.appendChild( item );
            break;
        }
        if ( control != null ) {
            row.appendChild( control );
        }

    }

    private Integer parseInteger( String value )
    {
        if ( SysUtils.isEmpty( value ) )
            return 0;
        Integer aux;
        try {
            aux = Integer.parseInt( value );
        }
        catch ( Exception e ) {
            e = null;
            aux = 0;
        }
        return aux;
    }

    private Boolean parseBoolean( String value )
    {
        if ( SysUtils.isEmpty( value ) )
            return false;
        Boolean aux;
        try {
            aux = Boolean.parseBoolean( value );
        }
        catch ( Exception e ) {
            e = null;
            aux = false;
        }
        return aux;
    }

    private Date parseDate( String value )
    {
        if ( SysUtils.isEmpty( value ) )
            return new Date();
        Date aux;
        try {
            DateFormat df = new SimpleDateFormat( "yyyyMMdd HHmmss" );
            aux = df.parse( value );
        }
        catch ( Exception e ) {
            e = null;
            aux = new Date();
        }
        return aux;
    }

    private Date parseTime( String value )
    {
        Date aux;
        if ( SysUtils.isEmpty( value ) )
            return new Date();
        try {
            DateFormat df = new SimpleDateFormat( "HHmmss" );
            aux = df.parse( value );
        }
        catch ( Exception e ) {
            e = null;
            aux = new Date();
        }
        return aux;
    }

    private BigDecimal parseDouble( String value )
    {
        if ( SysUtils.isEmpty( value ) )
            return new BigDecimal( 0.0 );
        double aux;
        try {
            aux = Double.parseDouble( value );
        }
        catch ( Exception e ) {
            e = null;
            aux = 0.0;
        }
        return new BigDecimal( aux );
    }

    public LoginPropertyFacade getSession()
    {
        if ( session == null )
            session = ( LoginPropertyFacade )getRemoteSession( LoginPropertyFacade.class );
        return session;
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

    private void loadMenu( Combobox combo, List<MenuDTO> list, String path )
    {
        if ( combo == null || SysUtils.isEmpty( list ) )
            return;

        if ( combo.getChildren() != null )
            combo.getChildren().clear();
        if ( SysUtils.isEmpty( list ) == false ) {
            for ( MenuDTO dto : list ) {
                Comboitem item = combo.appendItem( dto.toString() );
                if ( item != null ) {
                    item.setValue( dto );
                    if ( path != null && path.equals( dto.getTargetURL() ) )
                        combo.setSelectedItem( item );
                }
            }
        }
    }

    private void onSelectComboMenu( SelectEvent evt )
    {
        LinkedHashSet<Comboitem> set = ( LinkedHashSet<Comboitem> )evt.getSelectedItems();
        for ( Comboitem item : set ) {
            MenuDTO dto = ( MenuDTO )item.getValue();
            if ( dto != null ) {
                try {
                    getSession().update( currentUser, 1, dto.getTargetURL() );
                }
                catch ( ApplicationException e ) {
                }
            }
        }
    }
}
