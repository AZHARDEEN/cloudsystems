package br.com.mcampos.ejb.cloudsystem.typing.iab.entity;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@NamedQueries( { @NamedQuery( name = "DadosIab.findAll", query = "select o from DadosIab o" ),
                 @NamedQuery( name = DadosIab.getById,
                              query = "select o from DadosIab o where o.id2 = ?1 and o.date_1 is not null" ),
                 @NamedQuery( name = DadosIab.getById2,
                              query = "select o from DadosIab o where o.id2 = ?1 and o.date_2 is not null" ),
                 @NamedQuery( name = DadosIab.getProblems,
                              query = "select o from DadosIab o where o.type_status not in ( 3, 4 ) order by o.id2" ) } )
@Table( name = "dados_iab" )
public class DadosIab implements Serializable
{
    public static final String getById = "DadosIab.getById";
    public static final String getById2 = "DadosIab.getById2";
    public static final String getProblems = "DadosIab.getProblems";

    @Column( name = "ano_corrigido" )
    private String ano_corrigido;

    @Column( name = "cit_id_in" )
    private Integer cit_id_in;

    @Column( name = "data_nascimento" )
    @Temporal( TemporalType.DATE )
    private Date data_nascimento;

    @Column( name = "date_1" )
    @Temporal( TemporalType.TIMESTAMP )
    private Date date_1;

    @Column( name = "date_2" )
    @Temporal( TemporalType.TIMESTAMP )
    private Date date_2;

    @Column( name = "escola" )
    private String escola;

    @Id
    @SequenceGenerator( name = "iabGenerator", sequenceName = "seq_iab", allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "iabGenerator" )
    @Column( name = "id", nullable = false )
    private Integer id;

    @Column( name = "inicial_professor" )
    private String inicial_professor;

    @Column( name = "id2" )
    private String id2;

    @Column( name = "municipio" )
    private String municipio;

    @Column( name = "nome" )
    private String nome;

    @Column( name = "nota1" )
    private Integer nota1;

    @Column( name = "nota2" )
    private Integer nota2;

    @Column( name = "nota3" )
    private Integer nota3;

    @Column( name = "nota4" )
    private Integer nota4;

    @Column( name = "qtd_classe" )
    private Integer qtd_classe;

    @Column( name = "qtd_teste" )
    private Integer qtd_teste;

    @Column( name = "sexo" )
    private String sexo;

    @Column( name = "status" )
    private String status;

    @Column( name = "turno" )
    private String turno;

    @Column( name = "type_status" )
    private Integer type_status;

    @Column( name = "typer_1" )
    private Integer typer_1;

    @Column( name = "typer_2" )
    private Integer typer_2;

    @Column( name = "uf" )
    private String uf;

    @Column( name = "urbano_rural" )
    private String urbano_rural;

    public DadosIab()
    {
    }

    public String getAno_corrigido()
    {
        if ( ano_corrigido == null )
            ano_corrigido = "";
        return ano_corrigido;
    }

    public void setAno_corrigido( String ano_corrigido )
    {
        this.ano_corrigido = ano_corrigido;
    }

    public Integer getCit_id_in()
    {
        return cit_id_in;
    }

    public void setCit_id_in( Integer cit_id_in )
    {
        this.cit_id_in = cit_id_in;
    }

    public Date getData_nascimento()
    {
        return data_nascimento;
    }

    public void setData_nascimento( Date data_nascimento )
    {
        this.data_nascimento = data_nascimento;
    }

    public Date getDate_1()
    {
        return date_1;
    }

    public void setDate_1( Date date_1 )
    {
        this.date_1 = date_1;
    }

    public Date getDate_2()
    {
        return date_2;
    }

    public void setDate_2( Date date_2 )
    {
        this.date_2 = date_2;
    }

    public String getEscola()
    {
        return escola;
    }

    public void setEscola( String escola )
    {
        this.escola = escola;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getInicial_professor()
    {
        return inicial_professor;
    }

    public void setInicial_professor( String inicial_professor )
    {
        this.inicial_professor = inicial_professor;
    }

    public String getMunicipio()
    {
        return municipio;
    }

    public void setMunicipio( String municipio )
    {
        this.municipio = municipio;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome( String nome )
    {
        this.nome = nome;
    }

    public Integer getNota1()
    {
        return nota1;
    }

    public void setNota1( Integer nota1 )
    {
        this.nota1 = nota1;
    }

    public Integer getNota2()
    {
        return nota2;
    }

    public void setNota2( Integer nota2 )
    {
        this.nota2 = nota2;
    }

    public Integer getNota3()
    {
        return nota3;
    }

    public void setNota3( Integer nota3 )
    {
        this.nota3 = nota3;
    }

    public Integer getNota4()
    {
        return nota4;
    }

    public void setNota4( Integer nota4 )
    {
        this.nota4 = nota4;
    }

    public Integer getQtd_classe()
    {
        return qtd_classe;
    }

    public void setQtd_classe( Integer qtd_classe )
    {
        this.qtd_classe = qtd_classe;
    }

    public Integer getQtd_teste()
    {
        return qtd_teste;
    }

    public void setQtd_teste( Integer qtd_teste )
    {
        this.qtd_teste = qtd_teste;
    }

    public String getSexo()
    {
        return sexo;
    }

    public void setSexo( String sexo )
    {
        this.sexo = sexo;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus( String status )
    {
        this.status = status;
    }

    public String getTurno()
    {
        return turno;
    }

    public void setTurno( String turno )
    {
        this.turno = turno;
    }

    public Integer getType_status()
    {
        return type_status;
    }

    public void setType_status( Integer type_status )
    {
        this.type_status = type_status;
    }

    public Integer getTyper_1()
    {
        return typer_1;
    }

    public void setTyper_1( Integer typer_1 )
    {
        this.typer_1 = typer_1;
    }

    public Integer getTyper_2()
    {
        return typer_2;
    }

    public void setTyper_2( Integer typer_2 )
    {
        this.typer_2 = typer_2;
    }

    public String getUf()
    {
        return uf;
    }

    public void setUf( String uf )
    {
        this.uf = uf;
    }

    public String getUrbano_rural()
    {
        return urbano_rural;
    }

    public void setUrbano_rural( String urbano_rural )
    {
        this.urbano_rural = urbano_rural;
    }

    public void setId2( String id2 )
    {
        this.id2 = id2;
    }

    public String getId2()
    {
        return id2;
    }

    public boolean isEqual( DadosIab other )
    {
        if ( !getId2().equals( other.getId2() ) )
            return false;

        if ( !getAno_corrigido().equals( other.getAno_corrigido() ) )
            return false;
        if ( !getCit_id_in().equals( other.getCit_id_in() ) )
            return false;
        if ( !getData_nascimento().equals( other.getData_nascimento() ) )
            return false;
        if ( !getEscola().equals( other.getEscola() ) )
            return false;
        if ( !getInicial_professor().equals( other.getInicial_professor() ) )
            return false;
        if ( !getNome().equals( other.getNome() ) )
            return false;
        if ( !getNota1().equals( other.getNota1() ) )
            return false;
        if ( !getNota2().equals( other.getNota2() ) )
            return false;
        if ( !getNota3().equals( other.getNota3() ) )
            return false;
        if ( !getNota4().equals( other.getNota4() ) )
            return false;
        if ( !getSexo().equals( other.getSexo() ) )
            return false;
        if ( !getTurno().equals( other.getTurno() ) )
            return false;
        if ( !getUrbano_rural().equals( other.getUrbano_rural() ) )
            return false;
        return true;
    }
}
