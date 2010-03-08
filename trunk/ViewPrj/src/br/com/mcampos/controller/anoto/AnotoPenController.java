package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.admin.tables.BasicListController;
import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;


public class AnotoPenController extends BasicListController<PenDTO>
{
    protected Textbox editId;
    protected Label recordId;


    protected Textbox editDescription;
    protected Label recordDescription;

    private AnodeFacade session;

    public AnotoPenController()
    {
        super();
    }

    protected AnodeFacade getSession()
    {
        if ( session == null )
            session = ( AnodeFacade )getRemoteSession( AnodeFacade.class );
        return session;
    }

    protected void showRecord( PenDTO record )
    {
        if ( record != null ) {
            recordId.setValue( record.getId() );
            recordDescription.setValue( record.getDescription() );
            //listAvailable.setModel( getAvailableFormsListModel( record ) );
            //listAdded.setModel( getFormModel( record ) );
        }
        else {
            recordId.setValue( "" );
            recordDescription.setValue( "" );
        }
    }

    protected PenDTO createDTO()
    {
        return new PenDTO();
    }

    protected PenDTO copyTo( PenDTO dto )
    {
        dto.setId( editId.getValue() );
        dto.setDescription( editDescription.getValue().trim() );
        return dto;
    }

    protected void configure( Listitem item )
    {
        if ( item == null )
            return;
        PenDTO dto = getValue( item );

        if ( dto != null ) {
            item.getChildren().add( new Listcell( dto.getId() ) );
        }
    }

    protected List getRecordList() throws ApplicationException
    {
        showRecord( null );
        return getSession().getPens( getLoggedInUser() );
    }

    protected void delete( Listitem currentRecord ) throws ApplicationException
    {
        getSession().delete( getLoggedInUser(), getValue( currentRecord ) );
    }

    protected Listitem saveRecord( Listitem getCurrentRecord )
    {
        return getCurrentRecord;
    }

    protected void prepareToInsert()
    {
        editId.setValue( "" );
        editDescription.setValue( "" );
        editId.setFocus( true );
        editId.setDisabled( false );
    }

    protected Object prepareToUpdate( Listitem currentRecord )
    {
        PenDTO dto = null;

        dto = getValue( currentRecord );

        editId.setValue( dto.getId() );
        editDescription.setValue( dto.getDescription() );
        editDescription.setFocus( true );
        editId.setDisabled( true );
        return dto;
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
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        /*We do not need update by now*/
        //cmdUpdate.setVisible( false );
    }
    /*
    protected void moveListitem( Listbox toListbox, Listbox fromListbox )
    {
        if ( getListboxRecord().getSelectedCount() != 1 )
            return;
        PenDTO currentPen = getValue( getListboxRecord().getSelectedItem() );
        Set selected = fromListbox.getSelectedItems();
        if ( selected.isEmpty() )
            return;
        List al = new ArrayList( selected );
        ArrayList<FormDTO> forms = new ArrayList<FormDTO>( al.size() );
        try {
            for ( Iterator it = al.iterator(); it.hasNext(); ) {
                Listitem li = ( Listitem )it.next();
                forms.add( ( FormDTO )li.getValue() );
            }
            if ( toListbox.equals( listAvailable ) )
                getSession().removeFromPen( getLoggedInUser(), currentPen, forms );
            else
                getSession().insertIntoPen( getLoggedInUser(), currentPen, forms );


            for ( Iterator it = al.iterator(); it.hasNext(); ) {
                Listitem li = ( Listitem )it.next();
                li.setSelected( false );
                toListbox.appendChild( li );
            }
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage(), "Formularios Associados" );
        }
    }

    public void onDrop( DropEvent evt )
    {
        if ( getListboxRecord().getSelectedCount() != 1 )
            return;
        if ( evt == null )
            return;
        Component dragged = evt.getDragged();
        Component target = evt.getTarget();
        if ( dragged == null || !( dragged instanceof Listitem ) || target == null || !( target instanceof Listbox ) )
            return;
        ( ( Listitem )dragged ).setSelected( true );
        if ( target.equals( listAvailable ) ) {
            //we are removing forms from this pen
            moveListitem( listAvailable, listAdded );
        }
        else {
            // we are adding forms into this pen
            moveListitem( listAdded, listAvailable );
        }
    }
    */
}
