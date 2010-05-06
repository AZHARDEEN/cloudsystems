package br.com.mcampos.ejb.cloudsystem.anode.session;


import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcField;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.key.PgcFieldPK;
import br.com.mcampos.ejb.session.core.Crud;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@Stateless( name = "PgcFieldSession", mappedName = "CloudSystems-EjbPrj-PgcFieldSession" )
@TransactionAttribute( TransactionAttributeType.MANDATORY )
public class PgcFieldSessionBean extends Crud<PgcFieldPK, PgcField> implements PgcFieldSessionLocal
{
	public PgcFieldSessionBean()
	{
	}

	public void delete( PgcFieldPK key ) throws ApplicationException
	{
		PgcField entity = get( key );
		PgcPage parent = null;

		if ( entity != null ) {
			parent = entity.getPgcPage();
			delete( PgcField.class, key );
			if ( parent != null )
				getEntityManager().refresh( parent );
		}
	}

	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public PgcField get( PgcFieldPK key ) throws ApplicationException
	{
		return get( PgcField.class, key );
	}


	@TransactionAttribute( TransactionAttributeType.SUPPORTS )
	public List<PgcField> getAll( PgcPage pgcPage ) throws ApplicationException
	{
		return ( List<PgcField> )getResultList( PgcField.findPageFields, pgcPage );
	}
}
