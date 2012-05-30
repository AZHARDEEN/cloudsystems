package br.com.mcampos.ejb.user.document;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.user.Users;

@Local
public interface UserDocumentSessionLocal extends BaseSessionInterface<UserDocument>
{
	public Users getUserByDocument( String document );
}
