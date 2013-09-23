package br.com.mcampos.ejb.cloudsystem.system;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.system.SendMailDTO;
import br.com.mcampos.sysutils.SysUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "EmailSession", mappedName = "EmailSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class EmailSessionBean implements EmailSessionLocal
{
    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    private SystemMessagesSessionLocal systemMessage;

    protected static final Integer systemMessageTypeId = 4;

    public EmailSessionBean()
    {
    }

    public SendMailDTO get( Integer emailId ) throws ApplicationException
    {
        if ( SysUtils.isZero( emailId ) )
            return null;


        EMail template;

        template = em.find( EMail.class, emailId );
        if ( template == null )
            throwRuntimeException( 1 );
        em.refresh( template );
        SendMailDTO dto = new SendMailDTO();
        for ( EMailPart part : template.getEMailPartList() ) {
            switch ( part.getPartType().getId() ) {
            case EMailPartType.partSubject:
                dto.setSubject( part.getContent() );
                break;
            case EMailPartType.partBody:
                dto.setBody( part.getContent() );
                break;
            }
        }
        return dto;
    }

    protected void throwRuntimeException( int id ) throws ApplicationException
    {
        getSystemMessage().throwRuntimeException( systemMessageTypeId, id );
    }

    public void setSystemMessage( SystemMessagesSessionLocal systemMessage )
    {
        this.systemMessage = systemMessage;
    }

    protected SystemMessagesSessionLocal getSystemMessage()
    {
        return systemMessage;
    }
}
