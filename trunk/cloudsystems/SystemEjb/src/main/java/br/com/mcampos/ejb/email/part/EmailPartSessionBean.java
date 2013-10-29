package br.com.mcampos.ejb.email.part;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.dto.MailDTO;
import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.email.EmailSessionLocal;
import br.com.mcampos.jpa.system.EMail;
import br.com.mcampos.jpa.system.EMailPart;
import br.com.mcampos.jpa.system.EMailPartType;
import br.com.mcampos.sysutils.SysUtils;

/**
 * Session Bean implementation class EmailPartSessionBean
 */
@Stateless
@LocalBean
public class EmailPartSessionBean extends SimpleSessionBean<EMailPart> implements EmailPartSession, EmailPartSessionLocal
{
	private static final long serialVersionUID = 4323918684639108025L;

	private static final Logger LOGGER = LoggerFactory.getLogger( EmailPartSessionBean.class );
	@EJB
	private EmailSessionLocal emailSession;

	@Resource( mappedName = "java:/JmsXA" )
	private ConnectionFactory cf;

	// @Resource( mappedName = "java:/cloudq" )
	@Resource( lookup = "java:jboss/exported/jms/queue/cloudq" )
	private Queue queue;

	private Connection connection;
	private Session session;

	@Override
	protected Class<EMailPart> getEntityClass( )
	{
		return EMailPart.class;
	}

	@Override
	public MailDTO getTemplate( EMail templateId )
	{
		List<EMailPart> parts = this.findByNamedQuery( EMailPart.getParts, templateId );
		if ( SysUtils.isEmpty( parts ) ) {
			return null;
		}
		MailDTO ret = new MailDTO( );
		for ( EMailPart part : parts ) {
			switch ( part.getType( ).getId( ) ) {
			case EMailPartType.partSubject:
				ret.setSubject( part.getText( ) );
				break;
			case EMailPartType.partBody:
				ret.setBody( part.getText( ) );
				break;
			default:
				throw new IllegalArgumentException( "O tipo de parte de email não está definido" );
			}
		}
		return ret;
	}

	@Override
	public MailDTO getTemplate( Integer templateId )
	{
		EMail email = this.emailSession.get( templateId );
		if ( email == null ) {
			return null;
		}
		return this.getTemplate( email );
	}

	@Override
	public Boolean sendMail( MailDTO dto )
	{
		try {
			MessageProducer mp = this.getSession( ).createProducer( this.queue );
			ObjectMessage objmsg = this.getSession( ).createObjectMessage( dto );
			LOGGER.info( "Putting into email queue" );
			mp.send( objmsg );
			mp.close( );
			return true;
		}
		catch ( JMSException e ) {
			LOGGER.error( "Error sending email", e );
			return false;
		}
	}

	private Connection getConnection( ) throws JMSException
	{
		if ( this.connection == null ) {
			LOGGER.info( "Creating a new Connection" );
			this.connection = this.cf.createConnection( );
		}
		return this.connection;
	}

	private Session getSession( ) throws JMSException
	{
		if ( this.session == null ) {
			LOGGER.info( "Creating a new Email Session" );
			this.session = this.getConnection( ).createSession( false, Session.AUTO_ACKNOWLEDGE );
		}
		return this.session;
	}

}
