package br.com.mcampos.ejb.inep.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleTable;

/**
 * The persistent class for the distribution_status database table.
 * 
 */
@Entity
@Table( name = "inep_distribution_status", schema = "inep" )
public class DistributionStatus extends SimpleTable<DistributionStatus> implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "ids_id_in" )
	private Integer id;

	@Column( name = "ids_description_ch" )
	private String description;

	public DistributionStatus() {
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	@Override
	public void setId(Integer dstIdIn) {
		this.id = dstIdIn;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public void setDescription(String dstDescricaoCh) {
		this.description = dstDescricaoCh;
	}
}