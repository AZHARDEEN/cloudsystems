package br.com.mcampos.ejb.fdigital.form;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.dto.MediaDTO;
import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.fdigital.AnotoForm;
import br.com.mcampos.entity.fdigital.AnotoPage;
import br.com.mcampos.entity.fdigital.FormMedia;
import br.com.mcampos.entity.system.Media;

@Remote
public interface AnotoFormSession extends BaseSessionInterface<AnotoForm>
{
	AnotoForm getRelationships( AnotoForm f );

	AnotoForm add( AnotoForm f, MediaDTO m );

	AnotoForm add( AnotoForm f, MediaDTO m, List<AnotoPage> pages );

	AnotoForm remove( AnotoForm f, FormMedia fm );

	byte[ ] getObject( Media media );

}
