package br.com.mcampos.ejb.media;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Remote
public interface MediaSessionBeanRemote extends BaseSessionInterface<Media>
{

}
