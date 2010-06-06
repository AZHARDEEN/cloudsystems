package br.com.mcampos.controller.admin.tables.documenttype;


import br.com.mcampos.controller.admin.tables.core.SimpleTableController;
import br.com.mcampos.dto.user.attributes.DocumentTypeDTO;
import br.com.mcampos.ejb.cloudsystem.user.attribute.documenttype.facade.DocumentTypeFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import org.zkoss.zul.ListitemRenderer;


public class DocumentTypeController extends SimpleTableController<DocumentTypeDTO>
{
	private DocumentTypeFacade session;

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
			DocumentTypeDTO dto = ( DocumentTypeDTO )currentRecord;
			getSession().delete( getLoggedInUser(), dto.getId() );
		}
	}

	protected Object createNewRecord()
	{
		return new DocumentTypeDTO();
	}

	protected void persist( Object e ) throws ApplicationException
	{
		if ( isAddNewOperation() ) {
			getSession().add( getLoggedInUser(), ( DocumentTypeDTO )e );
		}
		else
			getSession().update( getLoggedInUser(), ( DocumentTypeDTO )e );

	}

	public DocumentTypeFacade getSession()
	{
		if ( session == null )
			session = ( DocumentTypeFacade )getRemoteSession( DocumentTypeFacade.class );
		return session;
	}

	@Override
	protected ListitemRenderer getRenderer()
	{
		return new DocumentTypeListRenderer();
	}
}
