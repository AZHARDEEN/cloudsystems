package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.anoto.renderer.GridProperties;
import br.com.mcampos.controller.anoto.renderer.PgcListRendered;
import br.com.mcampos.controller.anoto.renderer.PropertyRowRenderer;
import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.controller.anoto.util.PgcFile;

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
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;


public class AnotoPcgController extends LoggedBaseController
{
    private AnodeFacade session;
    protected Button cmdCreate;
    protected org.zkoss.zul.Image imgTest;
    protected Listbox listboxRecord;
    protected Grid gridProperties;

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

        List<PGCDTO> medias = getSession().getAllPgc( getLoggedInUser() );

        listboxRecord.setItemRenderer( new PgcListRendered() );
        listboxRecord.setModel( new ListModelList( medias, true ) );
        gridProperties.setRowRenderer( new PropertyRowRenderer() );
        gridProperties.setModel( new ListModelList( new ArrayList<GridProperties>() ) );
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
            pgcFile.persist();
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage(), "Upload Media" );
        }
    }

    protected Pen getPenObject( byte[] pgc )
    {
        ByteArrayInputStream is = new ByteArrayInputStream( pgc );
        try {
            return PenHome.read( is );
        }
        catch ( PenCreationException e ) {
            showErrorMessage( e.getMessage(), "Load Pen Object" );
            return null;
        }
    }

    public void onSelect$listboxRecord()
    {
        PGCDTO dto = ( PGCDTO )listboxRecord.getSelectedItem().getValue();

        try {
            byte[] bytepgc = getSession().getObject( getLoggedInUser(), dto.getMedia() );
            Pen pen = getPenObject( bytepgc );
            showProperties( pen, dto );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "" );
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
            showErrorMessage( e.getMessage(), "Propriedades" );
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
}
