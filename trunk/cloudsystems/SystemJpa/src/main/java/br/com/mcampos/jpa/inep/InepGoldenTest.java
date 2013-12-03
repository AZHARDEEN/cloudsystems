package br.com.mcampos.jpa.inep;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the inep_golden_test database table.
 * 
 */
@Entity
@Table( name = "inep_golden_test", schema = "inep" )
@NamedQueries( {
		@NamedQuery( name = InepGoldenTest.getAll, query = "SELECT o FROM InepGoldenTest o WHERE o.id.companyId = ?1 and o.id.eventId = ?2 order by o.id" )
} )
public class InepGoldenTest implements Serializable
{
	private static final long serialVersionUID = 1L;
	public static final String getAll = "InepGoldenTest.getAll";

	@EmbeddedId
	private InepGoldenTestPK id;

	@Column( name = "igt_grade_in", nullable = false )
	private Integer baseGrade;

	public InepGoldenTest( )
	{
	}

	public InepGoldenTestPK getId( )
	{
		return this.id;
	}

	public void setId( InepGoldenTestPK id )
	{
		this.id = id;
	}

	public Integer getBaseGrade( )
	{
		return this.baseGrade;
	}

	public void setBaseGrade( Integer igtGradeIn )
	{
		this.baseGrade = igtGradeIn;
	}

}