package br.com.mcampos.ejb.fdigital;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the pgc_attachment_type database table.
 * 
 */
@Entity
@Table(name="pgc_attachment_type")
public class PgcAttachmentType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="pat_id_in", unique=true, nullable=false)
	private Integer patIdIn;

	@Column(name="pat_description_ch", length=64)
	private String patDescriptionCh;

	//bi-directional many-to-one association to PgcPageAttachment
	@OneToMany(mappedBy="pgcAttachmentType")
	private List<PgcPageAttachment> pgcPageAttachments;

    public PgcAttachmentType() {
    }

	public Integer getPatIdIn() {
		return this.patIdIn;
	}

	public void setPatIdIn(Integer patIdIn) {
		this.patIdIn = patIdIn;
	}

	public String getPatDescriptionCh() {
		return this.patDescriptionCh;
	}

	public void setPatDescriptionCh(String patDescriptionCh) {
		this.patDescriptionCh = patDescriptionCh;
	}

	public List<PgcPageAttachment> getPgcPageAttachments() {
		return this.pgcPageAttachments;
	}

	public void setPgcPageAttachments(List<PgcPageAttachment> pgcPageAttachments) {
		this.pgcPageAttachments = pgcPageAttachments;
	}
	
}