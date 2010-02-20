package br.com.mcampos.controller.anode;

import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.anode.FormDTO;
import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;

import br.com.mcampos.exception.ApplicationException;

import br.com.mcampos.sysutils.SysUtils;

import java.util.List;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

public class AnodeFormController extends SimpleTableController<FormDTO>
{
    private AnodeFacade session;
    protected Textbox editIP;
    protected Label recordIP;
    Button btnAddAttach;
    Button btnRemoveAttach;

    public AnodeFormController()
    {
        super();
    }

    protected AnodeFacade getSession()
    {
        if ( session == null )
            session = ( AnodeFacade )getRemoteSession( AnodeFacade.class );
        return session;
    }

    protected Integer getNextId()
    {
        try {
            return session.nextFormId( getLoggedInUser() );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Próximo id" );
            return 0;
        }
    }

    protected SimpleTableDTO createDTO()
    {
        return new FormDTO();
    }

    protected void delete( Listitem currentRecord ) throws ApplicationException
    {
        FormDTO dto = getValue( currentRecord );

        getSession().delete( getLoggedInUser(), dto.getId() );
    }

    protected void insertItem( Listitem e ) throws ApplicationException
    {
        getSession().add( getLoggedInUser(), getValue( e ) );
    }

    protected void updateItem( Listitem e ) throws ApplicationException
    {
        getSession().update( getLoggedInUser(), getValue( e ) );
    }


    @Override
    protected FormDTO getValue( Listitem selecteItem )
    {
        return ( FormDTO )super.getValue( selecteItem );
    }

    protected List getRecordList() throws ApplicationException
    {
        List list = super.getRecordList();
        btnAddAttach.setDisabled( true );
        btnRemoveAttach.setDisabled( true );
        if ( SysUtils.isEmpty( list ) )
            list = getSession().getAll( getLoggedInUser() );
        return list;
    }

    @Override
    protected void prepareToInsert()
    {
        super.prepareToInsert();
        editIP.setValue( "" );
        editIP.setFocus( true );
        btnRemoveAttach.setDisabled( true );
    }

    @Override
    protected FormDTO prepareToUpdate( Listitem currentRecord )
    {
        FormDTO dto = ( FormDTO )super.prepareToUpdate( currentRecord );
        if ( dto != null )
            editIP.setValue( dto.getIp() );
        editIP.setFocus( true );
        return dto;
    }

    public void onUpload$btnUpload( UploadEvent evt )
    {
        Media media;
        String contentType;


        media = evt.getMedia();
        if ( media == null )
            return;
        if ( media.isBinary() ) {
            byte[] data = media.getByteData();

            if ( media.getFormat() == "jpeg" )
                data = null;
        }
        contentType = media.getContentType();
        evt.stopPropagation();
    }

    @Override
    protected SimpleTableDTO copyTo( SimpleTableDTO dto )
    {
        FormDTO d = ( FormDTO )super.copyTo( dto );
        if ( d != null )
            d.setIp( editIP.getValue() );
        return d;
    }

    @Override
    protected void configure( Listitem item )
    {
        if ( item == null )
            return;
        FormDTO dto = getValue( item );

        if ( dto != null ) {
            item.getChildren().add( new Listcell( dto.getIp() ) );
            item.getChildren().add( new Listcell( dto.getDescription() ) );
        }
    }

    @Override
    protected void showRecord( SimpleTableDTO record )
    {
        super.showRecord( record );
        recordIP.setValue( record != null ? ( ( FormDTO )record ).getIp() : "" );
        btnAddAttach.setDisabled( record == null );
    }
}
