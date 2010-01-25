package br.com.mcampos.ejb.mdb;

import br.com.mcampos.dto.system.SendMailDTO;


import java.util.Date;
import java.util.Properties;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@MessageDriven( mappedName = "jms/CloudSystemQueue",
                activationConfig = {
                    @ActivationConfigProperty(
                        propertyName="destinationType",
                        propertyValue="javax.jms.Queue"
                    ),
                    @ActivationConfigProperty(
                        propertyName="destinationName",
                        propertyValue="jms/CloudSystemQueue"
                    )
                } 
)
public class EmailMessageSessionBean implements MessageListener
{
    private static final String SMTP_SERVER_ADDRESS = "smpts.uol.com.br";
    private static final String SMTP_SERVER_PORT = "587";
    private static final String SMTP_USENAME = "cloudsystems";
    private static final String SMTP_FROM = "cloudsystems@uol.com.br";
    private static final String SMTP_PASSWORD = "lvsu132";
        
        
    public void onMessage( Message message )
    {
        if ( message instanceof ObjectMessage ) 
        {
            ObjectMessage objMessage = (ObjectMessage) message;
            Object object;

            try {
                object = objMessage.getObject();
                if ( object != null && object instanceof SendMailDTO ) 
                {
                    SendMailDTO dto = ( SendMailDTO ) object;
                    sendMail ( dto );
                }
            }
            catch ( JMSException e ) {
                e = null;
            }
        }
    }
    
    protected void sendMail ( SendMailDTO dto )
    {
        Properties props = System.getProperties();
        
        props.put ( "mail.smtp.host", SMTP_SERVER_ADDRESS );
        props.put ( "mail.smtp.port", SMTP_SERVER_PORT );
        props.put ( "mail.user", SMTP_USENAME );
        props.put ( "mail.host", "smtps.uol.com.br" );
        props.put ( "mail.transport.protocol", "smtp" );
        props.put ( "mail.from", SMTP_FROM );
        
        
        Session session = Session.getInstance(props, null);
        Transport bus;


        try {
            bus = session.getTransport("smtps");
            bus.connect("smtps.uol.com.br", SMTP_USENAME, SMTP_PASSWORD);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_FROM));
            for ( String item : dto.getRecipients() ) {
                InternetAddress[] address =  InternetAddress.parse(item,false);
                message.addRecipients( MimeMessage.RecipientType.TO, address );
            }
            message.setSubject(dto.getSubject());
            message.setSentDate(new Date());
            message.setText(dto.getBody());
            Transport.send(message);
        }
        catch ( NoSuchProviderException e ) {
            e = null;
        }
        catch ( MessagingException e ) {
            e = null;
        }
    }
}
