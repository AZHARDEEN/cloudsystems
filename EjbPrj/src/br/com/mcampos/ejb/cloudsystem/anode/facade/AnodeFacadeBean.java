package br.com.mcampos.ejb.cloudsystem.anode.facade;


import br.com.mcampos.dto.anode.FormDTO;
import br.com.mcampos.dto.anode.PenDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Form;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Pen;
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

    public List<PenDTO> getAvailablePens( AuthenticationDTO auth, FormDTO dto ) throws ApplicationException
    {
        authenticate( auth );
        return toPenList( formSession.getAvailablePens( dto.getId() ) );
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

    public MediaDTO addToForm( AuthenticationDTO auth, FormDTO form, MediaDTO media ) throws ApplicationException
    {
        authenticate( auth );
        Media mediaEntity;

        if ( SysUtils.isZero( media.getId() ) )
            mediaEntity = mediaSession.add( DTOFactory.copy( media ) );
        else
            mediaEntity = mediaSession.get( media.getId() );
        return formSession.addMedia( form.getId(), mediaEntity ).toDTO();
    }


    public MediaDTO removeFromForm( AuthenticationDTO auth, FormDTO form, MediaDTO media ) throws ApplicationException
    {
        authenticate( auth );
        Media mediaEntity;

        if ( SysUtils.isZero( media.getId() ) )
            return null;
        mediaEntity = mediaSession.get( media.getId() );
        return formSession.removeMedia( form.getId(), mediaEntity ).toDTO();
    }

    public List<MediaDTO> getMedias( AuthenticationDTO auth, FormDTO dto ) throws ApplicationException
    {
        authenticate( auth );
        return toMediaList( formSession.getMedias( dto.getId() ) );
    }

    public List<PenDTO> getPens( AuthenticationDTO auth, FormDTO dto ) throws ApplicationException
    {
        authenticate( auth );
        return toPenList( formSession.getPens( dto.getId() ) );
    }

    public PenDTO insertIntoForm( AuthenticationDTO auth, FormDTO key, PenDTO toRemove ) throws ApplicationException
    {
        authenticate( auth );
        return insertIntoForm( key.getId(), toRemove ).toDTO();
    }

    protected Pen insertIntoForm( Integer key, PenDTO toRemove ) throws ApplicationException
    {
        Pen entity = penSession.get( toRemove.getId() );
        return formSession.addPen( key, entity );
    }

    public PenDTO removeFromForm( AuthenticationDTO auth, FormDTO key, PenDTO toRemove ) throws ApplicationException
    {
        authenticate( auth );
        return removeFromForm( key.getId(), toRemove ).toDTO();
    }

    protected Pen removeFromForm( Integer key, PenDTO toRemove ) throws ApplicationException
    {
        Pen entity = penSession.get( toRemove.getId() );
        entity = formSession.removePen( key, entity );
        return entity;
    }


    public List<PenDTO> insertIntoForm( AuthenticationDTO auth, FormDTO key, List<PenDTO> itens ) throws ApplicationException
    {
        authenticate( auth );
        ArrayList<Pen> entities = new ArrayList<Pen>( itens.size() );
        for ( PenDTO entity : itens ) {
            entities.add( insertIntoForm( key.getId(), entity ) );
        }
        return toPenList( entities );
    }

    public List<PenDTO> removeFromForm( AuthenticationDTO auth, FormDTO key, List<PenDTO> itens ) throws ApplicationException
    {
        authenticate( auth );
        ArrayList<Pen> entities = new ArrayList<Pen>( itens.size() );
        for ( PenDTO entity : itens ) {
            entities.add( removeFromForm( key.getId(), entity ) );
        }
        return toPenList( entities );
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

    public List<FormDTO> getAvailableForms( AuthenticationDTO auth, PenDTO dto ) throws ApplicationException
    {
        authenticate( auth );
        return toFormList( penSession.getAvailableForms( dto.getId() ) );
    }

    public List<FormDTO> getForms( AuthenticationDTO auth, PenDTO dto ) throws ApplicationException
    {
        authenticate( auth );
        return toFormList( penSession.getForms( dto.getId() ) );
    }

    protected List<FormDTO> toFormList( List<Form> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<FormDTO> dtoList = new ArrayList<FormDTO>( list.size() );
        for ( Form f : list ) {
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


    protected List<PenDTO> toPenList( List<Pen> list )
    {
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        List<PenDTO> dtoList = new ArrayList<PenDTO>( list.size() );
        for ( Pen f : list ) {
            dtoList.add( f.toDTO() );
        }
        return dtoList;
    }


    public FormDTO insertIntoPen( AuthenticationDTO auth, PenDTO key, FormDTO toRemove ) throws ApplicationException
    {
        authenticate( auth );
        return insertIntoPen( key.getId(), toRemove ).toDTO();
    }

    protected Form insertIntoPen( String key, FormDTO toRemove ) throws ApplicationException
    {
        Form formEntity = formSession.get( toRemove.getId() );
        return penSession.addForm( key, formEntity );
    }

    public FormDTO removeFromPen( AuthenticationDTO auth, PenDTO key, FormDTO toRemove ) throws ApplicationException
    {
        authenticate( auth );
        return removeFromPen( key.getId(), toRemove ).toDTO();
    }

    protected Form removeFromPen( String key, FormDTO toRemove ) throws ApplicationException
    {
        Form entity = formSession.get( toRemove.getId() );
        entity = penSession.removeForm( key, entity );
        return entity;
    }


    public List<FormDTO> insertIntoPen( AuthenticationDTO auth, PenDTO key, List<FormDTO> itens ) throws ApplicationException
    {
        authenticate( auth );
        ArrayList<Form> entities = new ArrayList<Form>( itens.size() );
        for ( FormDTO entity : itens ) {
            entities.add( insertIntoPen( key.getId(), entity ) );
        }
        return toFormList( entities );
    }

    public List<FormDTO> removeFromPen( AuthenticationDTO auth, PenDTO key, List<FormDTO> itens ) throws ApplicationException
    {
        authenticate( auth );
        ArrayList<Form> entities = new ArrayList<Form>( itens.size() );
        for ( FormDTO entity : itens ) {
            entities.add( removeFromPen( key.getId(), entity ) );
        }
        return toFormList( entities );
    }

}
