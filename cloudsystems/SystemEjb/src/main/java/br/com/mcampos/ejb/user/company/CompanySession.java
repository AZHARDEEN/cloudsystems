package br.com.mcampos.ejb.user.company;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.user.Company;

@Remote
public interface CompanySession extends BaseCrudSessionInterface<Company>
{

}
