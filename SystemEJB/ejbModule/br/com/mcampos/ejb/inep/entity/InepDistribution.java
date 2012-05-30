package br.com.mcampos.ejb.inep.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.ejb.inep.revisor.InepRevisor;
import br.com.mcampos.ejb.inep.test.InepTest;


/**
 * The persistent class for the inep_distribution database table.
 * 
 */
@Entity
@Table( name = "inep_distribution", schema = "inep" )
@NamedQueries( {
		@NamedQuery( name = InepDistribution.getAllFromRevisor, query = "select o from InepDistribution o where o.revisor = ?1 and o.status.id = 1 and o.revisor.task = o.test.task order by o.test.subscription" ),
	@NamedQuery( name = InepDistribution.getAllFromTest, query = "select o from InepDistribution o where o.test = ?1 and o.status.id = 2" )
} )
public class InepDistribution implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String getAllFromRevisor = "InepDistribution.getAllFromRevisor";
	public static final String getAllFromTest = "InepDistribution.getAllFromTest";

	@EmbeddedId
	private InepDistributionPK id;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn( name = "ids_id_in", referencedColumnName = "ids_id_in", updatable = true, insertable = true, nullable = false )
	private DistributionStatus status;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumns( {
		@JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", updatable = false, insertable = false, nullable = false ),
		@JoinColumn( name = "pct_id_in", referencedColumnName = "pct_id_in", updatable = false, insertable = false, nullable = false ),
		@JoinColumn( name = "col_seq_in", referencedColumnName = "col_seq_in", updatable = false, insertable = false, nullable = false )
	} )
	private InepRevisor revisor;

	@ManyToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumns( {
		@JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", updatable = false, insertable = false, nullable = false ),
		@JoinColumn( name = "pct_id_in", referencedColumnName = "pct_id_in", updatable = false, insertable = false, nullable = false ),
		@JoinColumn( name = "tsk_id_in", referencedColumnName = "tsk_id_in", updatable = false, insertable = false, nullable = false ),
		@JoinColumn( name = "isc_id_ch", referencedColumnName = "isc_id_ch", updatable = false, insertable = false, nullable = false ) } )
	private InepTest test;

	@Column( name = "dis_adequacao_in" )
	private Integer adequacao;

	@Column( name = "dis_clareza_in" )
	private Integer clareza;

	@Column( name = "dis_enunciador_in" )
	private Integer encunciador;

	@Column( name = "dis_grade_in" )
	private Integer nota;

	@Column( name = "dis_interlocutor_in" )
	private Integer interlocutor;

	@Column( name = "dis_proposito_in" )
	private Integer proposicao;

	@Column( name = "dis_obs_tx" )
	private String obs;

	public InepDistribution() {
	}

	public InepDistribution( InepRevisor r, InepTest t )
	{
		getId( ).set( r, t );
	}

	public InepDistributionPK getId() {
		if ( this.id == null ) {
			this.id = new InepDistributionPK();
		}
		return this.id;
	}

	public void setId(InepDistributionPK id) {
		this.id = id;
	}

	public DistributionStatus getStatus( )
	{
		return this.status;
	}

	public void setStatus( DistributionStatus status )
	{
		this.status = status;
	}

	public Integer getAdequacao( )
	{
		if ( this.adequacao == null ) {
			this.adequacao = 0;
		}
		return this.adequacao;
	}

	public void setAdequacao( Integer adequacao )
	{
		this.adequacao = adequacao;
	}

	public Integer getClareza( )
	{
		if ( this.clareza == null ) {
			this.clareza = 0;
		}
		return this.clareza;
	}

	public void setClareza( Integer clareza )
	{
		this.clareza = clareza;
	}

	public Integer getEncunciador( )
	{
		if ( this.encunciador == null ) {
			this.encunciador = 0;
		}
		return this.encunciador;
	}

	public void setEncunciador( Integer encunciador )
	{
		this.encunciador = encunciador;
	}

	public Integer getNota( )
	{
		if ( this.nota == null ) {
			this.nota = 0;
		}
		return this.nota;
	}

	public void setNota( Integer nota )
	{
		this.nota = nota;
	}

	public Integer getInterlocutor( )
	{
		if ( this.interlocutor == null ) {
			this.interlocutor = 0;
		}
		return this.interlocutor;
	}

	public void setInterlocutor( Integer interlocutor )
	{
		this.interlocutor = interlocutor;
	}

	public Integer getProposicao( )
	{
		if ( this.proposicao == null ) {
			this.proposicao = 0;
		}
		return this.proposicao;
	}

	public void setProposicao( Integer proposicao )
	{
		this.proposicao = proposicao;
	}

	public InepRevisor getRevisor( )
	{
		return this.revisor;
	}

	public void setRevisor( InepRevisor revisor )
	{
		this.revisor = revisor;
		if ( getRevisor( ) != null ) {
			getId( ).set( revisor  );
		}
	}

	public InepTest getTest( )
	{
		return this.test;
	}

	public void setTest( InepTest test )
	{
		this.test = test;
	}

	public String getObs( )
	{
		return this.obs;
	}

	public void setObs( String obs )
	{
		this.obs = obs;
	}

}