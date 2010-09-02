package br.com.mcampos.controller.accounting;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.accounting.AccountingEventDTO;
import br.com.mcampos.dto.accounting.AccountingMaskDTO;
import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.ejb.cloudsystem.account.event.facade.AccountingEventFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;


public class AccountingEventController extends SimpleTableController<AccountingEventDTO>
{
    private AccountingEventFacade session;
    private Combobox cmbMask;
    private Label labelHistory;
    private Label labelEditHistory;
    private Textbox editHistory;
    private Label recordHistory;

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
        setLabels();
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

}
