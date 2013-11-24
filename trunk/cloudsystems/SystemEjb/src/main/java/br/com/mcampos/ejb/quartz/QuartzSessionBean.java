package br.com.mcampos.ejb.quartz;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.BaseSessionBean;
import br.com.mcampos.ejb.params.SystemParameterSessionLocal;
import br.com.mcampos.ejb.user.company.CompanySessionLocal;
import br.com.mcampos.jpa.user.Company;

/**
 * Session Bean implementation class QuartzSessionBean
 */
@Stateless( name = "QuartzSession", mappedName = "QuartzSession" )
public class QuartzSessionBean extends BaseSessionBean implements QuartzSession, QuartzSessionLocal
{
	private static final long serialVersionUID = 4481663756603460251L;

	@EJB
	private CompanySessionLocal companySession;

	@EJB
	private SystemParameterSessionLocal parameterSession;

	@Override
	public List<Company> getCompanies( )
	{
		return this.companySession.getWithUploadJobsEnabled( );
	}

	@Override
	public String get( String id )
	{
		return this.parameterSession.get( id );
	}

}
