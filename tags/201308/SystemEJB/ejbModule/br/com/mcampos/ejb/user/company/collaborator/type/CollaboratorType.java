package br.com.mcampos.ejb.user.company.collaborator.type;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the collaborator_type database table.
 * 
 */
@Entity
@Table( name = "collaborator_type" )
public class CollaboratorType implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final Integer typeEmployee = 2;
	public static final Integer typeManager = 4;
	public static final Integer typeDirector = 3;
	public static final Integer typeAdministrator = 1;

	@Id
	@Column( name = "clt_id_in", unique = true, nullable = false )
	private Integer id;

	@Column( name = "clt_description_ch", nullable = false, length = 32 )
	private String description;

	@Column( name = "clt_inherit_role_bt" )
	private Boolean cltInheritRoleBt;

	public CollaboratorType( )
	{
	}

	public Integer getId( )
	{
		return this.id;
	}

	public void setId( Integer cltIdIn )
	{
		this.id = cltIdIn;
	}

	public String getDescription( )
	{
		return this.description;
	}

	public void setDescription( String cltDescriptionCh )
	{
		this.description = cltDescriptionCh;
	}

	public Boolean getCltInheritRoleBt( )
	{
		return this.cltInheritRoleBt;
	}

	public void setCltInheritRoleBt( Boolean cltInheritRoleBt )
	{
		this.cltInheritRoleBt = cltInheritRoleBt;
	}

}