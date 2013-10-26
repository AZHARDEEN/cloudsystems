package br.com.mcampos.ejb.fdigital.form.media;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.fdigital.FormMedia;

@Local
public interface FormMediaSessionLocal extends BaseCrudSessionInterface<FormMedia>
{

}
