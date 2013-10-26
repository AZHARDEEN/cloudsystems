package br.com.mcampos.ejb.system.fileupload;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.dto.AssefazDTO;
import br.com.mcampos.dto.RejectedDTO;
import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.system.FileUpload;
import br.com.mcampos.jpa.system.Media;

@Local
public interface FileUploadSessionLocal extends BaseCrudSessionInterface<FileUpload>
{
	FileUpload addNewFile( PrincipalDTO c, MediaDTO media );

	FileUpload update( FileUpload entity, ArrayList<RejectedDTO> rejecteds );

	AssefazDTO add( PrincipalDTO auth, FileUpload entity, AssefazDTO dto, boolean bCreateIfNotExists );

	List<FileUpload> getAllByMedia( Media media );
}
