package br.com.mcampos.ejb.email;

import java.util.Properties;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.mcampos.dto.MailDTO;

/**
 * Message-Driven Bean implementation class for: EmailMessageBean
 * 
 */
@MessageDriven( name = "EmailMessage",
		activationConfig = {
				@ActivationConfigProperty( propertyName = "destinationType", propertyValue = "javax.jms.Queue" ),
				@ActivationConfigProperty( propertyName = "destination", propertyValue = EmailMessageBean.destinationName ),
				@ActivationConfigProperty( propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge" )
		} )
public class EmailMessageBean implements MessageListener
{

	public static final String destinationName = "java:/cloudq";
	private static final String SMTP_SERVER_ADDRESS = "smtps.1and1.com";
	private static final Integer SMTP_SERVER_PORT = 587;
	private static final String SMTP_USENAME = "marcelo@meusistema.info";
	private static final String SMTP_FROM = "sistema@meusistema.info";
	private static final String SMTP_PASSWORD = "Cld09Xnl$";
	private static final String SMTP_PROTOCOL = "smtp";

	private Session mailSession = null;

	public EmailMessageBean( )
	{
	}

	@Override
	public void onMessage( Message message )
	{
		if ( message instanceof ObjectMessage ) {
			ObjectMessage objMessage = (ObjectMessage) message;
			try {

				Object object = objMessage.getObject( );
				if ( object != null && object instanceof MailDTO ) {
					MailDTO dto = (MailDTO) object;

					MimeMessage mailMessage = new MimeMessage( getMailSession( ) );
					mailMessage.setSubject( dto.getSubject( ) );
					for ( String to : dto.getRecipients( ) ) {
						mailMessage.addRecipient( MimeMessage.RecipientType.TO, new InternetAddress( to ) );
					}
					mailMessage.setFrom( new InternetAddress( SMTP_FROM ) );
					mailMessage.setContent( dto.getBody( ), "text/plain" );
					Transport.send( mailMessage );
				}
			}
			catch ( Exception e ) {
				e.printStackTrace( );
			}
		}
	}

	protected Properties configureProperties( Properties props )
	{
		// java.security.Security.addProvider( new
		// com.sun.net.ssl.internal.ssl.Provider( ) );
		// System.setProperty( "javax.net.debug", "ssl,handshake" );

		props.put( "mail.host", SMTP_SERVER_ADDRESS );
		props.put( "mail.smtp.host", SMTP_SERVER_ADDRESS );
		props.put( "mail.smtp.port", SMTP_SERVER_PORT.toString( ) );
		props.put( "mail.smtp.socketFactory.port", SMTP_SERVER_PORT.toString( ) );

		props.put( "mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory" );
		props.put( "mail.transport.protocol", SMTP_PROTOCOL );
		props.put( "mail.smtp.starttls.enable", "true" );
		props.put( "mail.smtp.ssl.enable", "true" );
		props.put( "mail.smtp.auth", "true" );

		props.put( "mail.user", SMTP_USENAME );
		props.put( "mail.pwd", SMTP_PASSWORD );
		props.put( "mail.from", SMTP_FROM );

		return props;
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator
	{

		@Override
		public PasswordAuthentication getPasswordAuthentication( )
		{
			String username = SMTP_USENAME;
			String password = SMTP_PASSWORD;
			return new PasswordAuthentication( username, password );
		}
	}

	private Session getMailSession( )
	{
		if ( mailSession == null ) {
			Properties props = new Properties( );
			SMTPAuthenticator auth = new SMTPAuthenticator( );
			props = configureProperties( props );
			mailSession = Session.getDefaultInstance( props, auth );
		}
		return mailSession;
	}

}
