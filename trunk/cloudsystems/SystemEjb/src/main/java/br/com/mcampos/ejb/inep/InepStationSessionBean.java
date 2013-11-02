package br.com.mcampos.ejb.inep;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;

import br.com.mcampos.dto.inep.StationDTO;
import br.com.mcampos.ejb.core.CollaboratorBaseSessionBean;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepStation;
import br.com.mcampos.sysutils.SysUtils;

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

	@Override
	public List<StationDTO> getAll( InepEvent evt )
	{
		List<StationDTO> dtos = Collections.emptyList( );
		List<InepStation> entities = findByNamedQuery( InepStation.getAllEventStations, evt );
		if( !SysUtils.isEmpty( entities ) ) {
			dtos = new ArrayList<StationDTO>( entities.size( ) );
			for( InepStation item : entities ) {
				dtos.add( new StationDTO( item.getId( ).getClientId( ), item.getClient( ).getId( ).getSequence( ),
						item.getClient( ).getClient( ).getName( ), item.getClient( ).getInternalCode( ) ) );
			}
		}
		return dtos;
	}

}
