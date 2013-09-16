package br.com.mcampos.web.controller.tables;

import br.com.mcampos.ejb.system.fileupload.UploadStatusSession;
import br.com.mcampos.jpa.system.UploadStatus;
import br.com.mcampos.web.core.SimpleTableController;

public class UploadStatusController extends SimpleTableController<UploadStatusSession, UploadStatus>
{
	private static final long serialVersionUID = 3585561577076014269L;

	@Override
	protected Class<UploadStatusSession> getSessionClass( )
	{
		return UploadStatusSession.class;
	}
}
