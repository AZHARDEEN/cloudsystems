package br.com.mcampos.ejb.cloudsystem.anode.session;

import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcPenPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.key.PgcPenPagePK;

import br.com.mcampos.ejb.session.core.Crud;

import br.com.mcampos.exception.ApplicationException;

import br.com.mcampos.sysutils.SysUtils;

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

    public List<PgcPenPage> getAll( Properties props ) throws ApplicationException
    {
        if ( props == null || props.size() == 0 )
            return getAll( PgcPenPage.getAll );


        String jpaQuery = "select o from PgcPenPage o ";
        String jpaWhere = "";
        AnotoForm form;
        String page;

        form = ( AnotoForm )props.get( "form" );
        if ( form != null ) {
            if ( jpaWhere.length() > 0 )
                jpaWhere += " AND ";
            jpaWhere += "o.penPage.page.pad.form = :form";
        }
        page = ( String )props.get( "page" );
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
        if ( jpaWhere.length() > 0 )
            jpaQuery += " WHERE " + jpaWhere;
        Query query = getEntityManager().createQuery( jpaQuery );
        if ( form != null )
            query.setParameter( "form", form );
        if ( SysUtils.isEmpty( page ) == false )
            query.setParameter( "page", page );

        return query.getResultList();
    }

    public void delete( PgcPenPagePK key ) throws ApplicationException
    {
        delete( PgcPenPage.class, key );
    }

    public PgcPenPage get( PgcPenPagePK key ) throws ApplicationException
    {
        return get( PgcPenPage.class, key );
    }
}
