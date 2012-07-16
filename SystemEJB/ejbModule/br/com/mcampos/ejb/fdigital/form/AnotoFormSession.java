package br.com.mcampos.ejb.fdigital.form;

import javax.ejb.Remote;

import br.com.mcampos.dto.MediaDTO;
import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.fdigital.form.media.FormMedia;

@Remote
public interface AnotoFormSession extends BaseSessionInterface<AnotoForm>
{
	AnotoForm getRelationships( AnotoForm f );

	AnotoForm add( AnotoForm f, MediaDTO m );

	AnotoForm remove( AnotoForm f, FormMedia fm );

}
