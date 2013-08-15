package br.com.mcampos.ejb.fdigital.upload;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.dto.MediaDTO;
import br.com.mcampos.ejb.fdigital.pen.AnotoPen;
import br.com.mcampos.ejb.fdigital.penpage.AnotoPenPage;
import br.com.mcampos.ejb.fdigital.pgc.Pgc;

@Remote
public interface UploadSession
{
	void setStatus( Pgc pgc, Integer status );

	Pgc persist( Pgc pgc, MediaDTO dto );

	AnotoPen getPen( String pen );

	List<AnotoPenPage> getPenPages( String penId, List<String> pages );
}
