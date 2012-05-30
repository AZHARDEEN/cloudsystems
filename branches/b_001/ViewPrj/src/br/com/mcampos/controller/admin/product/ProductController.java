package br.com.mcampos.controller.admin.product;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.dto.product.ProductDTO;
import br.com.mcampos.ejb.cloudsystem.product.facade.ProductFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Textbox;


public class ProductController extends SimpleTableController<ProductDTO>
{
    private Listheader listHeaderName;
    private ProductFacade session;

    private Label labelEditName;
    private Textbox editName;
    private Label labelPrdName;
    private Label recordName;


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
        getSession().delete( getLoggedInUser(), ( ( ProductDTO )currentRecord ).getId() );
    }

    protected Object createNewRecord()
    {
        return new ProductDTO();
    }

    protected void persist( Object e ) throws ApplicationException
    {
        if ( isAddNewOperation() )
            getSession().add( getLoggedInUser(), ( ( ProductDTO )e ) );
        else
            getSession().update( getLoggedInUser(), ( ( ProductDTO )e ) );
    }

    @Override
    protected String getPageTitle()
    {
        return getLabel( "productPageTitle" );
    }

    private void setLabels()
    {
        setLabel( listHeaderName );
        setLabel( labelEditName );
        setLabel( labelPrdName );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        setLabels();
    }

    public ProductFacade getSession()
    {
        if ( session == null )
            session = ( ProductFacade )getRemoteSession( ProductFacade.class );
        return session;
    }

    @Override
    protected void clearRecordInfo()
    {
        super.clearRecordInfo();
        editName.setText( "" );
    }

    @Override
    protected SimpleTableDTO prepareToUpdate( Object currentRecord )
    {
        super.prepareToUpdate( currentRecord );
        if ( currentRecord != null ) {
            editName.setText( ( ( ProductDTO )currentRecord ).getName() );
        }
        return ( SimpleTableDTO )currentRecord;
    }

    @Override
    protected Object saveRecord( Object object )
    {
        super.saveRecord( object );
        ProductDTO dto = ( ProductDTO )object;
        dto.setName( editName.getValue() );
        return object;
    }

    @Override
    protected void showRecord( SimpleTableDTO record )
    {
        super.showRecord( record );
        if ( record == null )
            return;
        ProductDTO dto = ( ProductDTO )record;
        recordName.setValue( dto.getName() );
    }

    @Override
    protected void prepareToInsert()
    {
        super.prepareToInsert();
        editName.setFocus( true );
    }
}
