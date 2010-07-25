package br.com.mcampos.dto.anoto;

import java.io.Serializable;

public class AnotoSummary implements Serializable, Comparable<AnotoSummary>
{
    private Integer pgc;
    private Integer foto;
    private Integer prepago;

    private Integer dinheiro;
    private Integer boleto;
    private Integer di;

    private Integer pap;
    private Integer cvm;

    private Integer fend;
    private Integer rejeitadoCep;
    private Integer rejeitadoCredito;


    public AnotoSummary()
    {
        super();
    }


    public void setPgc( Integer pgcId )
    {
        this.pgc = pgcId;
    }

    public Integer getPgc()
    {
        if ( pgc == null )
            pgc = 0;
        return pgc;
    }

    public void setFoto( Integer foto )
    {
        this.foto = foto;
    }

    public Integer getFoto()
    {
        if ( foto == null )
            foto = 0;
        return foto;
    }

    public void setPrepago( Integer prepago )
    {
        this.prepago = prepago;
    }

    public Integer getPrepago()
    {
        if ( prepago == null )
            prepago = 0;
        return prepago;
    }

    public void setDinheiro( Integer dinheiro )
    {
        this.dinheiro = dinheiro;
    }

    public Integer getDinheiro()
    {
        if ( dinheiro == null )
            dinheiro = 0;
        return dinheiro;
    }

    public void setBoleto( Integer boleto )
    {
        this.boleto = boleto;
    }

    public Integer getBoleto()
    {
        if ( boleto == null )
            boleto = 0;
        return boleto;
    }

    public void setDi( Integer DI )
    {
        this.di = DI;
    }

    public Integer getDi()
    {
        if ( di == null )
            di = 0;
        return di;
    }

    public void setPap( Integer PAP )
    {
        this.pap = PAP;
    }

    public Integer getPap()
    {
        if ( pap == null )
            pap = 0;
        return pap;
    }

    public void setCvm( Integer CVM )
    {
        this.cvm = CVM;
    }

    public Integer getCvm()
    {
        if ( cvm == null )
            cvm = 0;
        return cvm;
    }

    public int compareTo( AnotoSummary o )
    {
        return getPgc().compareTo( o.getPgc() );
    }

    @Override
    public boolean equals( Object obj )
    {
        return getPgc().equals( ( ( AnotoSummary )obj ).getPgc() );
    }

    public void addPAP()
    {
        if ( pap == null )
            pap = 0;
        pap++;
    }

    public void addCVM()
    {
        if ( cvm == null )
            cvm = 0;
        cvm++;
    }

    public void addDI()
    {
        if ( di == null )
            di = 0;
        di++;
    }

    public void addDinheiro()
    {
        if ( dinheiro == null )
            dinheiro = 0;
        dinheiro++;
    }

    public void addBoleto()
    {
        if ( boleto == null )
            boleto = 0;
        boleto++;
    }

    public void addPrepago()
    {
        if ( prepago == null )
            prepago = 0;
        prepago++;
    }

    public void addFoto()
    {
        if ( foto == null )
            foto = 0;
        foto++;
    }

    public void addFend()
    {
        if ( fend == null )
            fend = 0;
        fend++;
    }

    public void addRejeitadoCredito()
    {
        if ( rejeitadoCredito == null )
            rejeitadoCredito = 0;
        rejeitadoCredito++;
    }

    public void addRejeitadoZip()
    {
        if ( rejeitadoCep == null )
            rejeitadoCep = 0;
        rejeitadoCep++;
    }

    public void setFend( Integer fend )
    {
        this.fend = fend;
    }

    public Integer getFend()
    {
        if ( fend == null )
            fend = 0;
        return fend;
    }

    public void setRejeitadoCep( Integer rejeitadoCep )
    {
        this.rejeitadoCep = rejeitadoCep;
    }

    public Integer getRejeitadoCep()
    {
        if ( rejeitadoCep == null )
            rejeitadoCep = 0;
        return rejeitadoCep;
    }

    public void setRejeitadoCredito( Integer rejeitadoCredito )
    {
        this.rejeitadoCredito = rejeitadoCredito;
    }

    public Integer getRejeitadoCredito()
    {
        if ( rejeitadoCredito == null )
            rejeitadoCredito = 0;
        return rejeitadoCredito;
    }
}
