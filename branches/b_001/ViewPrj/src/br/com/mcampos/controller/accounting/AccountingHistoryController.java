package br.com.mcampos.controller.accounting;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.accounting.AccountingHistoryDTO;
import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.ejb.cloudsystem.account.history.facade.AccountingHistoryFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;


public class AccountingHistoryController extends SimpleTableController<AccountingHistoryDTO>
{
    private AccountingHistoryFacade session;
    private Label labelHistory;
    private Label labelEditHistory;

    private Textbox editHistory;
    private Label recordHistory;


    protected Integer getNextId() throws ApplicationException
    {
        return getSession().nextId( getLoggedInUser() );
    }

    protected List getRecordList() throws ApplicationException
    {
        return getSession().getAll( getLoggedInUser() );
    }

    protected void delete( Object currentRecord ) throws ApplicationException
    {
        AccountingHistoryDTO dto = ( AccountingHistoryDTO )currentRecord;
        getSession().delete( getLoggedInUser(), dto.getId() );
    }

    protected Object createNewRecord()
    {
        return new AccountingHistoryDTO();
    }

    protected void persist( Object e ) throws ApplicationException
    {
        if ( isAddNewOperation() ) {
            getSession().add( getLoggedInUser(), ( AccountingHistoryDTO )e );
        }
        else
            getSession().update( getLoggedInUser(), ( AccountingHistoryDTO )e );
    }


    public AccountingHistoryFacade getSession()
    {
        if ( session == null )
            session = ( AccountingHistoryFacade )getRemoteSession( AccountingHistoryFacade.class );
        return session;
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        setLabel( labelHistory );
        labelEditHistory.setValue( labelHistory.getValue() );
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
        AccountingHistoryDTO dto = ( AccountingHistoryDTO )currentRecord;
        editHistory.setValue( dto.getHistory() );
        return super.prepareToUpdate( currentRecord );
    }

    @Override
    protected void showRecord( SimpleTableDTO record )
    {
        super.showRecord( record );
        AccountingHistoryDTO dto = ( AccountingHistoryDTO )record;
        recordHistory.setValue( dto.getHistory() );

    }

    @Override
    protected Object saveRecord( Object object )
    {
        AccountingHistoryDTO dto = ( AccountingHistoryDTO )object;
        dto.setHistory( editHistory.getValue() );
        return super.saveRecord( object );
    }

    @Override
    protected String getPageTitle()
    {
        return getLabel( "accountingHistoryPageTitle" );
    }
}
