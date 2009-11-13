package br.com.mcampos.ejb.entity.login;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "AccessLogType.findAll", query = "select o from AccessLogType o")
})
@Table( name = "\"access_log_type\"" )
public class AccessLogType implements Serializable
{
    public static int accessLogTypeNormalLogin = 1;
    public static int accessLogTypeCreateLogin = 2;
    public static int accessLogTypeLogout = 3;
    
    
    @Column( name="alt_description_ch", nullable = false )
    private String description;
    
    
    @Id
    @Column( name="alt_id_in", nullable = false )
    private Integer id;

    public AccessLogType()
    {
    }

    public AccessLogType( Integer alt_id_in, String alt_description_id )
    {
        setDescription( alt_description_id );
        setId ( alt_id_in );
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String alt_description_id )
    {
        this.description = alt_description_id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer alt_id_in )
    {
        this.id = alt_id_in;
    }
}
