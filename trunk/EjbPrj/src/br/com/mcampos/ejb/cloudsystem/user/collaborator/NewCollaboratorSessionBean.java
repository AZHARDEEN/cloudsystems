package br.com.mcampos.ejb.cloudsystem.user.collaborator;


import br.com.mcampos.ejb.cloudsystem.user.Users;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.entity.Collaborator;
import br.com.mcampos.ejb.cloudsystem.user.collaborator.entity.CollaboratorPK;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;
import br.com.mcampos.ejb.cloudsystem.user.company.session.CompanySessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;
import br.com.mcampos.ejb.cloudsystem.user.person.session.NewPersonSessionLocal;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "NewCollaboratorSession", mappedName = "CloudSystems-EjbPrj-NewCollaboratorSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class NewCollaboratorSessionBean extends Crud<CollaboratorPK, Collaborator> implements NewCollaboratorSessionLocal
{
    @EJB
    private NewPersonSessionLocal personSession;

    @EJB
    private CompanySessionLocal companySession;

    public NewCollaboratorSessionBean()
    {
    }

    public void delete( CollaboratorPK key ) throws ApplicationException
    {
        delete( Collaborator.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Collaborator get( CollaboratorPK key ) throws ApplicationException
    {
        return get( Collaborator.class, key );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<Collaborator> get( Person person ) throws ApplicationException
    {
        if ( person == null )
            return Collections.emptyList();
        return ( List<Collaborator> )getResultList( Collaborator.findCompanies, person.getId() );
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Collaborator get( Company company, Person person ) throws ApplicationException
    {
        ArrayList<Object> param = new ArrayList<Object>();
        param.add( company );
        param.add( person );
        Collaborator coll = ( Collaborator )getSingleResult( Collaborator.hasCollaborator, param );
        return coll;
    }

    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public List<Collaborator> get( Users user ) throws ApplicationException
    {
        return ( List<Collaborator> )getResultList( Collaborator.getAllCompanyCollaborator, user );
    }


    @TransactionAttribute( TransactionAttributeType.SUPPORTS )
    public Collaborator get( Integer companyId, Integer userId ) throws ApplicationException
    {
        Person person = personSession.get( userId );
        if ( person == null )
            return null;
        Company company = companySession.get( companyId );
        if ( company == null )
            return null;
        return get( company, person );
    }

}
