package br.com.mcampos.ejb.inep.media;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.inep.InepMedia;
import br.com.mcampos.jpa.inep.InepSubscription;
import br.com.mcampos.jpa.inep.InepTest;
import br.com.mcampos.jpa.system.Media;

@Local
public interface InepMediaSessionLocal extends BaseCrudSessionInterface<InepMedia>
{
	InepMedia addAudio( InepSubscription isc, Media media );

	InepMedia addPDF( InepTest test, String name, byte[ ] object );

}
