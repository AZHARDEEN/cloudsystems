package br.com.mcampos.ejb.session.system;

import br.com.mcampos.dto.system.SendMailDTO;

import br.com.mcampos.ejb.entity.system.EMail;

import br.com.mcampos.ejb.entity.system.EMailPart;
import br.com.mcampos.ejb.entity.system.EMailPartType;
import br.com.mcampos.sysutils.SysUtils;

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
    
    
    public EmailSessionBean()
    {
    }
    
    public SendMailDTO get ( Integer emailId )
    {
        if ( SysUtils.isZero( emailId ) )
            return null;


        EMail template;
                
        template = em.find( EMail.class, emailId );
        if ( template == null )
            return null;
        
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
}
