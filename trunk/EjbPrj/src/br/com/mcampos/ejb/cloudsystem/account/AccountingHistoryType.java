package br.com.mcampos.ejb.cloudsystem.account;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "AccountingHistoryType.findAll", query = "select o from AccountingHistoryType o")
})
@Table( name = "\"accounting_history_type\"" )
public class AccountingHistoryType implements Serializable
{
    @Column( name="aht_description_ch", nullable = false )
    private String aht_description_ch;
    @Id
    @Column( name="aht_id_in", nullable = false )
    private Integer aht_id_in;

    public AccountingHistoryType()
    {
    }

    public AccountingHistoryType( String aht_description_ch, Integer aht_id_in )
    {
        this.aht_description_ch = aht_description_ch;
        this.aht_id_in = aht_id_in;
    }

    public String getAht_description_ch()
    {
        return aht_description_ch;
    }

    public void setAht_description_ch( String aht_description_ch )
    {
        this.aht_description_ch = aht_description_ch;
    }

    public Integer getAht_id_in()
    {
        return aht_id_in;
    }

    public void setAht_id_in( Integer aht_id_in )
    {
        this.aht_id_in = aht_id_in;
    }
}
