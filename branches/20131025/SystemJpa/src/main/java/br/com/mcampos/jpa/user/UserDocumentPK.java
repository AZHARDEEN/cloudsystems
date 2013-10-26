package br.com.mcampos.jpa.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserDocumentPK implements Serializable, Comparable<UserDocumentPK>
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "usr_id_in", unique = true, nullable = false )
	private Integer userId;

	@Column( name = "doc_id_in", unique = true, nullable = false )
	private Integer docId;

	public UserDocumentPK( )
	{
	}

	public Integer getUserId( )
	{
		if ( this.userId == null ) {
			this.userId = 0;
		}
		return this.userId;
	}

	public void setUserId( Integer usrIdIn )
	{
		this.userId = usrIdIn;
	}

	public Integer getDocId( )
	{
		return this.docId;
	}

	public void setDocId( Integer docIdIn )
	{
		this.docId = docIdIn;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( !( other instanceof UserDocumentPK ) ) {
			return false;
		}
		UserDocumentPK castOther = (UserDocumentPK) other;
		return getUserId( ).equals( castOther.userId ) && getDocId( ).equals( castOther.docId );

	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + getUserId( ).hashCode( );
		hash = hash * prime + getDocId( ).hashCode( );

		return hash;
	}

	@Override
	public int compareTo( UserDocumentPK o )
	{
		int nRet = getUserId( ).compareTo( o.getUserId( ) );
		if ( nRet == 0 ) {
			nRet = getDocId( ).compareTo( o.getDocId( ) );
		}
		return nRet;
	}

	@Override
	public String toString( )
	{
		return "User: " + getUserId( ).toString( ) + " - Doc: " + getDocId( ).toString( );
	}
}
