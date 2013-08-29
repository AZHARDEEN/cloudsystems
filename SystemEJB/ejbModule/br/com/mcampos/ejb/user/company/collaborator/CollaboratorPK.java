package br.com.mcampos.ejb.user.company.collaborator;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.com.mcampos.ejb.user.company.Company;

/**
 * The primary key class for the collaborator database table.
 * 
 */
@Embeddable
public class CollaboratorPK implements Serializable, Comparable<CollaboratorPK>
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "usr_id_in", unique = true, nullable = false )
	private Integer companyId;

	@Column( name = "col_seq_in", unique = true, nullable = false )
	private Integer sequence;

	public CollaboratorPK( )
	{
		super( );
	}

	public void set( Company c )
	{
		setCompanyId( c.getId( ) );
	}

	public Integer getCompanyId( )
	{
		return companyId;
	}

	public void setCompanyId( Integer usrIdIn )
	{
		companyId = usrIdIn;
	}

	public Integer getSequence( )
	{
		return sequence;
	}

	public void setSequence( Integer colSeqIn )
	{
		sequence = colSeqIn;
	}

	@Override
	public boolean equals( Object other )
	{
		if( this == other ) {
			return true;
		}
		if( !(other instanceof CollaboratorPK) ) {
			return false;
		}
		CollaboratorPK castOther = (CollaboratorPK) other;
		return companyId.equals( castOther.companyId )
				&& sequence.equals( castOther.sequence );

	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + companyId.hashCode( );
		hash = hash * prime + sequence.hashCode( );

		return hash;
	}

	@Override
	public int compareTo( CollaboratorPK o )
	{
		int nRet;

		nRet = getCompanyId( ).compareTo( o.getCompanyId( ) );
		if( nRet == 0 ) {
			nRet = getSequence( ).compareTo( o.getSequence( ) );
		}
		return nRet;
	}
}