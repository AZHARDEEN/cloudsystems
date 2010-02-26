package br.com.mcampos.ejb.cloudsystem.anode.facade;


import br.com.mcampos.dto.anode.FormDTO;
import br.com.mcampos.dto.anode.PenDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anode.session.AnodeFormSessionLocal;


import br.com.mcampos.ejb.cloudsystem.anode.session.AnodePenSessionLocal;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless( name = "AnodeFacade", mappedName = "CloudSystems-EjbPrj-AnodeFacade" )
public class AnodeFacadeBean extends AbstractSecurity implements AnodeFacade
{
    @PersistenceContext( unitName = "EjbPrj" )
    private EntityManager em;


    @EJB
    private AnodeFormSessionLocal formSession;

    @EJB
    private AnodePenSessionLocal penSession;
    @EJB
    private MediaSessionLocal mediaSession;

    public AnodeFacadeBean()
    {
    }

    public FormDTO add( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        return formSession.add( DTOFactory.copy( entity ) ).toDTO();
    }

    public void delete( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        formSession.delete( entity.getId() );
    }

    public FormDTO get( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        return formSession.get( entity.getId() ).toDTO();
    }

    public List<FormDTO> getForms( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        return toFormList( formSession.getAll() );
    }

    public FormDTO update( AuthenticationDTO auth, FormDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        return formSession.update( DTOFactory.copy( entity ) ).toDTO();
    }

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return 7;
    }

    public Integer nextFormId( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        return formSession.nextId();
    }


    /* *************************************************************************
     * *************************************************************************
     *
     * OPERACAO EM CANETAS
     *
     * *************************************************************************
     * *************************************************************************
     */


    public PenDTO add( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        return penSession.add( DTOFactory.copy( entity ) ).toDTO();
    }

    public void delete( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        penSession.delete( entity.getId() );
    }

    public PenDTO get( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        return penSession.get( entity.getId() ).toDTO();
    }

    public List<PenDTO> getPens( AuthenticationDTO auth ) throws ApplicationException
    {
        authenticate( auth );
        return toPenList( penSession.getAll() );
    }

    public PenDTO update( AuthenticationDTO auth, PenDTO entity ) throws ApplicationException
    {
        authenticate( auth );
        return penSession.update( DTOFactory.copy( entity ) ).toDTO();
    }

    protected List<FormDTO> toFormList( List<AnotoForm> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<FormDTO> dtoList = new ArrayList<FormDTO>( list.size() );
        for ( AnotoForm f : list ) {
            dtoList.add( f.toDTO() );
        }
        return dtoList;
    }


    protected List<MediaDTO> toMediaList( List<Media> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<MediaDTO> dtoList = new ArrayList<MediaDTO>( list.size() );
        for ( Media f : list ) {
            dtoList.add( f.toDTO() );
        }
        return dtoList;
    }


    protected List<PenDTO> toPenList( List<AnotoPen> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<PenDTO> dtoList = new ArrayList<PenDTO>( list.size() );
        for ( AnotoPen f : list ) {
            dtoList.add( f.toDTO() );
        }
        return dtoList;
    }


    /* *************************************************************************
     * *************************************************************************
     *
     * OPERACAO EM CANETAS
     *
     * *************************************************************************
     * *************************************************************************
     */

    public byte[] getObject( AuthenticationDTO auth, MediaDTO key ) throws ApplicationException
    {
        authenticate( auth );
        return mediaSession.getObject( key.getId() );
    }
}

