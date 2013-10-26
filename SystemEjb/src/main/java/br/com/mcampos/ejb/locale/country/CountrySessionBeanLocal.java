package br.com.mcampos.ejb.locale.country;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.locale.Country;

@Local
public interface CountrySessionBeanLocal extends BaseCrudSessionInterface<Country>
{

}
