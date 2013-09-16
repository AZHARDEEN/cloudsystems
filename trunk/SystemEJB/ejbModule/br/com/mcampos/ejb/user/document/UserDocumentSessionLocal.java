package br.com.mcampos.ejb.user.document;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.user.UserDocument;
import br.com.mcampos.jpa.user.Users;

@Local
public interface UserDocumentSessionLocal extends BaseCrudSessionInterface<UserDocument>
{
	public Users getUserByDocument( String document );

	List<UserDocument> searchByDocument( Integer documentType, String lookFor );

	List<UserDocument> searchByCPF( String lookFor );

	List<UserDocument> searchByEmail( String lookFor );

}
