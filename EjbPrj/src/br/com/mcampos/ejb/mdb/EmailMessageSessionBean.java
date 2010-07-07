package br.com.mcampos.ejb.mdb;


import br.com.mcampos.dto.system.SendMailDTO;

import java.util.Properties;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@MessageDriven( mappedName = "jms/CloudSystemQueue",
                activationConfig = { @ActivationConfigProperty( propertyName = "destinationType",
                                                                propertyValue = "javax.jms.Queue" ),
                                     @ActivationConfigProperty( propertyName = "destinationName",
                                                                propertyValue = "jms/CloudSystemQueue" ) } )
public class EmailMessageSessionBean implements MessageListener
{
    private static final String SMTP_SERVER_ADDRESS = "187.108.192.20";
    private static final Integer SMTP_SERVER_PORT = 25;
    private static final String SMTP_USENAME = "fdigital@formulariodigital.com.br";
    private static final String SMTP_FROM = "fdigital@formulariodigital.com.br";
    private static final String SMTP_PASSWORD = "123456";


    protected Properties configureProperties( Properties props )
    {
        java.security.Security.addProvider( new com.sun.net.ssl.internal.ssl.Provider() );
        props.put( "mail.transport.protocol", "smtp" );
        props.put( "mail.smtp.auth", "false" );
        props.put( "mail.smtp.host", SMTP_SERVER_ADDRESS );
        props.put( "mail.smtp.port", SMTP_SERVER_PORT.toString() );
        props.put( "mail.smtp.starttls.enable", "false" );
        props.put( "mail.smtp.ssl.enable", "false" );
        props.put( "mail.smtp.socketFactory.port", SMTP_SERVER_PORT.toString() );
        props.put( "mail.user", SMTP_USENAME );
        props.put( "mail.pwd", SMTP_PASSWORD );
        props.put( "mail.host", SMTP_SERVER_ADDRESS );
        props.put( "mail.transport.protocol", "smtp" );
        props.put( "mail.from", SMTP_FROM );
        return props;
    }


    public void onMessage( Message message )
    {
        if ( message instanceof ObjectMessage ) {
            ObjectMessage objMessage = ( ObjectMessage )message;
            Object object;

            try {
                Session mailSession = null;
                Transport transport = null;
                Properties props = new Properties();
                Authenticator auth = new SMTPAuthenticator();
                props = configureProperties( props );
                mailSession = Session.getDefaultInstance( props, auth );
                transport = mailSession.getTransport( "smtp" );
                transport.connect( SMTP_SERVER_ADDRESS, SMTP_SERVER_PORT, SMTP_USENAME, SMTP_PASSWORD );
                object = objMessage.getObject();
                if ( object != null && object instanceof SendMailDTO ) {
                    SendMailDTO dto = ( SendMailDTO )object;
                    sendMail( dto, transport, mailSession );
                }
                if ( transport != null )
                    transport.close();
                mailSession = null;
            }
            catch ( Exception e ) {
                System.out.println( e.getMessage() );
            }
            try {
                message.acknowledge();
            }
            catch ( JMSException e ) {
                System.out.println( e.getMessage() );
            }
        }
    }

    protected void sendMail( SendMailDTO dto, Transport transport, Session mailSession )
    {
        MimeMessage message = new MimeMessage( mailSession );

        try {
            message.setSubject( dto.getSubject() );
            for ( String to : dto.getRecipients() ) {
                message.addRecipient( MimeMessage.RecipientType.TO, new InternetAddress( to ) );
            }
            message.setFrom( new InternetAddress( SMTP_FROM ) );
            message.setContent( dto.getBody(), "text/plain" );
            transport.send( message );
        }
        catch ( MessagingException e ) {
            System.out.println( e.getMessage() );
        }
    }


    private class SMTPAuthenticator extends javax.mail.Authenticator
    {

        public PasswordAuthentication getPasswordAuthentication()
        {
            String username = SMTP_USENAME;
            String password = SMTP_PASSWORD;
            return new PasswordAuthentication( username, password );
        }
    }
}
