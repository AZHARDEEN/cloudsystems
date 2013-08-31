package br.com.mcampos.ejb.fdigital.upload;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.dto.MediaDTO;
import br.com.mcampos.entity.fdigital.AnotoPen;
import br.com.mcampos.entity.fdigital.AnotoPenPage;
import br.com.mcampos.entity.fdigital.Pgc;

@Remote
public interface UploadSession
{
	void setStatus( Pgc pgc, Integer status );

	Pgc persist( Pgc pgc, MediaDTO dto );

	AnotoPen getPen( String pen );

	List<AnotoPenPage> getPenPages( String penId, List<String> pages );
}
