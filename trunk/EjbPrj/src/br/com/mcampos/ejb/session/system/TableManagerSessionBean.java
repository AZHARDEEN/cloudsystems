package br.com.mcampos.ejb.session.system;

import br.com.mcampos.dto.system.SystemParametersDTO;
import br.com.mcampos.dto.user.attributes.CompanyPositionDTO;
import br.com.mcampos.dto.user.attributes.CompanyTypeDTO;
import br.com.mcampos.dto.user.login.AccessLogTypeDTO;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.login.AccessLogType;
import br.com.mcampos.ejb.entity.system.SystemParameters;


import br.com.mcampos.ejb.entity.user.attributes.CompanyPosition;

import br.com.mcampos.ejb.entity.user.attributes.CompanyType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless( name = "TableManagerSession", mappedName = "CloudSystems-EjbPrj-TableManagerSession" )
@Remote
@Local
public class TableManagerSessionBean implements TableManagerSession, TableManagerSessionLocal 
{

    @PersistenceContext( unitName="EjbPrj" )
    private EntityManager em;

    public TableManagerSessionBean() 
    {
    }

    public void add( SystemParametersDTO dto ) 
    {
        SystemParameters systemParameter;
        
        systemParameter = DTOFactory.copy ( dto );
        em.persist(systemParameter);
    }


    public void add( AccessLogTypeDTO dto ) 
    {
        AccessLogType entity;
        
        entity = DTOFactory.copy ( dto );
        em.persist( entity );
    }


    public void update( SystemParametersDTO dto ) 
    {
        SystemParameters systemParameter;
        
        systemParameter = DTOFactory.copy ( dto );
        em.merge(systemParameter);
    }
    

    public void update( AccessLogTypeDTO dto ) 
    {
        AccessLogType entity;
        
        entity = DTOFactory.copy ( dto );
        em.merge( entity );
    }
    

    /** <code>select o from SystemParameters o</code> */
    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public List<SystemParametersDTO> getAll() 
    {
        return copyList ( (List<SystemParameters>)  em.createNamedQuery("SystemParameters.findAll").getResultList() );
    }


    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public List<AccessLogTypeDTO> getAllAccessLogType() 
    {
        return copyListAccessType ( (List<AccessLogType>) em.createNamedQuery("AccessLogType.findAll").getResultList() );
    }


    protected List<SystemParametersDTO> copyList ( List<SystemParameters> list )
    {
        List<SystemParametersDTO> dtos = null;
        
        if ( list == null )
            return Collections.emptyList();
        dtos = new ArrayList<SystemParametersDTO>( list.size () );
        for ( SystemParameters item : list )
            dtos.add( DTOFactory.copy (item) );
        return dtos;
    }




    protected List<AccessLogTypeDTO> copyListAccessType ( List<AccessLogType> list )
    {
        List<AccessLogTypeDTO> dtos = null;
        
        if ( list == null )
            return Collections.emptyList();
        dtos = new ArrayList<AccessLogTypeDTO>( list.size () );
        for ( AccessLogType item : list )
            dtos.add( DTOFactory.copy (item) );
        return dtos;
    }


    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public SystemParametersDTO getSystemParameter( String id ) 
    {
        SystemParameters systemParameter;
        
        systemParameter = em.find ( SystemParameters.class, id );
        return DTOFactory.copy ( systemParameter );
    }
    

    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public AccessLogTypeDTO getAccessLogType( Integer id ) 
    {
        AccessLogType entity;
        
        entity = em.find ( AccessLogType.class, id );
        return DTOFactory.copy ( entity );
    }
    
    
    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public Integer getAccessLogTypeMaxValue ()
    {
        Query query = em.createNativeQuery( "Select max( coalesce ( alt_id_in, 0 ) ) + 1 as idMax from access_log_type" );
        
        return (Integer) query.getSingleResult();
    }

    public void deleteAccessLogType( Integer id )
    {
        AccessLogType entity;
        
        
        entity = em.find(AccessLogType.class, id );
        if ( entity != null )
            em.remove(entity);
    }






    protected List<CompanyPositionDTO> copyListCompanyPosition ( List<CompanyPosition> list )
    {
        List<CompanyPositionDTO> dtos = null;
        
        if ( list == null )
            return Collections.emptyList();
        dtos = new ArrayList<CompanyPositionDTO>( list.size () );
        for ( CompanyPosition item : list )
            dtos.add( DTOFactory.copy (item) );
        return dtos;
    }



    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public CompanyPositionDTO getCompanyPosition( Integer id ) 
    {
        CompanyPosition entity;
        
        entity = em.find ( CompanyPosition.class, id );
        return DTOFactory.copy ( entity );
    }
    
    
    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public Integer getCompanyPositionMaxValue ()
    {
        Query query = em.createNativeQuery( "Select max( coalesce ( alt_id_in, 0 ) ) + 1 as idMax from access_log_type" );
        
        return (Integer) query.getSingleResult();
    }
    
    public void add( CompanyPositionDTO dto ) 
    {
        CompanyPosition entity;
        
        entity = DTOFactory.copy ( dto );
        em.persist( entity );
    }

    public void update( CompanyPositionDTO dto ) 
    {
        CompanyPosition entity;
        
        entity = DTOFactory.copy ( dto );
        em.merge( entity );
    }
    

    public void deleteCompanyPosition( Integer id )
    {
        CompanyPosition entity;
        
        
        entity = em.find(CompanyPosition.class, id );
        if ( entity != null )
            em.remove(entity);
    }





    protected List<CompanyTypeDTO> copyListCompanyType ( List<CompanyType> list )
    {
        List<CompanyTypeDTO> dtos = null;
        
        if ( list == null )
            return Collections.emptyList();
        dtos = new ArrayList<CompanyTypeDTO>( list.size () );
        for ( CompanyType item : list )
            dtos.add( DTOFactory.copy (item) );
        return dtos;
    }



    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public CompanyTypeDTO getCompanyType( Integer id ) 
    {
        CompanyType entity;
        
        entity = em.find ( CompanyType.class, id );
        return DTOFactory.copy ( entity );
    }
    
    
    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public Integer getCompanyTypeMaxValue ()
    {
        Query query = em.createNativeQuery( "Select max( coalesce ( alt_id_in, 0 ) ) + 1 as idMax from access_log_type" );
        
        return (Integer) query.getSingleResult();
    }


    public void add( CompanyTypeDTO dto ) 
    {
        CompanyType entity;
        
        entity = DTOFactory.copy ( dto );
        em.persist( entity );
    }


    public void update( CompanyTypeDTO dto ) 
    {
        CompanyType entity;
        
        entity = DTOFactory.copy ( dto );
        em.merge( entity );
    }



    public void deleteCompanyType( Integer id )
    {
        CompanyType entity;
        
        
        entity = em.find(CompanyType.class, id );
        if ( entity != null )
            em.remove(entity);
    }


    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public List<CompanyTypeDTO> getAllCompanyType() 
    {
        return copyListCompanyType ( (List<CompanyType>) em.createNamedQuery("CompanyType.findAll").getResultList() );
    }

    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public List<CompanyPositionDTO> getAllCompanyPosition() 
    {
        return copyListCompanyPosition ( (List<CompanyPosition>) em.createNamedQuery("CompanyPosition.findAll").getResultList() );
    }

}
