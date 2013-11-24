package br.com.mcampos.ejb.user.company;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.user.Company;
import br.com.mcampos.jpa.user.UserDocument;

@Local
public interface CompanySessionLocal extends BaseCrudSessionInterface<Company>
{
	List<UserDocument> searchByDocument( Integer documentType, String lookFor );

	List<UserDocument> searchByCPF( String lookFor );

	List<UserDocument> searchByEmail( String lookFor );

	List<Company> getWithUploadJobsEnabled( );

}
