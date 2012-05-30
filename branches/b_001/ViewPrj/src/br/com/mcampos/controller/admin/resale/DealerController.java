package br.com.mcampos.controller.admin.resale;


import br.com.mcampos.controller.admin.tables.BasicListController;
import br.com.mcampos.controller.user.client.ClientListRenderer;
import br.com.mcampos.dto.resale.DealerDTO;
import br.com.mcampos.dto.resale.DealerTypeDTO;
import br.com.mcampos.dto.resale.ResaleDTO;
import br.com.mcampos.dto.user.ClientDTO;
import br.com.mcampos.ejb.cloudsystem.resale.dealer.facade.DealerFacade;
import br.com.mcampos.exception.ApplicationException;

import java.security.InvalidParameterException;

import java.util.Collections;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.ListitemRenderer;


public class DealerController extends BasicListController<DealerDTO>
{
    private DealerFacade session;
    private Listheader listHeaderType;

    private Label labelName;
    private Label labelType;
    private Label labelEditType;
    private Label labelEditName;

    private Label recordName;
    private Label recordType;
    private Bandbox editName;
    private Combobox cmbType;
    private Combobox cmbResale;
    private Listbox listClientPerson;


    public DealerController( char c )
    {
        super( c );
    }

    public DealerController()
    {
        super();
    }

    protected void showRecord( DealerDTO record )
    {
        recordType.setValue( record.getType().toString() );
        recordName.setValue( record.getPerson().toString() );
    }

    protected void clearRecordInfo()
    {
        recordType.setValue( "" );
        recordName.setValue( "" );
    }

    protected List getRecordList() throws ApplicationException
    {
        return Collections.emptyList();
    }

    protected ListitemRenderer getRenderer()
    {
        return new DealerListRenderer();
    }

    protected void delete( Object currentRecord ) throws ApplicationException
    {
        getSession().delete( getLoggedInUser(), ( ResaleDTO )cmbResale.getSelectedItem().getValue(),
                             ( ( DealerDTO )currentRecord ).getSequence() );
    }

    protected Object saveRecord( Object currentRecord )
    {
        DealerDTO dto = ( DealerDTO )currentRecord;
        if ( dto.getPerson() == null ) {
            ClientDTO client = getSelectedClient();
            if ( client == null )
                throw new InvalidParameterException( "Client inválido. Selecione uma pessoa" );
            dto.setPerson( client.getClient() );
        }
        dto.setType( ( DealerTypeDTO )cmbType.getSelectedItem().getValue() );
        return dto;
    }

    protected void prepareToInsert()
    {
        editName.setValue( "" );
        cmbType.setSelectedIndex( 0 );
        listClientPerson.clearSelection();
    }

    protected Object prepareToUpdate( Object currentRecord )
    {
        DealerDTO dto = ( DealerDTO )currentRecord;
        editName.setValue( dto.getPerson().toString() );
        findType( dto.getType() );
        listClientPerson.clearSelection();
        return currentRecord;
    }

    private void findType( DealerTypeDTO type )
    {
        int nIndex;

        for ( nIndex = 0; nIndex < cmbType.getItemCount(); nIndex++ ) {
            DealerTypeDTO item = ( DealerTypeDTO )cmbType.getItemAtIndex( nIndex ).getValue();
            if ( item.equals( type ) ) {
                cmbType.setSelectedIndex( nIndex );
                break;
            }
        }
    }

    protected Object createNewRecord()
    {
        DealerDTO dto = new DealerDTO();
        dto.setResale( ( ResaleDTO )cmbResale.getSelectedItem().getValue() );
        return dto;
    }

    protected void persist( Object e ) throws ApplicationException
    {
        if ( isAddNewOperation() ) {
            getSession().add( getLoggedInUser(), ( DealerDTO )e );
        }
        else
            getSession().update( getLoggedInUser(), ( DealerDTO )e );
    }

    public DealerFacade getSession()
    {
        if ( session == null )
            session = ( DealerFacade )getRemoteSession( DealerFacade.class );
        return session;
    }

    @Override
    protected String getPageTitle()
    {
        return getLabel( "dealerTitle" );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        listClientPerson.setItemRenderer( new ClientListRenderer() );
        setLabel( listHeaderType );
        setLabel( labelName );
        labelType.setValue( listHeaderType.getLabel() );
        labelEditType.setValue( labelType.getValue() );
        setLabel( labelEditName );
        loadCombobox( cmbType, getSession().getTypes() );
        loadCombobox( cmbResale, getSession().getResales( getLoggedInUser() ) );
        if ( cmbResale.getItemCount() > 0 ) {
            cmbResale.setSelectedIndex( 0 );
            loadDealers();
        }
        loadClients();
    }


    public void onSelect$cmbResale()
    {
        loadDealers();
    }

    private void loadDealers()
    {
        List<DealerDTO> dealers;
        try {
            dealers = getSession().getAll( getLoggedInUser(), ( ResaleDTO )cmbResale.getSelectedItem().getValue() );
            getListboxRecord().setModel( new ListModelList( dealers, true ) );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage() );
        }
    }


    private void loadClients()
    {
        try {
            ListModelList model = ( ListModelList )listClientPerson.getModel();
            if ( model == null ) {
                model = new ListModelList();
                listClientPerson.setModel( model );
            }
            model.clear();
            model.addAll( getSession().getPeople( getLoggedInUser() ) );
        }
        catch ( Exception e ) {
            showErrorMessage( e.getMessage() );
        }
    }

    public void onSelect$listClientPerson()
    {
        ClientDTO dto = getSelectedClient();
        editName.setValue( dto.getClient().toString() );
        editName.setOpen( false );
    }

    private ClientDTO getSelectedClient()
    {
        if ( listClientPerson.getSelectedItem() == null )
            return null;
        return ( ClientDTO )listClientPerson.getSelectedItem().getValue();
    }
}
