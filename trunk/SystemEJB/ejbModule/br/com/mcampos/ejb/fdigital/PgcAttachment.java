package br.com.mcampos.ejb.fdigital;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the pgc_attachment database table.
 * 
 */
@Entity
@Table(name="pgc_attachment")
public class PgcAttachment implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PgcAttachmentPK id;

	//bi-directional many-to-one association to Pgc
    @ManyToOne
	@JoinColumn(name="pgc_id_in", nullable=false, insertable=false, updatable=false)
	private Pgc pgc;

    public PgcAttachment() {
    }

	public PgcAttachmentPK getId() {
		return this.id;
	}

	public void setId(PgcAttachmentPK id) {
		this.id = id;
	}
	
	public Pgc getPgc() {
		return this.pgc;
	}

	public void setPgc(Pgc pgc) {
		this.pgc = pgc;
	}
	
}