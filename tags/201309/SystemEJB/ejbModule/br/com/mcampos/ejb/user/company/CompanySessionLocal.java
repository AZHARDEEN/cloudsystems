package br.com.mcampos.ejb.user.company;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.user.Company;

@Local
public interface CompanySessionLocal extends BaseCrudSessionInterface<Company>
{

}