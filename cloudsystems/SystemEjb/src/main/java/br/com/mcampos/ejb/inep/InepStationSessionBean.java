package br.com.mcampos.ejb.inep;

import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.CollaboratorBaseSessionBean;
import br.com.mcampos.jpa.inep.InepStation;

/**
 * Session Bean implementation class InepStationSessionBean
 */
@Stateless( mappedName = "InepStationSession" )
public class InepStationSessionBean extends CollaboratorBaseSessionBean<InepStation> implements InepStationSessionLocal
{

	private static final long serialVersionUID = 6993580427989247283L;

	@Override
	protected Class<InepStation> getEntityClass( )
	{
		return InepStation.class;
	}

}
