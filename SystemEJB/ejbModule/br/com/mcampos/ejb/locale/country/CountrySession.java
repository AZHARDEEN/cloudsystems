package br.com.mcampos.ejb.locale.country;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Remote
public interface CountrySession extends BaseSessionInterface<Country>
{

}
