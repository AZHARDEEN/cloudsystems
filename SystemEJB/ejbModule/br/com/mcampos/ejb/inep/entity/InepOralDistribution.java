package br.com.mcampos.ejb.inep.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The persistent class for the inep_oral_distribution database table.
 * 
 */
@Entity
@Table( name = "inep_oral_distribution", schema = "inep" )
public class InepOralDistribution implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InepOralDistributionPK id;

	public InepOralDistribution( )
	{
	}

	public InepOralDistribution( InepOralTest test, InepRevisor revisor )
	{
		set( test );
		set( revisor );
	}

	public void set( InepOralTest test )
	{
		getId( ).setCompanyId( test.getId( ).getUserId( ) );
		getId( ).setEventId( test.getId( ).getEventId( ) );
		getId( ).setSubscriptionId( test.getId( ).getSubscriptionId( ) );
	}

	public void set( InepRevisor revisor )
	{
		getId( ).setCompanyId( revisor.getId( ).getCompanyId( ) );
		getId( ).setEventId( revisor.getId( ).getEventId( ) );
		getId( ).setCollaboratorId( revisor.getId( ).getSequence( ) );

	}

	public InepOralDistributionPK getId( )
	{
		if ( id == null )
			id = new InepOralDistributionPK( );
		return id;
	}

	public void setId( InepOralDistributionPK id )
	{
		this.id = id;
	}

}