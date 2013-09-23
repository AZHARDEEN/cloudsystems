package br.com.mcampos.ejb.fdigital.form;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.dto.MediaDTO;
import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.fdigital.AnotoForm;
import br.com.mcampos.jpa.fdigital.AnotoPage;
import br.com.mcampos.jpa.fdigital.FormMedia;
import br.com.mcampos.jpa.system.Media;
import br.com.mcampos.sysutils.dto.PrincipalDTO;

@Remote
public interface AnotoFormSession extends BaseCrudSessionInterface<AnotoForm>
{
	AnotoForm getRelationships( AnotoForm f );

	AnotoForm add( PrincipalDTO auth, AnotoForm f, MediaDTO m );

	AnotoForm add( PrincipalDTO auth, AnotoForm f, MediaDTO m, List<AnotoPage> pages );

	AnotoForm remove( PrincipalDTO auth, AnotoForm f, FormMedia fm );

	byte[ ] getObject( Media media );

}
