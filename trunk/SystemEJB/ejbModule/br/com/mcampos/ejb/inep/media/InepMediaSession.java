package br.com.mcampos.ejb.inep.media;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.InepMedia;

@Remote
public interface InepMediaSession extends BaseSessionInterface<InepMedia>
{

}
