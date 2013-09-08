package br.com.mcampos.entity.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table( name = "user_document", schema = "public" )
@NamedQueries( { @NamedQuery( name = UserDocument.findDocument, query = "SELECT d FROM UserDocument d WHERE d.code = ?1" ) } )
public class UserDocument implements Serializable, Comparable<UserDocument>
{

	private static final long serialVersionUID = 3433190879624455807L;

	public static final String findDocument = "UserDocument.findDocument";

	public static final int typeCPF = 1;
	public static final int typeIdentity = 2;
	public static final int typeEmail = 6;
	public static final int typeCNPJ = 10;
	public static final int typeAssefaz = 13;

	@EmbeddedId
	private UserDocumentPK id;

	@Column( name = "udc_additional_ch", length = 256 )
	private String additionalInfo;

	@Column( name = "udc_code_ch", nullable = false, length = 128 )
	private String code;

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
	private Users user;

	@ManyToOne
	@JoinColumn( name = "doc_id_in", nullable = false, insertable = false, updatable = false )
	private DocumentType type;

	public UserDocument( )
	{
	}

	public UserDocument( String code, DocumentType type )
	{
		setCode( code );
		setType( type );
	}

	public void setId( UserDocumentPK id )
	{
		this.id = id;
	}

	public UserDocumentPK getId( )
	{
		if ( id == null ) {
			id = new UserDocumentPK( );
		}
		return id;
	}

	public void setAdditionalInfo( String additionalInfo )
	{
		this.additionalInfo = additionalInfo;
	}

	public String getAdditionalInfo( )
	{
		return additionalInfo;
	}

	public void setCode( String code )
	{
		this.code = code;
	}

	public String getCode( )
	{
		return code;
	}

	public void setUser( Users user )
	{
		this.user = user;
		if ( this.user != null ) {
			getId( ).setUserId( this.user.getId( ) );
		}
	}

	public Users getUser( )
	{
		return user;
	}

	public DocumentType getType( )
	{
		return type;
	}

	public void setType( DocumentType type )
	{
		this.type = type;
		if ( getType( ) != null ) {
			getId( ).setDocId( type.getId( ) );
		}
	}

	@Override
	public int compareTo( UserDocument o )
	{
		return getId( ).compareTo( o.getId( ) );
	}

	@Override
	public int hashCode( )
	{
		return getId( ).hashCode( );
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( obj == null || obj instanceof UserDocument == false ) {
			return false;
		}
		UserDocument other = (UserDocument) obj;
		return getId( ).equals( other.getId( ) );
	}

	@Override
	public String toString( )
	{
		return getId( ).toString( ) + " - " + getCode( );
	}
}
