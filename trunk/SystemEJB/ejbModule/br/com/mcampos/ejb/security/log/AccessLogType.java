package br.com.mcampos.ejb.security.log;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleTable;


@Entity
@NamedQueries( { @NamedQuery( name = "AccessLogType.findAll", query = "select o from AccessLogType o" ) } )
@Table( name = "\"access_log_type\"" )
public class AccessLogType extends SimpleTable<AccessLogType>
{
	private static final long serialVersionUID = 719719694474136842L;
	public static final int accessLogTypeNormalLogin = 1;
	public static final int accessLogTypeCreateLogin = 2;
	public static final int accessLogTypeLogout = 3;


	@Column( name = "alt_description_ch", nullable = false )
	private String description;

	@Id
	@Column( name = "alt_id_in", nullable = false )
	private Integer id;


	@Override
	public String getDescription()
	{
		return this.description;
	}

	@Override
	public void setDescription( String description )
	{
		this.description = description;
	}

	@Override
	public Integer getId()
	{
		return this.id;
	}

	@Override
	public void setId( Integer id )
	{
		this.id = id;
	}

	@Override
	public String toString()
	{
		return getDescription();
	}
}
