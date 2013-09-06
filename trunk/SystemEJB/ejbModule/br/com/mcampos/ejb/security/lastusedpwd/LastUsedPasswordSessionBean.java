package br.com.mcampos.ejb.security.lastusedpwd;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.entity.security.LastUsedPassword;
import br.com.mcampos.entity.security.LastUsedPasswordPK;
import br.com.mcampos.entity.security.Login;

/**
 * Session Bean implementation class LastUsedPasswordSessionBean
 */
@Stateless
@LocalBean
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class LastUsedPasswordSessionBean extends SimpleSessionBean<LastUsedPassword> implements LastUsedPasswordSessionLocal
{
	@Override
	protected Class<LastUsedPassword> getEntityClass( )
	{
		return LastUsedPassword.class;
	}

	@Override
	public LastUsedPassword get( Login login )
	{
		LastUsedPasswordPK pk = new LastUsedPasswordPK( );
		pk.setId( login.getId( ) );
		pk.setPassword( login.getPassword( ) );
		return get( pk );
	}

	@Override
	public void closeAllUsedPassword( Login login )
	{
		Query q = getEntityManager( ).createQuery( "UPDATE LastUsedPassword set toDate =  CURRENT_TIMESTAMP() " +
				"where login = ?1 and toDate is null " );
		q.setParameter( 1, login );
		q.executeUpdate( );
	}

}
