package br.com.mcampos.ejb.inep.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

/**
 * The persistent class for the subscriptiongradeview database table.
 * 
 */
@Entity
@NamedNativeQueries( {
		@NamedNativeQuery(
				name = SubscriptionGradeView.getAllByEvent,
				resultClass = SubscriptionGradeView.class,
				query = "select * from inep.subscriptionGradeView where usr_id_in = ? and pct_id_in = ? " )
} )
public class SubscriptionGradeView implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final String getAllByEvent = "SubscriptionGradeView.getAllByEvent";

	@EmbeddedId
	private InepSubscriptionPK id;

	@Column( name = "nf1" )
	private Double tarefa1;

	@Column( name = "nf2" )
	private Double tarefa2;

	@Column( name = "nf3" )
	private Double tarefa3;

	@Column( name = "nf4" )
	private Double tarefa4;

	public SubscriptionGradeView( )
	{
	}

	public Double getTarefa1( )
	{
		return this.tarefa1;
	}

	public void setTarefa1( Double tarefa1 )
	{
		this.tarefa1 = tarefa1;
	}

	public Double getTarefa2( )
	{
		return this.tarefa2;
	}

	public void setTarefa2( Double tarefa2 )
	{
		this.tarefa2 = tarefa2;
	}

	public Double getTarefa3( )
	{
		return this.tarefa3;
	}

	public void setTarefa3( Double tarefa3 )
	{
		this.tarefa3 = tarefa3;
	}

	public Double getTarefa4( )
	{
		return this.tarefa4;
	}

	public void setTarefa4( Double tarefa4 )
	{
		this.tarefa4 = tarefa4;
	}

	public InepSubscriptionPK getId( )
	{
		return this.id;
	}

	public void setId( InepSubscriptionPK id )
	{
		this.id = id;
	}

}