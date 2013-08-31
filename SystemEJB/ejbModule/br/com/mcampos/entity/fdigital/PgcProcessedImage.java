package br.com.mcampos.entity.fdigital;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the pgc_processed_image database table.
 * 
 */
@Entity
@Table(name="pgc_processed_image")
public class PgcProcessedImage implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PgcProcessedImagePK id;

	//bi-directional many-to-one association to PgcPage
    @ManyToOne
	@JoinColumns({
		@JoinColumn(name="pgc_id_in", referencedColumnName="pgc_id_in", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ppg_book_id", referencedColumnName="ppg_book_id", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="ppg_page_id", referencedColumnName="ppg_page_id", nullable=false, insertable=false, updatable=false)
		})
	private PgcPage pgcPage;

    public PgcProcessedImage() {
    }

	public PgcProcessedImagePK getId() {
		return this.id;
	}

	public void setId(PgcProcessedImagePK id) {
		this.id = id;
	}
	
	public PgcPage getPgcPage() {
		return this.pgcPage;
	}

	public void setPgcPage(PgcPage pgcPage) {
		this.pgcPage = pgcPage;
	}
	
}