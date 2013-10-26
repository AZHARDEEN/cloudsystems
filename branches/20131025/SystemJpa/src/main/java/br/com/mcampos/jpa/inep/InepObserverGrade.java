package br.com.mcampos.jpa.inep;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the inep_observer_grade database table.
 * 
 */
@Entity
@Table( name = "inep_observer_grade", schema = "inep" )
@NamedQuery( name = "InepObserverGrade.findAll", query = "SELECT i FROM InepObserverGrade i" )
public class InepObserverGrade extends BaseInepSubscription implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InepObserverGradePK id;

	@Column( name = "iot_grade_in" )
	private Integer grade;

	public InepObserverGrade( )
	{
	}

	public InepObserverGrade( InepSubscription sub, int index, int grade )
	{
		super( sub );
		this.getId( ).setId( index );
		this.setGrade( grade );
	}

	@Override
	public InepObserverGradePK getId( )
	{
		if ( this.id == null ) {
			this.id = new InepObserverGradePK( );
		}
		return this.id;
	}

	public void setId( InepObserverGradePK id )
	{
		this.id = id;
	}

	public Integer getGrade( )
	{
		return this.grade;
	}

	public void setGrade( Integer iotGradeIn )
	{
		this.grade = iotGradeIn;
	}

}