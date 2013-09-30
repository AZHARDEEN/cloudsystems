package br.com.mcampos.ejb.media;

import javax.ejb.Local;

import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.system.Media;

@Local
public interface MediaSessionBeanLocal extends BaseCrudSessionInterface<Media>
{
	Media add( MediaDTO m );

	Media findByName( String name );
}
