package br.com.mcampos.controller.anoto.renderer;


import br.com.mcampos.dto.anoto.PenUserDTO;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.util.system.IClickEvent;

import java.io.Serializable;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Toolbarbutton;


public class PenUserRowRenderer implements RowRenderer, Serializable
{

    IClickEvent listener;

    public PenUserRowRenderer( IClickEvent listener )
    {
        super();
        this.listener = listener;
    }

    public void render( Row row, Object data ) throws Exception
    {
        if ( row.getChildren() != null && row.getChildren().size() > 0 )
            row.getChildren().clear();
        row.setValue( data );
        if ( data == null )
            return;
        PenUserDTO dto = ( PenUserDTO )data;
        new Label( dto.getPenId() ).setParent( row );
        if ( dto.getUser() != null ) {
            new Label( dto.getUser().getName() ).setParent( row );
        }
        else {
            new Label( "-" ).setParent( row );
        }
        new Label( dto.getFromDate() != null ? SysUtils.formatDate( dto.getFromDate() ) : "-" ).setParent( row );
        Toolbarbutton button = new Toolbarbutton( Labels.getLabel( "btnChangeUser" ) );
        button.setParent( row );
        button.setImage( "/img/user.png" );
        setButtonListener( button );
    }

    protected void setButtonListener( Component c )
    {
        if ( listener == null )
            return;
        c.addEventListener( Events.ON_CLICK, new EventListener()
            {
                public void onEvent( Event event ) throws Exception
                {
                    listener.onClick( ( MouseEvent )event );
                }
            } );
    }
}
