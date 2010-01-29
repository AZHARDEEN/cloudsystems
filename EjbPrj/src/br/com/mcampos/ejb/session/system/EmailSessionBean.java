package br.com.mcampos.ejb.session.system;

import br.com.mcampos.dto.system.SendMailDTO;

import br.com.mcampos.ejb.entity.system.EMail;

import br.com.mcampos.ejb.entity.system.EMailPart;
import br.com.mcampos.ejb.entity.system.EMailPartType;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless( name = "EmailSession", mappedName = "CloudSystems-EjbPrj-EmailSession" )
@Local
public class EmailSessionBean implements EmailSessionLocal
{
    @PersistenceContext( unitName="EjbPrj" )
    private EntityManager em;
    
    @EJB
    SystemMessagesSessionLocal systemMessage;
    
    protected static final Integer systemMessageTypeId = 4;
    
    public EmailSessionBean()
    {
    }
    
    public SendMailDTO get ( Integer emailId ) throws ApplicationException
    {
        if ( SysUtils.isZero( emailId ) )
            return null;


        EMail template;
                
        template = em.find( EMail.class, emailId );
        if ( template == null )
            throwRuntimeException( 1 );
        
        SendMailDTO dto = new SendMailDTO();
        for ( EMailPart part: template.getEMailPartList() ) 
        {
            switch ( part.getPartType().getId() )
            {
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

    protected void throwRuntimeException ( int id ) throws ApplicationException
    {
        getSystemMessage().throwRuntimeException( systemMessageTypeId, id );
    }

    public void setSystemMessage ( SystemMessagesSessionLocal systemMessage )
    {
        this.systemMessage = systemMessage;
    }

    protected SystemMessagesSessionLocal getSystemMessage ()
    {
        return systemMessage;
    }
}
