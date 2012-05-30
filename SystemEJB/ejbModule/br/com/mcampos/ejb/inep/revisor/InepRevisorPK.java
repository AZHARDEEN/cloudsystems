package br.com.mcampos.ejb.inep.revisor;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.com.mcampos.ejb.inep.packs.InepPackage;
import br.com.mcampos.ejb.inep.task.InepTask;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;

/**
 * The primary key class for the inep_revisor database table.
 * 
 */
@Embeddable
public class InepRevisorPK implements Serializable, Comparable<InepRevisorPK>
{
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="usr_id_in")
	private Integer companyId;

	@Column(name="col_seq_in")
	private Integer sequence;

	@Column( name = "pct_id_in" )
	private Integer eventId;

	public InepRevisorPK() {
	}

	public InepRevisorPK( Collaborator c, InepPackage e )
	{
		set( c );
		set( e );
	}

	public void set( Collaborator c )
	{
		setCompanyId( c.getId( ).getCompanyId( ) );
		setSequence( c.getId( ).getSequence( ) );
	}

	public void set( InepTask c )
	{
		setCompanyId( c.getId( ).getCompanyId( ) );
		setEventId( c.getId( ).getEventId( ) );
	}

	public void set( InepPackage c )
	{
		setCompanyId( c.getId( ).getCompanyId( ) );
		setEventId( c.getId( ).getId( ) );
	}

	public Integer getCompanyId() {
		return this.companyId;
	}
	public void setCompanyId(Integer usrIdIn) {
		this.companyId = usrIdIn;
	}
	public Integer getSequence() {
		return this.sequence;
	}
	public void setSequence(Integer colSeqIn) {
		this.sequence = colSeqIn;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof InepRevisorPK)) {
			return false;
		}
		InepRevisorPK castOther = (InepRevisorPK)other;
		return getCompanyId( ).equals( castOther.getCompanyId( ) ) && getSequence( ).equals( castOther.getSequence( ) )
				&& getEventId( ).equals( castOther.getEventId( ) );

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + getCompanyId( ).hashCode( );
		hash = hash * prime + getSequence( ).hashCode( );
		hash = hash * prime + getEventId( ).hashCode( );

		return hash;
	}

	public Integer getEventId( )
	{
		return this.eventId;
	}

	public void setEventId( Integer eventId )
	{
		this.eventId = eventId;
	}

	@Override
	public int compareTo( InepRevisorPK o )
	{
		int nRet;

		nRet = getCompanyId( ).compareTo( o.getCompanyId( ) );
		if ( nRet == 0 ) {
			nRet = getEventId( ).compareTo( o.getEventId( ) );
		}
		if ( nRet == 0 ) {
			nRet = getSequence( ).compareTo( o.getSequence( ) );
		}
		return 0;
	}
}