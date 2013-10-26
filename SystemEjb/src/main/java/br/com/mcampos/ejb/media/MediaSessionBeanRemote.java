package br.com.mcampos.ejb.media;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.system.Media;

@Remote
public interface MediaSessionBeanRemote extends BaseCrudSessionInterface<Media>
{

}
