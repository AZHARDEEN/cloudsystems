package br.com.mcampos.ejb.media;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.system.Media;

@Remote
public interface MediaSessionBeanRemote extends BaseSessionInterface<Media>
{

}
