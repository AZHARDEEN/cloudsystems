package br.com.mcampos.ejb.fdigital.form;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.fdigital.AnotoForm;

@Local
public interface AnotoFormLocal extends BaseCrudSessionInterface<AnotoForm>
{

}
