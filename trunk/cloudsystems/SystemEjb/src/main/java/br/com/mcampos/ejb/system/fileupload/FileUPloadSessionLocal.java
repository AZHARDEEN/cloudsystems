package br.com.mcampos.ejb.system.fileupload;

import javax.ejb.Local;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.system.FileUpload;

@Local
public interface FileUPloadSessionLocal extends BaseCrudSessionInterface<FileUpload>
{
	FileUpload addNewFile( PrincipalDTO c, MediaDTO media );
}
