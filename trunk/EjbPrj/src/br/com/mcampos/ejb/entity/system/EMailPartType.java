package br.com.mcampos.ejb.entity.system;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = "EMailPartType.findAll", query = "select o from EMailPartType o" ) } )
@Table( name = "e_mail_part_type" )
public class EMailPartType implements Serializable
{
    @Column( name = "emp_description_ch", nullable = false )
    private String description;
    @Id
    @Column( name = "emp_id_in", nullable = false )
    private Integer id;

    public static final int partSubject = 1;
    public static final int partBody = 2;

    public EMailPartType()
    {
    }

    public EMailPartType( String emp_description_ch, Integer emp_id_in )
    {
        this.description = emp_description_ch;
        this.id = emp_id_in;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String emp_description_ch )
    {
        this.description = emp_description_ch;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer emp_id_in )
    {
        this.id = emp_id_in;
    }
}
