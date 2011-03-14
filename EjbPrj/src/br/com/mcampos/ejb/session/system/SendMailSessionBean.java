package br.com.mcampos.ejb.session.system;


import br.com.mcampos.dto.system.SendMailDTO;
import br.com.mcampos.ejb.mdb.EmailMessageSessionBean;

import javax.annotation.Resource;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;


@Stateless( name = "SendMailSession", mappedName = "CloudSystems-EjbPrj-SendMailSession" )
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
    @Resource( mappedName = "JmsXA" )
    private transient ConnectionFactory cf;
    @Resource( mappedName = EmailMessageSessionBean.destinationName )
    private transient Queue queue;

    public SendMailSessionBean()
    {
    }

    @Override
    public void sendMail( SendMailDTO dto )
    {
        Connection connection;
        Session session;
        try {
            connection = cf.createConnection();
            if ( connection != null ) {
                session = connection.createSession( false, Session.AUTO_ACKNOWLEDGE );
                if ( session != null ) {
                    MessageProducer mp = session.createProducer( queue );
                    ObjectMessage objmsg = session.createObjectMessage( dto );
                    mp.send( objmsg );
                    mp.close();
                }
            }
        }
        catch ( JMSException e ) {
            e.printStackTrace();
        }
    }

}
