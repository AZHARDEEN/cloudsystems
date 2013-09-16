package br.com.mcampos.ejb.system.fileupload;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.system.UploadStatus;

@Local
public interface UploadStatusSessionLocal extends BaseCrudSessionInterface<UploadStatus>
{

}
