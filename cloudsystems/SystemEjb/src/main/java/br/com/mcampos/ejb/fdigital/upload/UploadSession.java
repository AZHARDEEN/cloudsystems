package br.com.mcampos.ejb.fdigital.upload;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.jpa.fdigital.AnotoPen;
import br.com.mcampos.jpa.fdigital.AnotoPenPage;
import br.com.mcampos.jpa.fdigital.Pgc;

@Remote
public interface UploadSession
{
	void setStatus( Pgc pgc, Integer status );

	Pgc persist( Pgc pgc, MediaDTO dto );

	AnotoPen getPen( String pen );

	List<AnotoPenPage> getPenPages( String penId, List<String> pages );
}
