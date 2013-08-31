package br.com.mcampos.ejb.user.company;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.user.Company;

@Remote
public interface CompanySession extends BaseSessionInterface<Company>
{

}
