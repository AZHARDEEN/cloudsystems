package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.anoto.renderer.GridProperties;
import br.com.mcampos.controller.anoto.renderer.PgcListRendered;
import br.com.mcampos.controller.anoto.renderer.PropertyRowRenderer;
import br.com.mcampos.controller.anoto.util.PgcFile;
import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.dto.anoto.PgcStatusDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.facade.AnodeFacade;
import br.com.mcampos.sysutils.SysUtils;

import com.anoto.api.core.NoSuchPermissionException;
import com.anoto.api.core.Pen;
import com.anoto.api.core.PenCreationException;
import com.anoto.api.core.PenHome;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;


public class AnotoPcgController extends LoggedBaseController
{
    private AnodeFacade session;

    private Button cmdCreate;
    private Button cmdRefresh;
    private Button btnAddAttach;
    private Button btnExport;
    private Button cmdDelete;

    private Listheader listHeaderName;

    private Label labelPGCTitle;

    private Listbox listboxRecord;
    private Grid gridProperties;

    public AnotoPcgController()
    {
        super();
    }

    protected AnodeFacade getSession()
    {
        if ( session == null )
            session = ( AnodeFacade )getRemoteSession( AnodeFacade.class );
        return session;
    }


    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );

        listboxRecord.setItemRenderer( new PgcListRendered() );
        gridProperties.setRowRenderer( new PropertyRowRenderer() );
        gridProperties.setModel( new ListModelList( new ArrayList<GridProperties>() ) );
        refresh();
        setLabel( cmdCreate );
        setLabel( cmdRefresh );
        setLabel( btnAddAttach );
        setLabel( btnExport );
        setLabel( cmdDelete );

        setLabel( listHeaderName );

        setLabel( labelPGCTitle );
    }

    private File checkAndCreateDir( File directory ) throws Exception
    {
        if ( !directory.exists() ) {
            directory.mkdirs();
        }
        return directory;
    }

    public void onUpload$btnAddAttach( UploadEvent evt )
    {

        PgcFile pgcFile = new PgcFile();

        try {
            pgcFile.uploadPgc( evt );
            pgcFile.persist( null );
            if ( pgcFile.getCurrentPgc() != null && pgcFile.getCurrentPgc().getPgcStatus().getId() != PgcStatusDTO.statusOk ) {
                ListModelList model = ( ListModelList )listboxRecord.getModel();
                if ( model != null )
                    model.add( 0, pgcFile.getCurrentPgc() );
            }

        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage() );
        }
    }

    protected Pen getPenObject( byte[] pgc )
    {
        ByteArrayInputStream is = new ByteArrayInputStream( pgc );
        try {
            return PenHome.read( is );
        }
        catch ( PenCreationException e ) {
            showErrorMessage( e.getMessage() );
            return null;
        }
    }

    public void onSelect$listboxRecord()
    {
        PGCDTO dto = ( PGCDTO )listboxRecord.getSelectedItem().getValue();

        try {
            byte[] bytepgc = getSession().getObject( dto.getMedia() );
            Pen pen = getPenObject( bytepgc );
            showProperties( pen, dto );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage() );
        }
    }

    protected void showProperties( Pen pen, PGCDTO pgc )
    {
        if ( pen == null )
            return;
        ListModelList list = ( ListModelList )gridProperties.getModel();
        list.clear();

        try {
            addProperty( "Status do PGC", pgc.getPgcStatus() );
            addProperty( "MagicBoxPage", pen.getMagicBoxPage() );
            addProperty( "ProtocolVersion", pen.getProtocolVersion() );
            addProperty( "Bateria", "" + ( ( int )pen.getPenData().getBatteryLevel() ) );
            addProperty( "Custom", "" + pen.getPenData().getCustomAllocationData() );
            addProperty( "Form ID", "" + pen.getPenData().getFormId() );
            addProperty( "Form Instance ID", "" + pen.getPenData().getFormInstanceId() );
            addProperty( "ID Caneta", "" + pen.getPenData().getPenSerial() );

            Iterator it = pen.getPageAddresses();
            GridProperties prop = new GridProperties();
            while ( it.hasNext() ) {
                prop.add( ( String )it.next() );
            }
            if ( prop.values.size() > 1 )
                prop.name = "Páginas";
            else
                prop.name = "Página";
            list.add( prop );
        }
        catch ( NoSuchPermissionException e ) {
            showErrorMessage( e.getMessage() );
        }
    }

    protected void addProperty( String name, Object value )
    {
        ListModelList list = ( ListModelList )gridProperties.getModel();

        GridProperties prop = new GridProperties();
        prop.name = name;
        prop.add( value );
        list.add( prop );
    }

    public void onClick$cmdDelete()
    {
        Listitem item = listboxRecord.getSelectedItem();
        if ( item != null ) {
            PGCDTO dto = ( PGCDTO )item.getValue();
            if ( dto != null ) {
                try {
                    getSession().delete( getLoggedInUser(), dto );
                    ListModelList list = ( ListModelList )listboxRecord.getModel();
                    if ( list != null ) {
                        list.remove( dto );
                    }
                }
                catch ( ApplicationException e ) {
                    showErrorMessage( e.getMessage() );
                }
            }
        }
    }

    public void onClick$cmdRefresh()
    {
        try {
            refresh();
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage() );
        }
    }


    protected void refresh() throws ApplicationException
    {
        List<PGCDTO> medias = getSession().getAllPgc( getLoggedInUser() );
        if ( SysUtils.isEmpty( medias ) )
            medias = new ArrayList<PGCDTO>();
        listboxRecord.setModel( new ListModelList( medias, true ) );
    }

    public void onClick$btnExport()
    {
        try {
            Listitem item = listboxRecord.getSelectedItem();

            if ( item != null && item.getValue() != null ) {
                PGCDTO pad = ( PGCDTO )item.getValue();
                byte[] obj;
                obj = getSession().getObject( pad.getMedia() );
                if ( obj != null ) {
                    Filedownload.save( obj, pad.getMedia().getMimeType(), pad.getMedia().getName() );
                }
            }
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage() );
        }
    }

}
