package br.com.mcampos.jpa.user;

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

	public static final int CPF = 1;
	public static final int IDENTITIDADE = 2;
	public static final int EMAIL = 6;
	public static final int CNPJ = 10;
	public static final int ASSEFAZ = 13;
	public static final int INTERNAL_CODE = 8;
	public static final int PASSAPORTE = 8;

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
		this.setCode( code );
		this.setType( type );
	}

	public void setId( UserDocumentPK id )
	{
		this.id = id;
	}

	public UserDocumentPK getId( )
	{
		if ( this.id == null ) {
			this.id = new UserDocumentPK( );
		}
		return this.id;
	}

	public void setAdditionalInfo( String additionalInfo )
	{
		this.additionalInfo = additionalInfo;
	}

	public String getAdditionalInfo( )
	{
		return this.additionalInfo;
	}

	public void setCode( String code )
	{
		this.code = code;
	}

	public String getCode( )
	{
		return this.code;
	}

	public void setUser( Users user )
	{
		this.user = user;
		if ( this.user != null ) {
			this.getId( ).setUserId( this.user.getId( ) );
		}
	}

	public Users getUser( )
	{
		return this.user;
	}

	public DocumentType getType( )
	{
		return this.type;
	}

	public void setType( DocumentType type )
	{
		this.type = type;
		if ( this.getType( ) != null ) {
			this.getId( ).setDocId( type.getId( ) );
		}
	}

	@Override
	public int compareTo( UserDocument o )
	{
		return this.getId( ).compareTo( o.getId( ) );
	}

	@Override
	public int hashCode( )
	{
		return this.getId( ).hashCode( );
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( obj == null || obj instanceof UserDocument == false ) {
			return false;
		}
		UserDocument other = (UserDocument) obj;
		return this.getId( ).equals( other.getId( ) );
	}

	@Override
	public String toString( )
	{
		return this.getId( ).toString( ) + " - " + this.getCode( );
	}
}
