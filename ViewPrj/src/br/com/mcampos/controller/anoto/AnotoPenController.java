package br.com.mcampos.controller.anoto;


import br.com.mcampos.controller.admin.tables.BasicListController;
import br.com.mcampos.controller.anoto.model.PenIdComparator;
import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;


public class AnotoPenController extends BasicListController<PenDTO>
{
    protected Textbox editId;
    protected Label recordId;


    protected Textbox editDescription;
    protected Label recordDescription;

    private AnodeFacade session;

    protected Listheader headerPenId;

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

    @Override
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

    @Override
    protected Object createNewRecord()
    {
        return new PenDTO();
    }

    protected PenDTO copyTo( PenDTO dto )
    {
        dto.setId( editId.getValue() );
        dto.setDescription( editDescription.getValue().trim() );
        return dto;
    }

    @Override
    public void render( Listitem item, Object value )
    {
        PenDTO dto = ( PenDTO )value;

        if ( dto != null ) {
            item.setValue( value );
            item.getChildren().add( new Listcell( dto.getId() ) );
        }
    }

    @Override
    protected List getRecordList() throws ApplicationException
    {
        showRecord( null );
        return getSession().getPens( getLoggedInUser() );
    }

    @Override
    protected void delete( Object currentRecord ) throws ApplicationException
    {
        getSession().delete( getLoggedInUser(), ( PenDTO )currentRecord );
    }

    @Override
    protected Object saveRecord( Object record )
    {
        PenDTO dto = ( PenDTO )record;
        copyTo( dto );
        return dto;
    }

    @Override
    protected void clearRecordInfo()
    {
        editId.setValue( "" );
        editDescription.setValue( "" );
    }

    @Override
    protected void prepareToInsert()
    {
        clearRecordInfo();
        editId.setFocus( true );
        editId.setDisabled( false );
    }

    @Override
    protected Object prepareToUpdate( Object currentRecord )
    {
        PenDTO dto = ( PenDTO )currentRecord;

        editId.setValue( dto.getId() );
        editDescription.setValue( dto.getDescription() );
        editDescription.setFocus( true );
        editId.setDisabled( true );
        return dto;
    }

    @Override
    protected void persist( Object e ) throws ApplicationException
    {
        if ( isAddNewOperation() )
            getSession().add( getLoggedInUser(), ( PenDTO )e );
        else
            getSession().update( getLoggedInUser(), ( PenDTO )e );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        headerPenId.setSortAscending( new PenIdComparator( true ) );
        headerPenId.setSortDescending( new PenIdComparator( false ) );
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
