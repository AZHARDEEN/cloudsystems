package br.com.mcampos.ejb.entity.system;

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
  @NamedQuery(name = "EMailPart.findAll", query = "select o from EMailPart o")
})
@Table( name = "\"e_mail_part\"" )
@IdClass( EMailPartPK.class )
public class EMailPart implements Serializable
{
    private Integer id;
    private Integer sequencial;
    private String content;
    private EMailPartType partType;
    private EMail EMail;

    public EMailPart()
    {
    }

    public EMailPart( EMail EMail, EMailPartType EMailPartType, Integer emp_seq_in, String emp_text_tx )
    {
        this.EMail = EMail;
        this.partType = EMailPartType;
        this.sequencial = emp_seq_in;
        this.content = emp_text_tx;
    }

    @Id
    @Column( name="eml_id_in", nullable = false, insertable = false, updatable = false )
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer eml_id_in )
    {
        this.id = eml_id_in;
    }


    @Id
    @Column( name="emp_seq_in", nullable = false )
    public Integer getSequencial()
    {
        return sequencial;
    }

    public void setSequencial( Integer emp_seq_in )
    {
        this.sequencial = emp_seq_in;
    }

    @Column( name="emp_text_tx", nullable = false )
    public String getContent()
    {
        return content;
    }

    public void setContent( String emp_text_tx )
    {
        this.content = emp_text_tx;
    }

    @ManyToOne
    @JoinColumn( name = "emp_id_in" )
    public EMailPartType getPartType()
    {
        return partType;
    }

    public void setPartType( EMailPartType EMailPartType )
    {
        this.partType = EMailPartType;
    }

    @ManyToOne
    @JoinColumn( name = "eml_id_in" )
    public EMail getEMail()
    {
        return EMail;
    }

    public void setEMail( EMail EMail )
    {
        this.EMail = EMail;
        if (EMail != null) {
            this.id = EMail.getId();
        }
    }
}
