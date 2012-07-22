package br.com.mcampos.ejb.fdigital.upload;

import javax.ejb.Remote;

import br.com.mcampos.dto.MediaDTO;
import br.com.mcampos.ejb.fdigital.pgc.Pgc;

@Remote
public interface UploadSession
{
	Pgc add( MediaDTO dto );

	void setStatus( Pgc pgc, Integer status );

	Pgc update( Pgc pgc );
}
