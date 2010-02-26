package br.com.mcampos.ejb.session.system;

import br.com.mcampos.dto.system.SendMailDTO;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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
public class SendMailSessionBean implements SendMailSessionLocal
{
    @Resource(mappedName="jms/CloudSystemsCF")  private ConnectionFactory cf;
    @Resource(mappedName="jms/CloudSystemQueue") private Queue queue;
    private Connection connection;
    private Session session;
    
    public SendMailSessionBean()
    {
    }
    
    
    @PostConstruct
    protected void init ()
    {

        try {
            connection = cf.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        }
        catch ( JMSException e ) {
            connection = null;
            session = null;
        }
    }
    
    @PreDestroy
    protected void destroy ()
    {
        if ( connection != null ) {
            try {
                session.close();
                connection.close ();
            }
            catch ( JMSException e ) {
                e = null;
            }
        }
    }
    
    
    public void sendMail ( SendMailDTO dto )
    {
        try {
            if ( getConnection() != null ) {
                MessageProducer mp = getSession().createProducer(queue);
                ObjectMessage objmsg = getSession().createObjectMessage( dto);
                mp.send(objmsg);
                mp.close();
            }
        }
        catch ( JMSException e ) {
            e = null;
        }
    }

    protected Connection getConnection ()
    {
        return connection;
    }

    public Session getSession ()
    {
        return session;
    }
}
