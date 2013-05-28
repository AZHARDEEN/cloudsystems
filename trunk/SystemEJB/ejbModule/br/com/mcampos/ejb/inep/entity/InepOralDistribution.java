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
@Table( name = "inep_oral_distribution" )
public class InepOralDistribution implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InepOralDistributionPK id;

	public InepOralDistribution( )
	{
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