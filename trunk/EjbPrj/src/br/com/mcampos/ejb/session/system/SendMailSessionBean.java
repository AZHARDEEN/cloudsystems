package br.com.mcampos.ejb.session.system;

import javax.annotation.Resource;

import javax.ejb.Local;
import javax.ejb.Stateless;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

@Stateless( name = "SendMailSession", mappedName = "CloudSystems-EjbPrj-SendMailSession" )
@Local
public class SendMailSessionBean implements SendMailSessionLocal
{
    @Resource(mappedName="jms/CloudSystemsCF")  private ConnectionFactory cf;
    @Resource(mappedName="jms/CloudSystemQueue") private Queue queue;
    
    public SendMailSessionBean()
    {
    }
    
    public void sendMail ( )
    {
        Connection conn;

        try {
            conn = cf.createConnection();
            Session sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer mp = sess.createProducer(queue);
            TextMessage objmsg = sess.createTextMessage("Este é o meu primeiro teste de envio de mes");
            mp.send(objmsg);
            conn.close();        
        }
        catch ( JMSException e ) {
        }
    }
}
