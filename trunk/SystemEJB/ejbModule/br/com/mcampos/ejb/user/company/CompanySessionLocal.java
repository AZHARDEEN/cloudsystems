package br.com.mcampos.ejb.user.company;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.user.Company;

@Local
public interface CompanySessionLocal extends BaseSessionInterface<Company>
{

}
