package br.com.mcampos.ejb.cloudsystem.anode.facade;


import br.com.mcampos.ejb.cloudsystem.anode.session.PgcFieldSessionBean;
import br.com.mcampos.ejb.core.AbstractSecurity;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "AnotoExportSession", mappedName = "CloudSystems-EjbPrj-AnotoExportSession" )
@TransactionAttribute( TransactionAttributeType.NEVER )
public class AnotoExportSessionBean extends AbstractSecurity implements AnotoExportSession
{
	protected static final int SystemMessageTypeId = 8;

	@PersistenceContext( unitName = "EjbPrj" )
	private EntityManager em;

	@EJB
	private PgcFieldSessionBean pgcFieldSession;


	public AnotoExportSessionBean()
	{
	}

	protected EntityManager getEntityManager()
	{
		return em;
	}

	public Integer getMessageTypeId()
	{
		return SystemMessageTypeId;
	}
}
