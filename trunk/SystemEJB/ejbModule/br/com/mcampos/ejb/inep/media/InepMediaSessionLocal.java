package br.com.mcampos.ejb.inep.media;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.InepMedia;
import br.com.mcampos.ejb.inep.entity.InepSubscription;
import br.com.mcampos.ejb.media.Media;

@Local
public interface InepMediaSessionLocal extends BaseSessionInterface<InepMedia>
{
	InepMedia addAudio( InepSubscription isc, Media media );
}
