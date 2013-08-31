package br.com.mcampos.ejb.email.part;

import javax.ejb.Remote;

import br.com.mcampos.dto.MailDTO;
import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.system.EMail;
import br.com.mcampos.entity.system.EMailPart;

@Remote
public interface EmailPartSession extends BaseSessionInterface<EMailPart>
{
	public MailDTO getTemplate( EMail template );

	public MailDTO getTemplate( Integer templateId );
}
