package br.com.mcampos.ejb.entity.system;

import java.io.Serializable;

public class EMailPartPK implements Serializable
{
    private Integer eml_id_in;
    private Integer emp_seq_in;

    public EMailPartPK()
    {
    }

    public EMailPartPK( Integer eml_id_in, Integer emp_seq_in )
    {
        this.eml_id_in = eml_id_in;
        this.emp_seq_in = emp_seq_in;
    }

    public boolean equals( Object other )
    {
        if (other instanceof EMailPartPK) {
            final EMailPartPK otherEMailPartPK = (EMailPartPK) other;
            final boolean areEqual = (otherEMailPartPK.eml_id_in.equals(eml_id_in) && otherEMailPartPK.emp_seq_in.equals(emp_seq_in));
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getEml_id_in()
    {
        return eml_id_in;
    }

    void setEml_id_in( Integer eml_id_in )
    {
        this.eml_id_in = eml_id_in;
    }

    Integer getEmp_seq_in()
    {
        return emp_seq_in;
    }

    void setEmp_seq_in( Integer emp_seq_in )
    {
        this.emp_seq_in = emp_seq_in;
    }
}
