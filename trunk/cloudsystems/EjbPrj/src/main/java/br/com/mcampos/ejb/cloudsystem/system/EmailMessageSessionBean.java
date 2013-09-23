package br.com.mcampos.ejb.cloudsystem.system;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class EmailMessageSessionBean implements MessageListener
{

	@Override
	public void onMessage( Message message )
	{
		if ( message instanceof ObjectMessage )
			try {
				message.acknowledge( );
			}
			catch ( JMSException e ) {
				// TODO Auto-generated catch block
				e.printStackTrace( );
			}
	}
}
