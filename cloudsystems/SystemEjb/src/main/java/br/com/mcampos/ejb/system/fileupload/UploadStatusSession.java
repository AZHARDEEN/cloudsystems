package br.com.mcampos.ejb.system.fileupload;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.system.UploadStatus;

@Remote
public interface UploadStatusSession extends BaseCrudSessionInterface<UploadStatus>
{
}
