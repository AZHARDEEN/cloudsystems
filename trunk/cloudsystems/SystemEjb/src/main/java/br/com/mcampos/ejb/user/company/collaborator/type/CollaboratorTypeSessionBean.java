package br.com.mcampos.ejb.user.company.collaborator.type;

import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.BaseCompanySessionBean;
import br.com.mcampos.jpa.user.CollaboratorType;
import br.com.mcampos.jpa.user.CollaboratorTypePK;

/**
 * Session Bean implementation class CollaboratorTypeSessionBean
 */
@Stateless( name = "CollaboratorTypeSession", mappedName = "CollaboratorTypeSession" )
public class CollaboratorTypeSessionBean extends BaseCompanySessionBean<CollaboratorType>
		implements CollaboratorTypeSession, CollaboratorTypeSessionBeanLocal
{
	private static final long serialVersionUID = 6676031081865757062L;

	@Override
	protected Class<CollaboratorType> getEntityClass( )
	{
		return CollaboratorType.class;
	}

	@Override
	public CollaboratorType get( Integer companyId, Integer typeId )
	{
		CollaboratorTypePK key = new CollaboratorTypePK( companyId, typeId );
		return this.get( key );
	}
}
