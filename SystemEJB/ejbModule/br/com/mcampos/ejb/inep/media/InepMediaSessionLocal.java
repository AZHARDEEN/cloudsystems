package br.com.mcampos.ejb.inep.media;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.inep.InepMedia;
import br.com.mcampos.entity.inep.InepSubscription;
import br.com.mcampos.entity.system.Media;

@Local
public interface InepMediaSessionLocal extends BaseSessionInterface<InepMedia>
{
	InepMedia addAudio( InepSubscription isc, Media media );
}
