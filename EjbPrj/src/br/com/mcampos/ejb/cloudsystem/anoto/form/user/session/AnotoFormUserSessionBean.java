package br.com.mcampos.ejb.cloudsystem.anoto.form.user.session;


import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoFormSessionLocal;
import br.com.mcampos.ejb.cloudsystem.anoto.form.user.entity.AnotoFormUser;
import br.com.mcampos.ejb.cloudsystem.anoto.form.user.entity.AnotoFormUserPK;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.company.session.CompanySessionLocal;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.Date;

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
        AnotoForm form = formSession.get( entity.getFormId() );
        Company company = companySession.get( entity.getUserId() );
        entity.setForm( form );
        entity.setCompany( company );
        entity.setFromDate( new Date() );
        entity.setSequence( nextSequence( entity.getForm() ) );
        return super.add( entity );
    }

    private Integer nextSequence( AnotoForm form ) throws ApplicationException
    {
        return nextIntegerId( AnotoFormUser.nextSequence, form );
    }
}
