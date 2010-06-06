package br.com.mcampos.controller.admin.tables;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.user.attributes.CompanyPositionDTO;
import br.com.mcampos.ejb.cloudsystem.user.attribute.companyposition.facade.CompanyPositionFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

public class CompanyPositionController extends SimpleTableController<CompanyPositionDTO>
{
    private CompanyPositionFacade session;

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
            CompanyPositionDTO dto = ( CompanyPositionDTO )currentRecord;
            getSession().delete( getLoggedInUser(), dto.getId() );
        }
    }

    protected Object createNewRecord()
    {
        return new CompanyPositionDTO();
    }

    protected void persist( Object e ) throws ApplicationException
    {
        if ( isAddNewOperation() ) {
            getSession().add( getLoggedInUser(), ( CompanyPositionDTO )e );
        }
        else
            getSession().update( getLoggedInUser(), ( CompanyPositionDTO )e );
    }

    public CompanyPositionFacade getSession()
    {
        if ( session == null )
            session = ( CompanyPositionFacade )getRemoteSession( CompanyPositionFacade.class );
        return session;
    }
}
