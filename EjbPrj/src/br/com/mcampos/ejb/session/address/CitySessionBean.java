package br.com.mcampos.ejb.session.address;


import br.com.mcampos.dto.address.CityDTO;
import br.com.mcampos.ejb.core.util.DTOFactory;
import br.com.mcampos.ejb.entity.address.City;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless( name = "CitySession", mappedName = "CloudSystems-EjbPrj-CitySession" )
public class CitySessionBean implements CitySession, CitySessionLocal
{
	@PersistenceContext( unitName = "EjbPrj" )
	private EntityManager em;

	public CitySessionBean()
	{
	}


	public void add( CityDTO dto )
	{
		em.persist( DTOFactory.copy( dto ) );
	}

	public void update( CityDTO dto )
	{
		em.merge( DTOFactory.copy( dto ) );
	}

	public void delete( Integer id )
	{
		City city = em.find( City.class, id );
		em.remove( city );
	}

	@TransactionAttribute( value = TransactionAttributeType.NOT_SUPPORTED )
	public List<CityDTO> getAll()
	{
		return copyList( ( List<City> )em.createNamedQuery( "City.findAll" ).getResultList() );
	}

	protected List<CityDTO> copyList( List<City> list )
	{
		List<CityDTO> dtos = null;

		if ( list == null )
			return Collections.emptyList();

		dtos = new ArrayList<CityDTO>( list.size() );
		for ( City item : list )
			dtos.add( DTOFactory.copy( item ) );
		return dtos;
	}

	@TransactionAttribute( value = TransactionAttributeType.NOT_SUPPORTED )
	public List<CityDTO> getAll( String countryId, Integer stateId )
	{
		Query query;

		query = em.createNamedQuery( "City.findByState" );
		query.setParameter( "stateId", stateId );
		query.setParameter( "countryId", countryId );

		List<City> list = query.getResultList();

		if ( list != null && list.size() > 0 )
			return copyList( list );
		else
			return null;
	}

	@TransactionAttribute( value = TransactionAttributeType.NOT_SUPPORTED )
	public CityDTO get( Integer id )
	{
		City record;


		record = ( City )em.find( City.class, id );
		if ( record != null )
			return DTOFactory.copy( record );
		else
			return null;
	}
}


