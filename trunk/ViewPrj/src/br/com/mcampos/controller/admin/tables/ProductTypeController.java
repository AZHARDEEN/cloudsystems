package br.com.mcampos.controller.admin.tables;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.product.ProductTypeDTO;
import br.com.mcampos.ejb.cloudsystem.product.type.facade.ProductTypeFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;


public class ProductTypeController extends SimpleTableController<ProductTypeDTO>
{
    private ProductTypeFacade session;
    private Label labelProductTypeTitle;


    public ProductTypeController()
    {
        super();
    }

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
        getSession().delete( getLoggedInUser(), ( ( ProductTypeDTO )currentRecord ).getId() );
    }

    protected Object createNewRecord()
    {
        return new ProductTypeDTO();
    }

    protected void persist( Object e ) throws ApplicationException
    {
        if ( isAddNewOperation() )
            getSession().add( getLoggedInUser(), ( ProductTypeDTO )e );
        else
            getSession().update( getLoggedInUser(), ( ProductTypeDTO )e );
    }


    public ProductTypeFacade getSession()
    {
        if ( session == null )
            session = ( ProductTypeFacade )getRemoteSession( ProductTypeFacade.class );
        return session;
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        setLabel( labelProductTypeTitle );
    }
}
