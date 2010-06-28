package br.com.mcampos.ejb.cloudsystem.anoto.form.user.session;


import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoFormSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.form.user.entity.AnotoFormUser;
import br.com.mcampos.ejb.cloudsystem.anoto.form.user.entity.AnotoFormUserPK;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.company.session.CompanySessionLocal;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "AnotoFormUserSession", mappedName = "CloudSystems-EjbPrj-AnotoFormUserSession" )
@TransactionAttribute( value = TransactionAttributeType.MANDATORY )
public class AnotoFormUserSessionBean extends Crud<AnotoFormUserPK, AnotoFormUser> implements AnotoFormUserSessionLocal
{
    @EJB
    private AnotoFormSessionLocal formSession;

    @EJB
    private CompanySessionLocal companySession;


    public AnotoFormUserSessionBean()
    {
    }

    public void delete( AnotoFormUserPK key ) throws ApplicationException
    {
        AnotoFormUser entity = get( AnotoFormUser.class, key );
        if ( entity != null )
            entity.setToDate( new Date() );
    }

    @Override
    public AnotoFormUser add( AnotoFormUser entity ) throws ApplicationException
    {
        entity.setFromDate( new Date() );
        entity.setSequence( nextSequence( entity.getForm() ) );
        return super.add( entity );
    }

    public AnotoFormUser add( Integer formId, Integer companyId ) throws ApplicationException
    {
        AnotoFormUser entity = new AnotoFormUser();
        AnotoForm form = formSession.get( formId );
        Company company = companySession.get( companyId );
        entity.setForm( form );
        entity.setCompany( company );
        return add( entity );
    }

    private Integer nextSequence( AnotoForm form ) throws ApplicationException
    {
        Integer id = nextIntegerId( AnotoFormUser.nextSequence, form );
        if ( SysUtils.isZero( id ) )
            id = 1;
        return id;
    }

    public List<AnotoFormUser> get( Integer formId ) throws ApplicationException
    {
        return ( List<AnotoFormUser> )getResultList( AnotoFormUser.getFormUser, formId );
    }

    public List<AnotoFormUser> get( Integer formId, Integer companyId ) throws ApplicationException
    {
        ArrayList<Object> param = new ArrayList<Object>( 2 );
        param.add( formId );
        param.add( companyId );
        return ( List<AnotoFormUser> )getResultList( AnotoFormUser.getUser, param );
    }


    public void delete( Integer formId, Integer companyId ) throws ApplicationException
    {
        List<AnotoFormUser> users = get( formId, companyId );
        if ( SysUtils.isEmpty( users ) == false ) {
            for ( AnotoFormUser user : users ) {
                user.setToDate( new Date() );
            }
        }
    }
}
