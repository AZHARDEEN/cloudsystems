package br.com.mcampos.ejb.user.company;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Remote
public interface CompanySession extends BaseSessionInterface<Company>
{

}
