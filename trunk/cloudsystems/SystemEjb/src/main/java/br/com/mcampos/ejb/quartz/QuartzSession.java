package br.com.mcampos.ejb.quartz;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.jpa.user.Company;

@Remote
public interface QuartzSession
{
	List<Company> getCompanies( );

	public String get( String id );

}
