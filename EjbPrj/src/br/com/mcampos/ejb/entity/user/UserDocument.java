package br.com.mcampos.ejb.entity.user;

import br.com.mcampos.ejb.entity.user.attributes.DocumentType;


import br.com.mcampos.ejb.entity.user.pk.UserDocumentPK;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "UserDocument.findAll", query = "select o from UserDocument o"),
  @NamedQuery(name = "UserDocument.findByDocument", query = "select o from UserDocument o where o.code = :document"),
  @NamedQuery(name = "UserDocument.findDocument", query = "select o from UserDocument o where o.code = :document and o.documentType.id = :docType")
})
@Table( name = "\"user_document\"" )
@IdClass( UserDocumentPK.class )
public class UserDocument implements Serializable, Comparable<UserDocument>
{

	public static final int typeCPF = 1;
	public static final int typeIdentity = 2;
	public static final int typeEmail = 6;


    private Integer userId;
    private Integer documentId;
    private String code;
    private String additionalInfo;
    private Users users;


    private DocumentType documentType;

    public UserDocument()
    {
        super ();
    }

    public UserDocument( String code, String additionalInfo,
                         DocumentType documentType )
    {
        super();
        init ( code, additionalInfo, documentType );
    }
    
    public void init( String code, String additionalInfo,
                         DocumentType documentType )
    {
		setDocumentType( documentType );
        setCode( code );
        setAdditionalInfo( additionalInfo );
    }



    @Id
    @Column( name="doc_id_in", nullable = false, insertable = false, updatable = false )
    public Integer getDocumentId()
    {
        return documentId;
    }

    public void setDocumentId( Integer doc_id_in )
    {
        this.documentId = doc_id_in;
    }

    @Column( name="udc_additional_ch" )
    public String getAdditionalInfo()
    {
        return additionalInfo;
    }

    public void setAdditionalInfo( String udc_additional_ch )
    {
        this.additionalInfo = udc_additional_ch;
    }

    @Column( name="udc_code_ch", nullable = false )
    public String getCode()
    {
        return code;
    }

	public void setCode ( String document )
	{
		this.code = document;
	}

    @Id
    @Column( name="usr_id_in", nullable = false, insertable = false, updatable = false )
    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId( Integer userId )
    {
        this.userId = userId;
    }

    @ManyToOne
    @JoinColumn( name = "usr_id_in", updatable = false, nullable = false )
    public Users getUsers()
    {
        return users;
    }

    public void setUsers( Users users )
    {
        this.users = users;
        if (users != null) {
            this.userId = users.getId();
        }
    }


    public void setDocumentType( DocumentType documentType )
    {
	    this.documentType = documentType;
        if ( this.documentType != null )
            setDocumentId( this.documentType.getId() );
    }

    @ManyToOne
    @JoinColumn (name = "doc_id_in", referencedColumnName = "doc_id_in", nullable = false )
    public DocumentType getDocumentType()
    {
        return documentType;
    }


    public int compareTo( UserDocument o )
    {
        int nRet;
        
        nRet = getDocumentId().compareTo( o.getDocumentId() );
        if ( nRet != 0 )
            return nRet;
        return getUserId().compareTo( o.getUserId() );
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj instanceof UserDocument ) {
            UserDocument o = ( UserDocument ) obj;
            
            return ( 
                    getDocumentId().equals( o.getDocumentId() ) &&
                    getUserId().equals( o.getUserId() )
                ) ;
        }
        else 
            return false;
    }
}