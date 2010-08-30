package br.com.mcampos.controller.accounting;


import br.com.mcampos.controller.admin.tables.BasicListController;
import br.com.mcampos.dto.accounting.AccountingMaskDTO;
import br.com.mcampos.ejb.cloudsystem.account.mask.facade.AccountingMaskFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.api.Intbox;
import org.zkoss.zul.api.Listheader;
import org.zkoss.zul.api.Textbox;


public class MaskController extends BasicListController<AccountingMaskDTO>
{
    private Label labelCode;
    private Label labelDescription;
    private Label labelMask;

    private Label labelEditCode;
    private Label labelEditDescription;
    private Label labelEditMask;

    private Textbox editMask;
    private Textbox editDescription;
    private Intbox editCode;

    private Label recordCode;
    private Label recordDescription;
    private Label recordMask;

    private Listheader headerCode;
    private Listheader headerDescription;
    private Listheader headerMask;


    private AccountingMaskFacade session;

    public MaskController( char c )
    {
        super( c );
    }

    public MaskController()
    {
        super();
    }

    private void setLabels()
    {
        setLabel( labelEditMask );
        setLabel( labelEditCode );
        setLabel( labelEditDescription );

        labelCode.setValue( labelEditCode.getValue() );
        labelDescription.setValue( labelEditDescription.getValue() );
        labelMask.setValue( labelEditMask.getValue() );

        headerCode.setLabel( labelEditCode.getValue() );
        headerDescription.setLabel( labelEditDescription.getValue() );
        headerMask.setLabel( labelEditMask.getValue() );

    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        setLabels();
    }

    @Override
    protected String getPageTitle()
    {
        return getLabel( "accountingMaskTitle" );
    }

    public AccountingMaskFacade getSession()
    {
        if ( session == null )
            session = ( AccountingMaskFacade )getRemoteSession( AccountingMaskFacade.class );
        return session;
    }

    protected void showRecord( AccountingMaskDTO record )
    {
        recordMask.setValue( record.getMask() );
        recordCode.setValue( record.getId().toString() );
        recordDescription.setValue( record.getDescription() );
    }

    protected void clearRecordInfo()
    {
        editMask.setValue( "" );
        editCode.setValue( 0 );
        editDescription.setValue( "" );
    }

    protected List getRecordList() throws ApplicationException
    {
        return getSession().getAll( getLoggedInUser() );
    }

    protected ListitemRenderer getRenderer()
    {
        return new AccountingMaskListRenderer();
    }

    protected void delete( Object currentRecord ) throws ApplicationException
    {
        getSession().delete( getLoggedInUser(), ( ( AccountingMaskDTO )currentRecord ).getId() );
    }

    protected Object saveRecord( Object currentRecord )
    {
        AccountingMaskDTO dto = ( ( AccountingMaskDTO )currentRecord );
        dto.setId( editCode.getValue() );
        dto.setDescription( editDescription.getValue() );
        dto.setMask( editMask.getValue() );
        return dto;
    }

    protected void prepareToInsert()
    {
        editMask.setValue( "" );
        editDescription.setValue( "" );
        editCode.setDisabled( false );
        try {
            editCode.setValue( getSession().nextId( getLoggedInUser() ) );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage() );
        }
    }

    protected Object prepareToUpdate( Object currentRecord )
    {
        AccountingMaskDTO dto = ( ( AccountingMaskDTO )currentRecord );
        editMask.setValue( dto.getMask() );
        editCode.setValue( dto.getId() );
        editDescription.setValue( dto.getDescription() );
        editCode.setDisabled( true );
        return currentRecord;
    }

    protected Object createNewRecord()
    {
        return new AccountingMaskDTO();
    }

    protected void persist( Object e ) throws ApplicationException
    {
        if ( isAddNewOperation() )
            getSession().add( getLoggedInUser(), ( ( AccountingMaskDTO )e ) );
        else
            getSession().update( getLoggedInUser(), ( ( AccountingMaskDTO )e ) );
    }
}
