package br.com.mcampos.ejb.session.user;

import br.com.mcampos.dto.user.CompanyDTO;

import br.com.mcampos.dto.user.login.LoginDTO;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.user.Collaborator;
import br.com.mcampos.ejb.entity.user.Company;


import br.com.mcampos.ejb.entity.user.Person;

import br.com.mcampos.ejb.entity.user.attributes.CollaboratorType;
import br.com.mcampos.ejb.session.system.SystemMessagesSessionLocal;

import br.com.mcampos.sysutils.SysUtils;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;



import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless( name = "CompanySession", mappedName = "CloudSystems-EjbPrj-CompanySession" )
@Remote
@Local
public class CompanySessionBean implements CompanySession, CompanySessionLocal
{
    @PersistenceContext( unitName="EjbPrj" )
    private EntityManager em;
    @EJB SystemMessagesSessionLocal systemMessage;

    public CompanySessionBean()
    {
    }
    
    public Company add ( CompanyDTO dto )
    {
        Company entity = DTOFactory.copy ( dto );
        
        em.persist( entity );
        return entity;
    }


    public Company update ( CompanyDTO dto )
    {
        Company entity = DTOFactory.copy ( dto );
        
        em.merge( entity );
        return entity;
    }


    public Company addBusinessEntity ( CompanyDTO dto, LoginDTO login )
    {
        Company entity = DTOFactory.copy ( dto );
        
        em.persist( entity );
        return entity;
    }


    public Company updateBusinessEntity ( CompanyDTO dto, LoginDTO login )
    {
        Company entity = DTOFactory.copy ( dto );
        
        em.merge( entity );
        return entity;
    }

    
    public Boolean hasManagers ( Integer companyId )
    {
        Long managersCount;
        
        try {
            managersCount = (Long) em.createNamedQuery( "Collaborator.hasManager" )
                                    .setParameter( "companyId", companyId )
                                    .getSingleResult();
            return managersCount > 0;
        }
        catch ( NoResultException e ) 
        {
            return false;
        }
    }
    
    public Boolean isManager( Integer companyId, Integer personId )
    {
        Integer manager;
        
        try {
            manager = (Integer) em.createNamedQuery( "Collaborator.isManager" )
                                    .setParameter( "companyId", companyId )
                                    .setParameter( "personId", personId )
                                    .getSingleResult();
            return manager != null ? true : false;
        }
        catch ( NoResultException e ) 
        {
            return false;
        }
    }


    public void addCollaborator ( Integer companyId, Integer personId, Integer collaboratorType )
    {
        Collaborator newCollaborator;
        
        try 
        {
            newCollaborator = (Collaborator) em.createNamedQuery( "Collaborator.hasCollaborator" )
                .setParameter( "companyId", companyId )
                .setParameter( "personId", personId )
                .getSingleResult();
        }
        catch ( NoResultException e )
        {
            newCollaborator = new Collaborator ();
            newCollaborator.setCompany( em.find( Company.class, companyId ) );
            newCollaborator.setPerson( em.find( Person.class, personId ) );
            newCollaborator.setFromDate( SysUtils.nowTimestamp() );
            newCollaborator.setCollaboratorType( em.find ( CollaboratorType.class, collaboratorType ) );
            newCollaborator.setCompanyPosition( 5 );
            if ( newCollaborator.getPerson() == null )
                throw new EJBException ( "Não existe este colaborador no banco de dados" ); 
            if ( newCollaborator.getCompany() == null )
                throw new EJBException ( "Não existe esta empresa no banco de dados" );
            /*Já existe este colaborador no banco de dados*/
            em.persist( newCollaborator );
        }
    }


    public void addEmployeeCollaborator ( Integer companyId, Integer personId )
    {
        Collaborator newCollaborator;
        
        try 
        {
            newCollaborator = (Collaborator) em.createNamedQuery( "Collaborator.hasCollaborator" )
                .setParameter( "companyId", companyId )
                .setParameter( "personId", personId )
                .getSingleResult();
        }
        catch ( NoResultException e )
        {
            newCollaborator = new Collaborator ();
            newCollaborator.setCompany( em.find( Company.class, companyId ) );
            newCollaborator.setPerson( em.find( Person.class, personId ) );
            newCollaborator.setFromDate( SysUtils.nowTimestamp() );
            newCollaborator.setCollaboratorType( em.find ( CollaboratorType.class, CollaboratorType.typeManager ) );
            if ( newCollaborator.getPerson() == null )
                throw new EJBException ( "Não existe este colaborador no banco de dados" ); 
            if ( newCollaborator.getCompany() == null )
                throw new EJBException ( "Não existe esta empresa no banco de dados" );
            /*Já existe este colaborador no banco de dados*/
            em.persist( newCollaborator );
        }
    }



    public void removeCollaborator ( Integer companyId, Integer personId )
    {
        Collaborator newCollaborator;
        
        try 
        {
            newCollaborator = (Collaborator) em.createNamedQuery( "Collaborator.hasCollaborator" )
                .setParameter( "companyId", companyId )
                .setParameter( "personId", personId )
                .getSingleResult();
            newCollaborator.setToDate( SysUtils.nowTimestamp() );
        }
        catch ( NoResultException e )
        {
            throw new EJBException ( "Não existe este colaborador no banco de dados" ); 
        }
    }
    
}
