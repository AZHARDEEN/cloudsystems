package br.com.mcampos.ejb.cloudsystem.anode.session;


import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Pgc;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcPenPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.key.PgcPenPagePK;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless( name = "PgcPenPageSession", mappedName = "CloudSystems-EjbPrj-PgcPenPageSession" )
@TransactionAttribute( value = TransactionAttributeType.MANDATORY )
public class PgcPenPageSessionBean extends Crud<PgcPenPagePK, PgcPenPage> implements PgcPenPageSessionLocal
{
    @PersistenceContext( unitName = "EjbPrj" )
    private EntityManager em;


    public PgcPenPageSessionBean()
    {
    }

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return 9;
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<PgcPage> getAll( Properties props ) throws ApplicationException
    {
        String jpaQuery = "select o from PgcPage o ";
        String jpaWhere = "";
        AnotoForm form;
        String page;
        String pen;

        form = ( AnotoForm )( props != null ? props.get( "form" ) : null );
        if ( form != null ) {
            if ( jpaWhere.length() > 0 )
                jpaWhere += " AND ";
            jpaWhere += " o.pgc IN ( select ppp.pgc from PgcPenPage ppp where ppp.penPage.page.pad.form = :form ) ";
        }

        page = ( String )( props != null ? props.get( "page" ) : "" );
        if ( SysUtils.isEmpty( page ) == false ) {
            if ( jpaWhere.length() > 0 )
                jpaWhere += " AND ";
            if ( page.indexOf( "*" ) >= 0 ) {
                page = page.replace( '*', '%' );
                jpaWhere += "o.penPage.page.pageAddress LIKE :page";
            }
            else
                jpaWhere += "o.penPage.page.pageAddress = :page";

        }

        pen = ( String )( props != null ? props.get( "pen" ) : "" );
        if ( SysUtils.isEmpty( pen ) == false ) {
            if ( jpaWhere.length() > 0 )
                jpaWhere += " AND ";
            if ( pen.indexOf( "*" ) >= 0 ) {
                pen = pen.replace( '*', '%' );
                jpaWhere += "o.penPage.pen.id LIKE :pen";
            }
            else
                jpaWhere += "o.penPage.pen.id = :pen";
        }

        Date initDate = ( Date )( props != null ? props.get( "initDate" ) : "" );
        if ( initDate != null ) {
            if ( jpaWhere.length() > 0 )
                jpaWhere += " AND ";
            jpaWhere += "o.pgc.insertDate >= :initDate";
        }

        Date endDate = ( Date )( props != null ? props.get( "endDate" ) : "" );
        if ( endDate != null ) {
            if ( jpaWhere.length() > 0 )
                jpaWhere += " AND ";
            jpaWhere += "o.pgc.insertDate <= :endDate";
        }

        if ( jpaWhere.length() > 0 )
            jpaQuery += " WHERE " + jpaWhere + " ORDER BY o.pgc.insertDate desc ";
        Query query = getEntityManager().createQuery( jpaQuery );
        query.setMaxResults( 20 );
        if ( form != null )
            query.setParameter( "form", form );
        if ( SysUtils.isEmpty( page ) == false )
            query.setParameter( "page", page );
        if ( SysUtils.isEmpty( pen ) == false )
            query.setParameter( "pen", pen );
        if ( initDate != null )
            query.setParameter( "initDate", initDate );
        if ( endDate != null )
            query.setParameter( "endDate", endDate );

        List<PgcPage> list = ( List<PgcPage> )query.getResultList();
        if ( SysUtils.isEmpty( list ) )
            return Collections.emptyList();
        return Collections.emptyList();
    }

    public void delete( PgcPenPagePK key ) throws ApplicationException
    {
        delete( PgcPenPage.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public PgcPenPage get( PgcPenPagePK key ) throws ApplicationException
    {
        return get( PgcPenPage.class, key );
    }

    public void delete( Pgc entity ) throws ApplicationException
    {
        Query query = getEntityManager().createNamedQuery( PgcPenPage.deleteFromPgc );
        query.setParameter( 1, entity );
        query.executeUpdate();
    }
}
