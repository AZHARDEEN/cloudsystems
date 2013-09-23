package br.com.mcampos.ejb.user.company.collaborator.type;

import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.jpa.user.CollaboratorType;

/**
 * Session Bean implementation class CollaboratorTypeSessionBean
 */
@Stateless(name = "CollaboratorTypeSession", mappedName = "CollaboratorTypeSession")
public class CollaboratorTypeSessionBean extends SimpleSessionBean<CollaboratorType>
implements CollaboratorTypeSessionBeanRemote, CollaboratorTypeSessionBeanLocal {

	@Override
	protected Class<CollaboratorType> getEntityClass( )
	{
		return CollaboratorType.class;
	}

}
