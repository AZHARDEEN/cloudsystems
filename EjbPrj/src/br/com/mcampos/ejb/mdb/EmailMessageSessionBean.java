package br.com.mcampos.ejb.mdb;

import br.com.mcampos.dto.system.SendMailDTO;


import com.sun.mail.smtp.SMTPSSLTransport;

import java.util.Date;
import java.util.Properties;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@MessageDriven( mappedName = "jms/CloudSystemQueue", activationConfig = { @ActivationConfigProperty( propertyName = "destinationType", propertyValue = "javax.jms.Queue" ), @ActivationConfigProperty( propertyName = "destinationName", propertyValue = "jms/CloudSystemQueue" ) } )
public class EmailMessageSessionBean implements MessageListener
{
    private static final String SMTP_SERVER_ADDRESS = "smtps.uol.com.br";
    private static final Integer SMTP_SERVER_PORT = 587;
    private static final String SMTP_USENAME = "cloudsystems";
    private static final String SMTP_FROM = "cloudsystems@uol.com.br";
    private static final String SMTP_PASSWORD = "lvsu132";


    public void onMessage( Message message )
    {
        if ( message instanceof ObjectMessage ) {
            ObjectMessage objMessage = ( ObjectMessage )message;
            Object object;

            try {
                object = objMessage.getObject();
                if ( object != null && object instanceof SendMailDTO ) {
                    SendMailDTO dto = ( SendMailDTO )object;
                    sendMail( dto );
                }
            }
            catch ( JMSException e ) {
                e = null;
            }
        }
    }

    protected void sendMail( SendMailDTO dto )
    {
        Properties props = new Properties();

        java.security.Security.addProvider( new com.sun.net.ssl.internal.ssl.Provider() );
        props.put( "mail.transport.protocol", "smtp" );
        props.put( "mail.smtp.auth", "true" );
        props.put( "mail.smtp.host", SMTP_SERVER_ADDRESS );
        props.put( "mail.debug", "true" );
        props.put( "mail.smtp.debug", "true" );
        props.put( "mail.smtp.port", "587" );
        props.put( "mail.smtp.starttls.enable", "true" );
        props.put( "mail.smtp.ssl.enable", "true" );
        props.put( "mail.smtp.socketFactory.port", "587" );
        /*         props.put ( "mail.user", SMTP_USENAME );
        props.put ( "mail.host", "smtps.uol.com.br" );
        props.put ( "mail.transport.protocol", "smtp" );
        props.put ( "mail.from", SMTP_FROM ); */


        try {
            Transport transport;
            Authenticator auth = new SMTPAuthenticator( );
            Session mailSession = Session.getDefaultInstance( props, auth );

            mailSession.setDebug( true );

            transport = mailSession.getTransport( "smtp" );
            transport.connect( SMTP_SERVER_ADDRESS, SMTP_SERVER_PORT, SMTP_USENAME, SMTP_PASSWORD );
            transport.close();
        }
        catch ( NoSuchProviderException e ) {
            e = null;
        }
        catch ( MessagingException e ) {
            e.getStackTrace();
            e = null;
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
