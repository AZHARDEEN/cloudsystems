package br.com.mcampos.ejb.media;

import javax.ejb.Local;

import br.com.mcampos.dto.MediaDTO;
import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.system.Media;

@Local
public interface MediaSessionBeanLocal extends BaseSessionInterface<Media>
{
	Media add( MediaDTO m );

	Media findByName( String name );
}
