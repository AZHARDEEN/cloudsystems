package br.com.mcampos.ejb.cloudsystem.user.civilstate.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.attributes.CivilStateDTO;
import br.com.mcampos.ejb.cloudsystem.user.civilstate.CivilStateUtil;
import br.com.mcampos.ejb.cloudsystem.user.civilstate.entity.CivilState;
import br.com.mcampos.ejb.cloudsystem.user.civilstate.session.CivilStateSessionLocal;
import br.com.mcampos.ejb.core.AbstractSecurity;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "CivilStateFacade", mappedName = "CloudSystems-EjbPrj-CivilStateFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class CivilStateFacadeBean extends AbstractSecurity implements CivilStateFacade
{
	public static final Integer messageId = 20;

	@PersistenceContext( unitName = "EjbPrj" )
	private transient EntityManager em;

	@EJB
	private CivilStateSessionLocal session;

	public CivilStateFacadeBean()
	{
	}

	protected EntityManager getEntityManager()
	{
		return em;
	}

	public Integer getMessageTypeId()
	{
		return messageId;
	}

	public Integer nextId( AuthenticationDTO auth ) throws ApplicationException
	{
		authenticate( auth );
		return session.nextIntegerId();
	}

	public void delete( AuthenticationDTO auth, Integer id ) throws ApplicationException
	{
		authenticate( auth );
		session.delete( id );
	}

	public CivilStateDTO get( AuthenticationDTO auth, Integer id ) throws ApplicationException
	{
		authenticate( auth );
		CivilState entity = session.get( id );
		return CivilStateUtil.copy( entity );
	}

	public CivilStateDTO add( AuthenticationDTO auth, CivilStateDTO dto ) throws ApplicationException
	{
		authenticate( auth );
		CivilState entity = session.get( dto.getId() );
		if ( entity != null )
			throwException( 1 );
		entity = session.add( CivilStateUtil.createEntity( dto ) );
		return CivilStateUtil.copy( entity );
	}

	public CivilStateDTO update( AuthenticationDTO auth, CivilStateDTO dto ) throws ApplicationException
	{
		authenticate( auth );
		CivilState entity = session.get( dto.getId() );
		if ( entity == null )
			throwException( 1 );
		entity = session.update( CivilStateUtil.update( entity, dto ) );
		return CivilStateUtil.copy( entity );
	}

	public List<CivilStateDTO> getAll() throws ApplicationException
	{
		return CivilStateUtil.toDTOList( session.getAll() );
	}
}
