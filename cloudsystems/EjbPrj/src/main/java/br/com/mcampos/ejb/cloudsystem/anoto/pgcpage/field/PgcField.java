package br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.ejb.cloudsystem.EntityCopyInterface;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPage;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.cloudsystem.system.fieldtype.entity.FieldType;
import br.com.mcampos.sysutils.SysUtils;

@Entity
@NamedQueries( {
		@NamedQuery( name = PgcField.findAll, query = "select o from PgcField o" ),
		@NamedQuery( name = PgcField.findPageFields,
				query = "select o from PgcField o where o.pgcPage = ?1 order by o.sequence" ),
		@NamedQuery( name = PgcField.findBookFields,
				query = "select o from PgcField o where o.pgcId = ?1 and o.bookId = ?2 order by o.sequence" ),
		@NamedQuery( name = PgcField.getFieldsType,
				query = "select o from PgcField o where o.pgcPage.pgc = ?1 and o.name in ('Plano Pos Combo via LPF', 'Plano Pos Combo via 30', 'Plano Pos 30', 'Plano Pos Shine LPF', 'Plano Pos LPF', 'Plano Pre 35', 'Plano Pre 15') and o.hasPenstrokes = true " ),
		@NamedQuery( name = PgcField.getFieldsCategory,
				query = "select o from PgcField o where o.pgcPage.pgc = ?1 and o.name in ('PAP', 'CVM') and o.hasPenstrokes = true " ),
		@NamedQuery( name = PgcField.getFieldsPayment,
				query = "select o from PgcField o where o.pgcPage.pgc = ?1 and o.name in ('Boleto_Bancario', 'Deposito_Identificado', 'Dinheiro') and o.hasPenstrokes = true " ),
		@NamedQuery( name = PgcField.getFieldsStatus,
				query = "select o from PgcField o where o.pgcPage.pgc = ?1 and o.name in ('Venda rejeitada por Análise Credito', 'Venda rejeitada por CEP inválido', 'Venda cadastrada FEND') and o.hasPenstrokes = true " ),
		@NamedQuery( name = PgcField.getFields,
				query = "select o from PgcField o where o.pgcPage.pgc = ?1 and o.name in (?2) and o.hasPenstrokes = true " ) } )
@Table( name = "pgc_field" )
@IdClass( PgcFieldPK.class )
public class PgcField implements Serializable, EntityCopyInterface<PgcFieldDTO>
{
	public static final String findAll = "PgcField.findAll";
	public static final String findPageFields = "PgcField.findPageFields";
	public static final String findBookFields = "PgcField.findBookFields";
	public static final String getFields = "PgcField.getFields";
	public static final String getFieldsType = "PgcField.getFieldsType";
	public static final String getFieldsCategory = "PgcField.getFieldsCategory";
	public static final String getFieldsPayment = "PgcField.getFieldsPayment";
	public static final String getFieldsStatus = "PgcField.getFieldsStatus";

	@Column( name = "med_id_in" )
	private Integer mediaId;

	@Id
	@Column( name = "ppg_book_id", nullable = false )
	private Integer bookId;

	@Column( name = "pfl_icr_tx" )
	private String icrText;

	@Id
	@Column( name = "pfl_name_ch", nullable = false )
	private String name;

	@Id
	@Column( name = "ppg_page_id", nullable = false )
	private Integer pageId;

	@Column( name = "pfl_revised_tx" )
	private String revisedText;

	@Id
	@Column( name = "pgc_id_in", nullable = false )
	private Integer pgcId;

	@ManyToOne
	@JoinColumn( name = "flt_id_in", referencedColumnName = "flt_id_in", nullable = false )
	private FieldType type;

	@Column( name = "pfl_start_time_in" )
	private Long startTime;

	@Column( name = "pfl_end_time_in" )
	private Long endTime;

	@Column( name = "pfl_has_penstrokes_bt" )
	private Boolean hasPenstrokes;

	@Column( name = "pfl_sequence_in" )
	private Integer sequence;

	@ManyToOne
	@JoinColumn( name = "med_id_in", referencedColumnName = "med_id_in", insertable = false, updatable = false )
	private Media media;

	@ManyToOne
	@JoinColumns( {
			@JoinColumn( name = "pgc_id_in", referencedColumnName = "pgc_id_in", insertable = false, updatable = false ),
			@JoinColumn( name = "ppg_book_id", referencedColumnName = "ppg_book_id", insertable = false, updatable = false ),
			@JoinColumn( name = "ppg_page_id", referencedColumnName = "ppg_page_id", insertable = false, updatable = false )
	} )
	private PgcPage pgcPage;

	public PgcField( )
	{
	}

	public Integer getMediaId( )
	{
		return this.mediaId;
	}

	public void setMediaId( Integer med_id_in )
	{
		this.mediaId = med_id_in;
	}

	public Integer getBookId( )
	{
		return this.bookId;
	}

	public void setBookId( Integer pfl_book_id )
	{
		this.bookId = pfl_book_id;
	}

	public String getIcrText( )
	{
		return this.icrText;
	}

	public void setIcrText( String pfl_icr_tx )
	{
		this.icrText = pfl_icr_tx;
	}

	public String getName( )
	{
		return this.name;
	}

	public void setName( String pfl_name_ch )
	{
		this.name = pfl_name_ch;
	}

	public Integer getPageId( )
	{
		return this.pageId;
	}

	public void setPageId( Integer pfl_page_id )
	{
		this.pageId = pfl_page_id;
	}

	public String getRevisedText( )
	{
		return this.revisedText;
	}

	public void setRevisedText( String pfl_revised_tx )
	{
		this.revisedText = pfl_revised_tx;
	}

	public Integer getPgcId( )
	{
		return this.pgcId;
	}

	public void setPgcId( Integer pgc_id_in )
	{
		this.pgcId = pgc_id_in;
	}

	public void setMedia( Media media )
	{
		this.media = media;
		if( media != null ) {
			this.setMediaId( media.getId( ) );
		}
	}

	public Media getMedia( )
	{
		return this.media;
	}

	public void setHasPenstrokes( Boolean pfl_has_penstrokes_bt )
	{
		this.hasPenstrokes = pfl_has_penstrokes_bt;
	}

	public Boolean getHasPenstrokes( )
	{
		return this.hasPenstrokes;
	}

	public void setType( FieldType type )
	{
		this.type = type;
	}

	public FieldType getType( )
	{
		return this.type;
	}

	public void setPgcPage( PgcPage pgcPage )
	{
		this.pgcPage = pgcPage;
		if( pgcPage != null ) {
			this.setBookId( pgcPage.getBookId( ) );
			this.setPageId( pgcPage.getPageId( ) );
			this.setPgcId( pgcPage.getPgcId( ) );
		}
	}

	public PgcPage getPgcPage( )
	{
		return this.pgcPage;
	}

	public void setStartTime( Long startTime )
	{
		this.startTime = startTime;
	}

	public Long getStartTime( )
	{
		return this.startTime;
	}

	public void setEndTime( Long endTime )
	{
		this.endTime = endTime;
	}

	public Long getEndTime( )
	{
		return this.endTime;
	}

	@Override
	public PgcFieldDTO toDTO( )
	{
		PgcFieldDTO dto = new PgcFieldDTO( this.getPgcPage( ).toDTO( ) );
		dto.setEndTime( this.getEndTime( ) );
		dto.setHasPenstrokes( this.getHasPenstrokes( ) );
		dto.setIrcText( this.getIcrText( ) );
		if( this.getMedia( ) != null ) {
			dto.setMedia( this.getMedia( ).toDTO( ) );
		}
		dto.setName( this.getName( ) );
		dto.setRevisedText( this.getRevisedText( ) );
		dto.setStartTime( this.getStartTime( ) );
		if( this.getType( ) != null ) {
			dto.setType( this.getType( ).toDTO( ) );
		}
		dto.setSequence( this.getSequence( ) );
		return dto;
	}

	public void setSequence( Integer sequence )
	{
		this.sequence = sequence;
	}

	public Integer getSequence( )
	{
		return this.sequence;
	}

	public String getValue( )
	{
		return SysUtils.isEmpty( this.getRevisedText( ) ) ? this.getIcrText( ) : this.getRevisedText( );
	}
}
