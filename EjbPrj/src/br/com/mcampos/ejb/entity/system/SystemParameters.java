package br.com.mcampos.ejb.entity.system;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "SystemParameters.findAll", query = "select o from SystemParameters o")
})
@Table( name = "\"system_parameters\"" )
public class SystemParameters implements Serializable {

    private String description;
    private String id;
    private String value;
    
    public static final String passwordValidDays = "PasswordValidDays";
    public static final String maxLoginTryCount = "MaxLoginTryCount";

    public SystemParameters() {
    }

    public SystemParameters( String id, String description, String value ) {
        setDescription ( description );
        setId ( id );
        setValue ( value );
    }

    @Column( name="spr_description_ch", nullable = false )
    public String getDescription() {
        return description;
    }

    public void setDescription( String spr_description_ch ) {
        this.description = spr_description_ch;
    }

    @Id
    @Column( name="spr_id_ch", nullable = false )
    public String getId() {
        return id;
    }

    public void setId( String spr_id_ch ) {
        this.id = spr_id_ch;
    }

    @Column( name="spr_value_tx" )
    public String getValue() {
        return value;
    }

    public void setValue( String spr_value_tx ) {
        this.value = spr_value_tx;
    }

}
