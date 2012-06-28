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

import br.com.mcampos.dto.MailDTO;
import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.email.EMail;
import br.com.mcampos.ejb.email.EmailMessageBean;
import br.com.mcampos.ejb.email.EmailSessionLocal;
import br.com.mcampos.ejb.email.parttype.EMailPartType;
import br.com.mcampos.sysutils.SysUtils;

/**
 * Session Bean implementation class EmailPartSessionBean
 */
@Stateless
@LocalBean
public class EmailPartSessionBean extends SimpleSessionBean<EMailPart> implements EmailPartSession, EmailPartSessionLocal
{
	@EJB
	private EmailSessionLocal emailSession;

	@Resource( mappedName = "java:/JmsXA" )
	private transient ConnectionFactory cf;
	@Resource( mappedName = EmailMessageBean.destinationName )
	private transient Queue queue;

	@Override
	protected Class<EMailPart> getEntityClass( )
	{
		return EMailPart.class;
	}

	@Override
	public MailDTO getTemplate( EMail templateId )
	{
		List<EMailPart> parts = findByNamedQuery( EMailPart.getParts, templateId );
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
		return getTemplate( email );
	}

	@Override
	public Boolean sendMail( MailDTO dto )
	{
		Connection connection;
		Session session;
		try {
			connection = this.cf.createConnection( );
			if ( connection != null ) {
				session = connection.createSession( false, Session.AUTO_ACKNOWLEDGE );
				if ( session != null ) {
					MessageProducer mp = session.createProducer( this.queue );
					ObjectMessage objmsg = session.createObjectMessage( dto );
					mp.send( objmsg );
					mp.close( );
					return true;
				}
				return false;
			}
			return false;
		}
		catch ( JMSException e ) {
			e.printStackTrace( );
			return false;
		}
	}

}
