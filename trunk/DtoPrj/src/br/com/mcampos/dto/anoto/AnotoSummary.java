package br.com.mcampos.dto.anoto;

import java.io.Serializable;

public class AnotoSummary implements Serializable, Comparable<AnotoSummary>
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5813174692670997478L;
	private Integer pgc = 0;
    private Integer foto = 0;

    private Integer prepago = 0;
    private Integer pospago = 0;
    private Integer emptyType = 0;

    private Integer dinheiro = 0;
    private Integer boleto = 0;
    private Integer di = 0;
    private Integer emptyPayment = 0;

    private Integer pap = 0;
    private Integer cvm = 0;
    private Integer emptyCategory = 0;

    private Integer fend = 0;
    private Integer rejeitadoCep = 0;
    private Integer rejeitadoCredito = 0;
    private Integer emptySituation = 0;


    public AnotoSummary()
    {
        super();
    }

    public void add( AnotoSummary sum )
    {
        if ( sum == null )
            return;

        this.prepago += sum.getPrepago();
        this.pospago += sum.getPospago();
        this.emptyType += sum.getEmptyType();

        this.dinheiro += sum.getDinheiro();
        this.boleto += sum.getBoleto();
        this.di += sum.getDi();
        this.emptyPayment += sum.getEmptyPayment();

        this.pap += sum.getPap();
        this.cvm += sum.getCvm();
        this.emptyCategory += sum.getEmptyCategory();

        this.fend += sum.getFend();
        this.rejeitadoCep += sum.getRejeitadoCep();
        this.rejeitadoCredito += sum.getRejeitadoCredito();
        this.emptySituation += sum.getEmptySituation();
    }


    public void setPgc( Integer pgcId )
    {
        this.pgc = pgcId;
    }

    public Integer getPgc()
    {
        return pgc;
    }

    public void setFoto( Integer foto )
    {
        this.foto = foto;
    }

    public Integer getFoto()
    {
        return foto;
    }

    public Integer getSemFoto()
    {
        Integer total = 0;
        try {
            total = getPgc() - getFoto();
        }
        catch ( Exception e ) {
            total = 0;
        }
        return total;
    }

    public void setPrepago( Integer prepago )
    {
        this.prepago = prepago;
    }

    public Integer getPrepago()
    {
        return prepago;
    }

    public void setDinheiro( Integer dinheiro )
    {
        this.dinheiro = dinheiro;
    }

    public Integer getDinheiro()
    {
        return dinheiro;
    }

    public void setBoleto( Integer boleto )
    {
        this.boleto = boleto;
    }

    public Integer getBoleto()
    {
        return boleto;
    }

    public void setDi( Integer DI )
    {
        this.di = DI;
    }

    public Integer getDi()
    {
        return di;
    }

    public void setPap( Integer PAP )
    {
        this.pap = PAP;
    }

    public Integer getPap()
    {
        return pap;
    }

    public void setCvm( Integer CVM )
    {
        this.cvm = CVM;
    }

    public Integer getCvm()
    {
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
        pap++;
    }

    public void addCVM()
    {
        cvm++;
    }

    public void addDI()
    {
        di++;
    }

    public void addDinheiro()
    {
        dinheiro++;
    }

    public void addBoleto()
    {
        boleto++;
    }

    public void addPrepago()
    {
        prepago++;
    }

    public void addFoto()
    {
        foto++;
    }

    public void addFend()
    {
        fend++;
    }

    public void addRejeitadoCredito()
    {
        rejeitadoCredito++;
    }

    public void addRejeitadoZip()
    {
        rejeitadoCep++;
    }

    public void setFend( Integer fend )
    {
        this.fend = fend;
    }

    public Integer getFend()
    {
        return fend;
    }

    public void addPospago()
    {
        pospago++;
    }

    public void setRejeitadoCep( Integer rejeitadoCep )
    {
        this.rejeitadoCep = rejeitadoCep;
    }

    public Integer getRejeitadoCep()
    {
        return rejeitadoCep;
    }

    public void setRejeitadoCredito( Integer rejeitadoCredito )
    {
        this.rejeitadoCredito = rejeitadoCredito;
    }

    public Integer getRejeitadoCredito()
    {
        return rejeitadoCredito;
    }

    public void setPospago( Integer pospago )
    {
        this.pospago = pospago;
    }

    public Integer getPospago()
    {
        return pospago;
    }

    public void setEmptyType( Integer emptyType )
    {
        this.emptyType = emptyType;
    }

    public Integer getEmptyType()
    {
        return emptyType;
    }

    public void addEmptyType()
    {
        emptyType++;
    }

    public void setEmptyPayment( Integer emptyPayment )
    {
        this.emptyPayment = emptyPayment;
    }

    public Integer getEmptyPayment()
    {
        return emptyPayment;
    }

    public void addEmptyPayment()
    {
        emptyPayment++;
    }

    public void setEmptyCategory( Integer emptyCategory )
    {
        this.emptyCategory = emptyCategory;
    }

    public Integer getEmptyCategory()
    {
        return emptyCategory;
    }

    public void addEmptyCategory()
    {
        emptyCategory++;
    }

    public void setEmptySituation( Integer emptySituation )
    {
        this.emptySituation = emptySituation;
    }

    public Integer getEmptySituation()
    {
        return emptySituation;
    }

    public void addEmptySituation()
    {
        emptySituation++;
    }
}
