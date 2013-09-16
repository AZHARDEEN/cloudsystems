package br.com.mcampos.ejb.system.fileupload;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.jpa.system.UploadStatus;

/**
 * Session Bean implementation class UploadStatusSessionBean
 */
@Stateless( mappedName = "UploadStatusSession", name = "UploadStatusSession" )
@LocalBean
public class UploadStatusSessionBean extends SimpleSessionBean<UploadStatus> implements UploadStatusSession, UploadStatusSessionLocal
{
	public UploadStatusSessionBean( )
	{
	}

	@Override
	protected Class<UploadStatus> getEntityClass( )
	{
		return UploadStatus.class;
	}
}
