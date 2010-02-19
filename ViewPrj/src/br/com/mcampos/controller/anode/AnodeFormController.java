package br.com.mcampos.controller.anode;

import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.controller.admin.tables.core.SimpleTableLocator;
import br.com.mcampos.controller.core.BaseController;
import br.com.mcampos.dto.anode.FormDTO;
import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;

import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.List;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

public class AnodeFormController extends SimpleTableController<FormDTO>
{
    private AnodeFacade session;
    protected Textbox editIP;

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
        return 0;
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
        return getSession().getAll( getLoggedInUser() );
    }

    @Override
    protected void prepareToInsert()
    {
        super.prepareToInsert();
        editIP.setValue( "" );
    }

    @Override
    protected FormDTO prepareToUpdate( Listitem currentRecord )
    {
        FormDTO dto = ( FormDTO )super.prepareToUpdate( currentRecord );
        if ( dto != null )
            editIP.setValue( dto.getIp() );
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
}
