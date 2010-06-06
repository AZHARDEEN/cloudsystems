package br.com.mcampos.controller.admin.tables;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.user.attributes.CompanyTypeDTO;
import br.com.mcampos.ejb.cloudsystem.user.attribute.companytype.CompanyTypeFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

public class CompanyTypeController extends SimpleTableController<CompanyTypeDTO>
{
    private CompanyTypeFacade session;

    protected Integer getNextId() throws ApplicationException
    {
        return getSession().nextId( getLoggedInUser() );
    }

    protected List getRecordList() throws ApplicationException
    {
        List list = getSession().getAll();

        return list;
    }

    protected void delete( Object currentRecord ) throws ApplicationException
    {
        if ( currentRecord != null ) {
            CompanyTypeDTO dto = ( CompanyTypeDTO )currentRecord;
            getSession().delete( getLoggedInUser(), dto.getId() );
        }
    }

    protected Object createNewRecord()
    {
        return new CompanyTypeDTO();
    }

    protected void persist( Object e ) throws ApplicationException
    {
        if ( isAddNewOperation() ) {
            getSession().add( getLoggedInUser(), ( CompanyTypeDTO )e );
        }
        else
            getSession().update( getLoggedInUser(), ( CompanyTypeDTO )e );
    }

    public CompanyTypeFacade getSession()
    {
        if ( session == null )
            session = ( CompanyTypeFacade )getRemoteSession( CompanyTypeFacade.class );
        return session;
    }
}
