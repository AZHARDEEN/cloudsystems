package br.com.mcampos.ejb.cloudsystem.anode.session;


import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.FormMedia;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Pad;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcPenPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.key.AnotoPenPagePK;
import br.com.mcampos.ejb.cloudsystem.anode.entity.key.FormMediaPK;
import br.com.mcampos.ejb.cloudsystem.anode.entity.key.PadPK;
import br.com.mcampos.ejb.cloudsystem.anode.utils.AnotoUtils;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.NoResultException;
import javax.persistence.Query;


@Stateless( name = "AnodeFormSession", mappedName = "CloudSystems-EjbPrj-AnodeFormSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class AnodeFormSessionBean extends Crud<Integer, AnotoForm> implements AnodeFormSessionLocal
{
    public AnodeFormSessionBean()
    {
    }

    public void delete( Integer key ) throws ApplicationException
    {
        delete( AnotoForm.class, key );
        getEntityManager().flush();
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public AnotoForm get( Integer key ) throws ApplicationException
    {
        return get( AnotoForm.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<AnotoForm> getAll() throws ApplicationException
    {
        return getAll( "Form.findAll" );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Integer nextId() throws ApplicationException
    {
        return nextIntegerId( "Form.nextId" );
    }

    @Override
    public AnotoForm add( AnotoForm entity ) throws ApplicationException
    {
        entity.setInsertDate( new Date() );
        return super.add( entity );
    }

    public Pad addPadFile( AnotoForm form, Media pad, List<String> pages ) throws ApplicationException
    {
        form = getEntityManager().merge( form );
        pad = getEntityManager().merge( pad );

        Pad newEntity = new Pad( form, pad );
        newEntity.setInsertDate( new Date() );
        getEntityManager().persist( newEntity );
        //getEntityManager().refresh( newEntity );
        loadPadFile( form, newEntity, pages );
        return newEntity;
    }


    public FormMedia addFile( AnotoForm form, Media media ) throws ApplicationException
    {
        form = getEntityManager().merge( form );
        media = getEntityManager().merge( media );

        FormMedia newEntity = new FormMedia( form, media );
        getEntityManager().persist( newEntity );
        return newEntity;
    }


    public void removeFile( AnotoForm form, Media media ) throws ApplicationException
    {
        FormMediaPK pk = new FormMediaPK( form, media );

        FormMedia newEntity = getEntityManager().find( FormMedia.class, pk );
        if ( newEntity != null )
            getEntityManager().remove( newEntity );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<FormMedia> getFiles( AnotoForm form ) throws ApplicationException
    {
        List<FormMedia> list = ( List<FormMedia> )getResultList( FormMedia.formGetFiles, form );
        return list;
    }


    protected void loadPadFile( AnotoForm form, Pad pad, List<String> pages ) throws ApplicationException
    {
        AnotoPage page;
        try {
            for ( String address : pages ) {
                page = new AnotoPage( pad, address );
                getEntityManager().persist( page );
            }
        }
        catch ( Exception e ) {
            throw new EJBException( "Erro ao carregar o arquivo PAD", e );
        }
    }


    public Media removePadFile( AnotoForm form, Media pad ) throws ApplicationException
    {
        PadPK padKey = new PadPK( form.getId(), pad.getId() );
        Pad removeEntity = getEntityManager().find( Pad.class, padKey );
        getEntityManager().remove( removeEntity.getMedia() );
        getEntityManager().remove( removeEntity );
        return pad;
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<Pad> getPads( AnotoForm form ) throws ApplicationException
    {
        List<Object> parameter = new ArrayList<Object>( 1 );
        parameter.add( form.getApplication() );
        List<Pad> padList = ( List<Pad> )getResultList( Pad.padFindAllNamedQuery, parameter );
        return padList;
    }

    @Override
    public AnotoForm update( AnotoForm entity ) throws ApplicationException
    {
        AnotoForm form = get( entity.getId() );
        entity.setInsertDate( form.getInsertDate() );
        return super.update( entity );
    }


    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<AnotoPen> getAvailablePens( AnotoForm form ) throws ApplicationException
    {
        String sqlQuery;
        Query query;
        List<AnotoPen> list;

        sqlQuery =
                "SELECT " + "pen_id_ch , pen_description_ch, pen_insert_dt " + "FROM anoto_pen " + "WHERE PEN_ID_CH NOT IN ( SELECT PEN_ID_CH FROM ANOTO_PEN_PAGE WHERE FRM_ID_IN = ?1 )";
        query = getEntityManager().createNativeQuery( sqlQuery, AnotoPen.class );
        query.setParameter( 1, form.getId() );
        try {
            list = ( List<AnotoPen> )query.getResultList();
        }
        catch ( NoResultException e ) {
            e = null;
            list = Collections.emptyList();
        }
        return list;
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<AnotoPen> getPens( AnotoForm form ) throws ApplicationException
    {
        List<AnotoPenPage> list = ( List<AnotoPenPage> )getResultList( AnotoPenPage.formPensQueryName, form );
        return AnotoUtils.getPenListFromPenPage( list, true );
    }


    public void add( AnotoForm form, List<AnotoPen> pens ) throws ApplicationException
    {
        List<AnotoPage> list = ( List<AnotoPage> )getResultList( AnotoPage.formPagesGetAllNamedQuery, form );
        if ( SysUtils.isEmpty( list ) )
            return;
        for ( AnotoPen pen : pens ) {
            for ( AnotoPage page : list ) {
                AnotoPenPage penPage = new AnotoPenPage( pen, page );
                getEntityManager().persist( penPage );
            }
        }
    }

    protected boolean existsPgcPenPage( AnotoPenPage penPage ) throws ApplicationException
    {
        List list = getResultList( PgcPenPage.getAllPgcQueryName, penPage );
        return SysUtils.isEmpty( list ) ? false : true;
    }


    public void remove( AnotoForm form, List<AnotoPen> pens ) throws ApplicationException
    {
        List<AnotoPage> list = ( List<AnotoPage> )getResultList( AnotoPage.formPagesGetAllNamedQuery, form );
        if ( SysUtils.isEmpty( list ) )
            return;
        for ( AnotoPen pen : pens ) {
            for ( AnotoPage page : list ) {
                AnotoPenPagePK penPagePK = new AnotoPenPagePK( pen, page );
                AnotoPenPage penPage = getEntityManager().find( AnotoPenPage.class, penPagePK );
                if ( penPage != null && existsPgcPenPage( penPage ) == false ) {
                    getEntityManager().remove( penPage );
                }
            }
        }
    }
}
