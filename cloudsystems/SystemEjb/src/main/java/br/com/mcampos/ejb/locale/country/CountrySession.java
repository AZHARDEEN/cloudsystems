package br.com.mcampos.ejb.locale.country;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.locale.Country;

@Remote
public interface CountrySession extends BaseCrudSessionInterface<Country>
{

}
