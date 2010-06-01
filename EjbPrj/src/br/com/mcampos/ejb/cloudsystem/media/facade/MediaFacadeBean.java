package br.com.mcampos.ejb.cloudsystem.media.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.media.Session.MediaSessionLocal;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "MediaFacade", mappedName = "CloudSystems-EjbPrj-MediaFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
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

     
    public byte[] getObject( AuthenticationDTO auth, Integer key ) throws ApplicationException
    {
        authenticate( auth );
        return media.getObject( key );

    }

     
    public List<MediaDTO> getAllPgc( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        List<Media> entities = media.getAll( "Media.findAll" );
        return toMediaListPgc( entities );
    }


    protected List<MediaDTO> toMediaListPgc( List<Media> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<MediaDTO> dtoList = new ArrayList<MediaDTO>( list.size() );
        for ( Media f : list ) {
            String name;

            name = f.getName().toLowerCase();
            if ( name.endsWith( ".pgc" ) ) {
                dtoList.add( f.toDTO() );
            }
        }
        return dtoList;
    }

}
