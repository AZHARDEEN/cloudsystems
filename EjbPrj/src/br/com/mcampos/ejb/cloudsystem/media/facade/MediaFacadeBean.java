package br.com.mcampos.ejb.cloudsystem.media.facade;

import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.media.Session.MediaSessionLocal;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.core.AbstractSecurity;

import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.exception.ApplicationException;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless( name = "MediaFacade", mappedName = "CloudSystems-EjbPrj-MediaFacade" )
public class MediaFacadeBean extends AbstractSecurity implements MediaFacade
{
    protected static final int messageTypeId = 8;

    @PersistenceContext( unitName = "EjbPrj" )
    private EntityManager em;

    @EJB
    private MediaSessionLocal media;

    public MediaFacadeBean()
    {
    }

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageTypeId;
    }

    public MediaDTO add( AuthenticationDTO auth, MediaDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        MediaDTO retDTO = ( media.add( DTOFactory.copy( entity ) ) ).toDTO();
        return retDTO;
    }

    public void delete( AuthenticationDTO auth, Integer key ) throws ApplicationException
    {
        authenticate( auth );
        media.delete( key );
    }

}
