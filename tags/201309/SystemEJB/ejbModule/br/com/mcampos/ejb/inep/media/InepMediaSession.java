package br.com.mcampos.ejb.inep.media;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.inep.InepMedia;

@Remote
public interface InepMediaSession extends BaseCrudSessionInterface<InepMedia>
{

}
