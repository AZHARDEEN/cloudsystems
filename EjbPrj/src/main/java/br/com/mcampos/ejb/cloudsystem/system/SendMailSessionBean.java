package br.com.mcampos.ejb.cloudsystem.system;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.mcampos.dto.system.SendMailDTO;

@Stateless( name = "SendMailSession", mappedName = "SendMailSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class SendMailSessionBean implements SendMailSessionLocal
{
	/**
     *
     */
	private static final long serialVersionUID = -4770856464922902526L;

	/*
	 * INFO: NO WEBLogic, DEVE ser ativado o XA-Transaction para o Connection
	 * Factory
	 */

	public SendMailSessionBean( )
	{
	}

	@Override
	public void sendMail( SendMailDTO dto )
	{
	}

}
