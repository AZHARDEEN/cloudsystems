package br.com.mcampos.ejb.system.fileupload;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Remote
public interface UploadStatusSession extends BaseSessionInterface<UploadStatus>
{
}
