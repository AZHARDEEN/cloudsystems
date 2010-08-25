package br.com.mcampos.controller.accounting;


import br.com.mcampos.controller.admin.tables.BasicListController;
import br.com.mcampos.dto.accounting.AccountingPlanDTO;
import br.com.mcampos.ejb.cloudsystem.account.plan.facade.AccountingPlanFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import org.zkoss.zk.ui.Component;
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

    private Textbox editCode;
    private Textbox editDescription;
    private Textbox editShortCode;

    private AccountingPlanFacade session;


    @Override
    protected String getPageTitle()
    {
        return getLabel( "accountingPageTitle" );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        setLabels();
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
    }

    protected void clearRecordInfo()
    {
        recordCode.setValue( "" );
        recordDescription.setValue( "" );
        recordShortCode.setValue( "" );
    }

    protected List getRecordList() throws ApplicationException
    {
        return getSession().getAll( getLoggedInUser() );
    }

    protected ListitemRenderer getRenderer()
    {
        return new AccountingPlanListRenderer();
    }

    protected void delete( Object currentRecord ) throws ApplicationException
    {
        getSession().delete( getLoggedInUser(), ( ( AccountingPlanDTO )currentRecord ).getNumber() );
    }

    protected Object saveRecord( Object currentRecord )
    {
        AccountingPlanDTO dto = ( AccountingPlanDTO )currentRecord;
        dto.setNumber( editCode.getValue() );
        dto.setDescription( editDescription.getValue() );
        dto.setShortNumber( editShortCode.getValue() );
        return currentRecord;
    }

    protected void prepareToInsert()
    {
        editCode.setValue( "" );
        editDescription.setValue( "" );
        editShortCode.setValue( "" );
    }

    protected Object prepareToUpdate( Object currentRecord )
    {
        AccountingPlanDTO dto = ( AccountingPlanDTO )currentRecord;
        editCode.setValue( "" );
        editDescription.setValue( "" );
        editShortCode.setValue( "" );

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
