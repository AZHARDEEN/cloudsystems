package br.com.mcampos.ejb.user.company;

import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;

/**
 * Session Bean implementation class CompanySessionBean
 */
@Stateless(name = "CompanySession", mappedName = "CompanySession")
public class CompanySessionBean extends SimpleSessionBean<Company> implements CompanySessionLocal, CompanySession
{

	@Override
	protected Class<Company> getEntityClass( )
	{
		return Company.class;
	}

}
