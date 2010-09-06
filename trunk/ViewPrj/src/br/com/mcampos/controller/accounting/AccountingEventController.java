package br.com.mcampos.controller.accounting;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.accounting.AccountingEventDTO;
import br.com.mcampos.dto.accounting.AccountingMaskDTO;
import br.com.mcampos.dto.accounting.AccountingRateTypeDTO;
import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.ejb.cloudsystem.account.event.facade.AccountingEventFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;


public class AccountingEventController extends SimpleTableController<AccountingEventDTO>
{
    private AccountingEventFacade session;
    private Combobox cmbMask;
    private Combobox cmbNature;
    private Combobox cmbType;
    private Label labelHistory;
    private Label labelEditHistory;
    private Textbox editHistory;
    private Label recordHistory;

    private Toolbar barComposition;

    private Listbox listComposition;

    private Label labelAccountingNumber;
    private Label labelNature;

    private Button addAccNumber;
    private Button updateAccNumber;
    private Button removeAccNumber;

    private Doublebox editRate;

    private Bandbox editAccountingNumber;
    private Listbox listAccountNumber;

    private Listheader headerAccountingNumber;
    private Listheader headerDescription;


    public AccountingEventController()
    {
        super();
    }

    protected Integer getNextId() throws ApplicationException
    {
        AccountingMaskDTO mask = getMask();
        if ( mask != null )
            return getSession().nextId( getLoggedInUser(), getMask().getId() );
        else
            return 1;
    }

    protected List getRecordList() throws ApplicationException
    {
        AccountingMaskDTO mask = getMask();
        if ( mask != null )
            return getSession().getAll( getLoggedInUser(), mask.getId() );
        else
            return Collections.emptyList();
    }

    protected void delete( Object currentRecord ) throws ApplicationException
    {
        AccountingMaskDTO mask = getMask();
        if ( mask != null )
            getSession().delete( getLoggedInUser(), mask.getId(), ( ( AccountingEventDTO )currentRecord ).getId() );
    }

    protected Object createNewRecord()
    {
        return new AccountingEventDTO();
    }

    protected void persist( Object e ) throws ApplicationException
    {
        AccountingMaskDTO mask = getMask();
        if ( mask != null ) {
            if ( isAddNewOperation() )
                getSession().add( getLoggedInUser(), ( ( AccountingEventDTO )e ) );
            else
                getSession().update( getLoggedInUser(), ( ( AccountingEventDTO )e ) );
        }
    }

    public AccountingEventFacade getSession()
    {
        if ( session == null )
            session = ( AccountingEventFacade )getRemoteSession( AccountingEventFacade.class );
        return session;
    }

    @Override
    protected String getPageTitle()
    {
        return getLabel( "accountingEventTitle" );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        loadCombobox( cmbMask, getSession().getMasks( getLoggedInUser() ) );
        if ( cmbMask.getItemCount() > 0 ) {
            cmbMask.setSelectedIndex( 0 );
            refresh();
        }
        loadCombobox( cmbNature, getSession().getNatures( getLoggedInUser() ) );
        if ( cmbNature.getItemCount() > 0 )
            cmbNature.setSelectedIndex( 0 );
        loadCombobox( cmbType, getSession().getRateTypes() );
        if ( cmbType.getItemCount() > 0 ) {
            cmbType.setSelectedIndex( 0 );
            setRateFormat();
        }
        setLabels();
        listAccountNumber.setItemRenderer( new AccountingPlanListRenderer() );
    }

    private void setRateFormat()
    {
        AccountingRateTypeDTO type = ( AccountingRateTypeDTO )cmbType.getSelectedItem().getValue();
        if ( type != null ) {
            if ( type.getId().equals( 1 ) )
                editRate.setFormat( "0.0000" );
            else
                editRate.setFormat( "##,##0.00" );
        }
    }

    public void onSelect$cmbType()
    {
        setRateFormat();
    }

    private AccountingMaskDTO getMask()
    {
        if ( cmbMask.getSelectedItem() == null )
            return null;
        return ( AccountingMaskDTO )cmbMask.getSelectedItem().getValue();
    }

    private void setLabels()
    {
        setLabel( labelHistory );
        labelEditHistory.setValue( labelHistory.getValue() );
        labelAccountingNumber.setValue( "Teste" );
        setLabel( labelAccountingNumber );
        setLabel( labelNature );

        setLabel( addAccNumber );
        setLabel( updateAccNumber );
        setLabel( removeAccNumber );
        setLabel( headerAccountingNumber );
        setLabel( headerDescription );

    }

    @Override
    protected void clearRecordInfo()
    {
        super.clearRecordInfo();
        editHistory.setValue( "" );
    }

    @Override
    protected SimpleTableDTO prepareToUpdate( Object currentRecord )
    {
        AccountingEventDTO dto = ( AccountingEventDTO )currentRecord;
        editHistory.setValue( dto.getHistory() );
        return super.prepareToUpdate( currentRecord );
    }

    @Override
    protected void showRecord( SimpleTableDTO record )
    {
        super.showRecord( record );
        AccountingEventDTO dto = ( AccountingEventDTO )record;
        recordHistory.setValue( dto.getHistory() );

    }

    @Override
    protected Object saveRecord( Object object )
    {
        AccountingMaskDTO mask = getMask();
        if ( mask != null ) {
            AccountingEventDTO dto = ( AccountingEventDTO )object;
            dto.setHistory( editHistory.getValue() );
            dto.setMask( getMask() );
            return super.saveRecord( object );
        }
        else
            return null;
    }

    private void setBarVisible( boolean bVisible )
    {
        if ( barComposition.isVisible() != bVisible )
            barComposition.setVisible( bVisible );

    }

    @Override
    protected void showEditPanel( Boolean bShow )
    {
        setBarVisible( bShow );
        super.showEditPanel( bShow );
    }

    public void onClick$addAccNumber()
    {
    }

    public void onClick$updateAccNumber()
    {
        Set selected = listComposition.getSelectedItems();
        if ( selected.isEmpty() ) {
            showErrorMessage( "noCurrentRecordMessage" );
            return;
        }

    }

    public void onClick$removeAccNumber()
    {
        Set selected = listComposition.getSelectedItems();
        if ( selected.isEmpty() ) {
            showErrorMessage( "noCurrentRecordMessage" );
            return;
        }

    }

    @Override
    protected void refresh()
    {
        AccountingMaskDTO mask = getMask();
        if ( mask != null ) {
            super.refresh();
            try {
                listAccountNumber.setModel( new ListModelList( getSession().getAccountNumbers( getLoggedInUser(),
                                                                                               mask.getId() ) ) );
            }
            catch ( Exception e ) {
                showErrorMessage( e.getMessage() );
            }
        }
    }
}
