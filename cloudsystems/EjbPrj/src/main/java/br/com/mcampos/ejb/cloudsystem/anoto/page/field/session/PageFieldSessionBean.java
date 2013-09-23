package br.com.mcampos.ejb.cloudsystem.anoto.page.field.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.Crud;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anoto.page.field.entity.AnotoPageField;
import br.com.mcampos.ejb.cloudsystem.anoto.page.field.entity.AnotoPageFieldPK;
import br.com.mcampos.ejb.cloudsystem.system.fieldtype.entity.FieldType;
import br.com.mcampos.sysutils.SysUtils;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;


@Stateless( name = "PageFieldSession", mappedName = "PageFieldSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class PageFieldSessionBean extends Crud<AnotoPageFieldPK, AnotoPageField> implements PageFieldSessionLocal
{
    public PageFieldSessionBean()
    {
    }

    public void delete( AnotoPageFieldPK key ) throws ApplicationException
    {
        AnotoPageField field = get( key );
        if ( field != null ) {
            delete( AnotoPageField.class, key );
            getEntityManager().refresh( field.getAnotoPage() );
        }
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public AnotoPageField get( AnotoPageFieldPK key ) throws ApplicationException
    {
        return get( AnotoPageField.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<AnotoPageField> getAll() throws ApplicationException
    {
        return getAll( AnotoPageField.getAll );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<AnotoPageField> getAll( AnotoPage anotoPage ) throws ApplicationException
    {
        return ( List<AnotoPageField> )getResultList( AnotoPageField.getAllFromPage, anotoPage );
    }

    public void refresh( AnotoPage anotoPage, List<AnotoPageField> list ) throws ApplicationException
    {
        if ( SysUtils.isEmpty( list ) )
            return;
        /*
         * Delete all page fields.
         */
        Query deleteQuery = getEntityManager().createQuery( "DELETE FROM AnotoPageField o WHERE o.anotoPage = ?1" );
        deleteQuery.setParameter( 1, anotoPage );
        deleteQuery.executeUpdate();
        add( anotoPage, list );
    }

    public void add( AnotoPage anotoPage, List<AnotoPageField> list ) throws ApplicationException
    {
        if ( SysUtils.isEmpty( list ) )
            return;
        for ( AnotoPageField e : list ) {
            e.setAnotoPage( anotoPage );
            e.setType( getEntityManager().find( FieldType.class, e.getType().getId() ) );
            getEntityManager().persist( e );
        }
        if ( anotoPage != null )
            getEntityManager().refresh( anotoPage );
    }

    public List<AnotoPageField> getAll( AnotoForm form ) throws ApplicationException
    {
        return ( List<AnotoPageField> )getResultList( AnotoPageField.getAllFromForm, form.getId() );
    }

    public List<AnotoPageField> getSearchable( AnotoForm form ) throws ApplicationException
    {
        return ( List<AnotoPageField> )getResultList( AnotoPageField.getAllFromFormSearchable, form.getId() );
    }

    public List<AnotoPageField> getFieldsToExport() throws ApplicationException
    {
        return ( List<AnotoPageField> )getResultList( AnotoPageField.getExport );
    }

    public List<AnotoPageField> getPKFields( AnotoForm form ) throws ApplicationException
    {
        return ( List<AnotoPageField> )getResultList( AnotoPageField.getAllFromFormPK, form.getId() );
    }
}
