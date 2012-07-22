package br.com.mcampos.ejb.fdigital;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the pgc_pen_page database table.
 * 
 */
@Entity
@Table(name="pgc_pen_page")
public class PgcPenPage implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PgcPenPagePK id;

    public PgcPenPage() {
    }

	public PgcPenPagePK getId() {
		return this.id;
	}

	public void setId(PgcPenPagePK id) {
		this.id = id;
	}
	
}