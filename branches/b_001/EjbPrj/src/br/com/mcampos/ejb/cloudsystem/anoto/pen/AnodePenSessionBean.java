package br.com.mcampos.ejb.cloudsystem.anoto.pen;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

@Stateless( name = "AnodePenSession", mappedName = "CloudSystems-EjbPrj-AnodePenSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class AnodePenSessionBean extends Crud<String, AnotoPen> implements AnodePenSessionLocal
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4996728377615345241L;

	public AnodePenSessionBean( )
	{
	}

	@Override
	public void delete( String key ) throws ApplicationException
	{
		super.delete( AnotoPen.class, key );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public AnotoPen get( String key ) throws ApplicationException
	{
		return super.get( AnotoPen.class, key );
	}

	@Override
	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<AnotoPen> getAll( Integer nStart, Integer nSize ) throws ApplicationException
	{
		return super.getAll( "Pen.findAll", nStart, nSize );
	}

	@Override
	public AnotoPen add( AnotoPen entity ) throws ApplicationException
	{
		entity.setInsertDate( new Date( ) );
		entity = super.add( entity );
		return entity;
	}

	@Override
	public Integer count( ) throws ApplicationException
	{
		Long size;
		size = (Long) getSingleResult( AnotoPen.penCount );
		return size.intValue( );
	}
}
