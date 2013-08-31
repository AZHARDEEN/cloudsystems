package br.com.mcampos.ejb.user.document;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.user.UserDocument;
import br.com.mcampos.entity.user.Users;

@Local
public interface UserDocumentSessionLocal extends BaseSessionInterface<UserDocument>
{
	public Users getUserByDocument( String document );

	List<UserDocument> searchByDocument( Integer documentType, String lookFor );

	List<UserDocument> searchByCPF( String lookFor );

	List<UserDocument> searchByEmail( String lookFor );

}
