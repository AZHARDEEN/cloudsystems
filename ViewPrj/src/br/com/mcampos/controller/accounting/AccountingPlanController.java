package br.com.mcampos.controller.accounting;


import br.com.mcampos.controller.admin.tables.BasicListController;
import br.com.mcampos.dto.accounting.AccountingMaskDTO;
import br.com.mcampos.dto.accounting.AccountingNatureDTO;
import br.com.mcampos.dto.accounting.AccountingPlanDTO;
import br.com.mcampos.ejb.cloudsystem.account.plan.facade.AccountingPlanFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;


public class AccountingPlanController extends BasicListController<AccountingPlanDTO>
{
    private Listheader headerAccountingNumber;
    private Listheader headerDescription;
    private Listheader headerAccountingShortNumber;

    private Label labelEditAccountingNumber;
    private Label labelDescription;
    private Label labelShortCode;
    private Label labelAccountingNumber;
    private Label labelEditDescription;
    private Label labelEditShortCode;

    private Label recordCode;
    private Label recordDescription;
    private Label recordShortCode;
    private Label recordNature;

    private Textbox editCode;
    private Textbox editDescription;
    private Textbox editShortCode;

    private AccountingPlanFacade session;

    private Combobox cmbMask;
    private Combobox cmbNature;


    @Override
    protected String getPageTitle()
    {
        return getLabel( "accountingPageTitle" );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        loadCombobox( cmbMask, getSession().getMasks( getLoggedInUser() ) );
        loadCombobox( cmbNature, getSession().getNatures( getLoggedInUser() ) );
        if ( cmbMask.getItemCount() > 0 ) {
            cmbMask.setSelectedIndex( 0 );
            refresh();
        }
        if ( cmbNature.getItemCount() > 0 )
            cmbNature.setSelectedIndex( 0 );
        setLabels();
    }

    private AccountingMaskDTO getMask()
    {
        if ( cmbMask.getSelectedItem() == null )
            return null;
        return ( AccountingMaskDTO )cmbMask.getSelectedItem().getValue();
    }

    public void onSelect$cmbMask()
    {
        refresh();
    }

    private void setLabels()
    {
        setLabel( headerAccountingNumber );
        setLabel( headerDescription );
        setLabel( headerAccountingShortNumber );

        setLabel( labelAccountingNumber );
        setLabel( labelDescription );
        setLabel( labelShortCode );

        labelEditAccountingNumber.setValue( labelAccountingNumber.getValue() );
        labelEditDescription.setValue( labelDescription.getValue() );
        labelEditShortCode.setValue( labelShortCode.getValue() );
    }


    private AccountingPlanFacade getSession()
    {
        if ( session == null )
            session = ( AccountingPlanFacade )getRemoteSession( AccountingPlanFacade.class );
        return session;
    }

    protected void showRecord( AccountingPlanDTO record )
    {
        recordCode.setValue( record.getNumber() );
        recordDescription.setValue( record.getDescription() );
        recordShortCode.setValue( record.getShortNumber() );
        recordNature.setValue( record.getNature().toString() );
    }

    protected void clearRecordInfo()
    {
        recordCode.setValue( "" );
        recordDescription.setValue( "" );
        recordShortCode.setValue( "" );
        recordNature.setValue( "" );
    }

    protected List getRecordList() throws ApplicationException
    {
        AccountingMaskDTO mask = getMask();
        if ( mask == null )
            return Collections.emptyList();
        return getSession().getAll( getLoggedInUser(), mask.getId() );
    }

    protected ListitemRenderer getRenderer()
    {
        return new AccountingPlanListRenderer();
    }

    protected void delete( Object currentRecord ) throws ApplicationException
    {
        AccountingMaskDTO mask = getMask();
        if ( mask == null )
            return;
        getSession().delete( getLoggedInUser(), mask.getId(), ( ( AccountingPlanDTO )currentRecord ).getNumber() );
    }

    protected Object saveRecord( Object currentRecord )
    {
        AccountingPlanDTO dto = ( AccountingPlanDTO )currentRecord;
        dto.setNumber( editCode.getValue() );
        dto.setDescription( editDescription.getValue() );
        dto.setShortNumber( editShortCode.getValue() );
        dto.setMask( getMask() );
        dto.setNature( ( AccountingNatureDTO )cmbNature.getSelectedItem().getValue() );
        return currentRecord;
    }

    protected void prepareToInsert()
    {
        editCode.setValue( "" );
        editDescription.setValue( "" );
        editShortCode.setValue( "" );
        cmbNature.setSelectedIndex( 0 );
    }

    protected Object prepareToUpdate( Object currentRecord )
    {
        AccountingPlanDTO dto = ( AccountingPlanDTO )currentRecord;
        editCode.setValue( dto.getNumber() );
        editDescription.setValue( dto.getDescription() );
        editShortCode.setValue( dto.getShortNumber() );
        for ( int index = 0; index < cmbNature.getItemCount(); index++ ) {
            if ( dto.getNature().equals( cmbNature.getItemAtIndex( index ).getValue() ) ) {
                cmbNature.setSelectedIndex( index );
                break;
            }
        }
        return currentRecord;
    }

    protected Object createNewRecord()
    {
        return new AccountingPlanDTO();
    }

    protected void persist( Object e ) throws ApplicationException
    {
        if ( isAddNewOperation() )
            getSession().add( getLoggedInUser(), ( ( AccountingPlanDTO )e ) );
        else
            getSession().update( getLoggedInUser(), ( ( AccountingPlanDTO )e ) );
    }
}
