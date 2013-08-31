package br.com.mcampos.ejb.locale.country;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.locale.Country;

@Remote
public interface CountrySession extends BaseSessionInterface<Country>
{

}
