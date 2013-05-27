package br.com.mcampos.ejb.inep.media;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.InepMedia;

@Local
public interface InepMediaSessionLocal extends BaseSessionInterface<InepMedia>
{

}
