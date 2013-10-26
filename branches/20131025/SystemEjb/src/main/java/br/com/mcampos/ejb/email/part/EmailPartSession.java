package br.com.mcampos.ejb.email.part;

import javax.ejb.Remote;

import br.com.mcampos.dto.MailDTO;
import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.system.EMail;
import br.com.mcampos.jpa.system.EMailPart;

@Remote
public interface EmailPartSession extends BaseCrudSessionInterface<EMailPart>
{
	public MailDTO getTemplate( EMail template );

	public MailDTO getTemplate( Integer templateId );
}
