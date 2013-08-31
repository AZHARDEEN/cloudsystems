package br.com.mcampos.ejb.system.fileupload;

import java.util.ArrayList;

import javax.ejb.Remote;

import br.com.mcampos.dto.MediaDTO;
import br.com.mcampos.dto.upload.AssefazDTO;
import br.com.mcampos.dto.upload.RejectedDTO;
import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.utils.dto.PrincipalDTO;

@Remote
public interface FileUploadSession extends BaseSessionInterface<FileUpload>
{
	FileUpload addNewFile( PrincipalDTO c, MediaDTO media );

	FileUpload update( FileUpload entity, ArrayList<RejectedDTO> rejecteds );

	AssefazDTO add( PrincipalDTO auth, FileUpload entity, AssefazDTO dto, boolean bCreateIfNotExists );

}
