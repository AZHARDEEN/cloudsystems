package br.com.mcampos.ejb.mdb;


import br.com.mcampos.dto.system.SendMailDTO;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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


@MessageDriven( mappedName = "jms/CloudSystemQueue",
                activationConfig = { @ActivationConfigProperty( propertyName = "destinationType",
                                                                propertyValue = "javax.jms.Queue" ),
                                     @ActivationConfigProperty( propertyName = "destinationName",
                                                                propertyValue = "jms/CloudSystemQueue" ) } )
public class EmailMessageSessionBean implements MessageListener
{
    private static final String SMTP_SERVER_ADDRESS = "mail.formulariodigital.com.br";
    private static final Integer SMTP_SERVER_PORT = 25;
    private static final String SMTP_USENAME = "fdigital@formulariodigital.com.br";
    private static final String SMTP_FROM = "fdigital@formulariodigital.com.br";
    private static final String SMTP_PASSWORD = "123456";

    private Session mailSession;
    Transport transport;

    @PostConstruct
    public void init()
    {
        Properties props = new Properties();
        Authenticator auth = new SMTPAuthenticator();

        configureProperties( props );
        mailSession = Session.getDefaultInstance( props, auth );
        try {
            transport = mailSession.getTransport( "smtp" );
            getTransport().connect( SMTP_SERVER_ADDRESS, SMTP_SERVER_PORT, SMTP_USENAME, SMTP_PASSWORD );
        }
        catch ( NoSuchProviderException e ) {
            System.out.println( e.getMessage() );
        }
        catch ( MessagingException e ) {
            System.out.println( e.getMessage() );
        }
    }

    @PreDestroy
    protected void destroy()
    {
        try {
            if ( transport != null )
                transport.close();
            transport = null;
        }
        catch ( MessagingException e ) {
            System.out.println( e.getMessage() );
        }
        mailSession = null;
    }

    protected void configureProperties( Properties props )
    {
        java.security.Security.addProvider( new com.sun.net.ssl.internal.ssl.Provider() );
        props.put( "mail.transport.protocol", "smtp" );
        props.put( "mail.smtp.auth", "true" );
        props.put( "mail.smtp.host", SMTP_SERVER_ADDRESS );
        props.put( "mail.smtp.port", SMTP_SERVER_PORT.toString() );
        props.put( "mail.smtp.starttls.enable", "false" );
        props.put( "mail.smtp.ssl.enable", "false" );
        props.put( "mail.smtp.socketFactory.port", SMTP_SERVER_PORT.toString() );
        props.put( "mail.user", SMTP_USENAME );
        props.put( "mail.host", SMTP_SERVER_ADDRESS );
        props.put( "mail.transport.protocol", "smtp" );
        props.put( "mail.from", SMTP_FROM );
    }


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
                message.acknowledge();
            }
            catch ( JMSException e ) {
                System.out.println( e.getMessage() );
            }
        }
    }

    protected void sendMail( SendMailDTO dto )
    {
        MimeMessage message = new MimeMessage( getMailSession() );

        try {
            message.setSubject( dto.getSubject() );
            for ( String to : dto.getRecipients() ) {
                message.addRecipient( MimeMessage.RecipientType.TO, new InternetAddress( to ) );
            }
            message.setFrom( new InternetAddress( SMTP_FROM ) );
            message.setContent( dto.getBody(), "text/plain" );
            getTransport().send( message );
        }
        catch ( MessagingException e ) {
            System.out.println( e.getMessage() );
        }
    }

    public Session getMailSession()
    {
        return mailSession;
    }

    public Transport getTransport()
    {
        return transport;
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
