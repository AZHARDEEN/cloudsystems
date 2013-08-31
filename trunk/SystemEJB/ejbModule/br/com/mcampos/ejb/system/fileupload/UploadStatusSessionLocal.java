package br.com.mcampos.ejb.system.fileupload;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.system.UploadStatus;

@Local
public interface UploadStatusSessionLocal extends BaseSessionInterface<UploadStatus>
{

}
