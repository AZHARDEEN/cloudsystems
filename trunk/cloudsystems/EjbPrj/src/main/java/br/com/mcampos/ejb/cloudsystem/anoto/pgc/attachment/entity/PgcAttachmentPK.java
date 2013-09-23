package br.com.mcampos.ejb.cloudsystem.anoto.pgc.attachment.entity;

import java.io.Serializable;

public class PgcAttachmentPK implements Serializable
{
	private Integer mediaId;
	private Integer pgcId;

	public PgcAttachmentPK()
	{
	}

	public PgcAttachmentPK( Integer med_id_in, Integer pgc_id_in )
	{
		this.mediaId = med_id_in;
		this.pgcId = pgc_id_in;
	}

	public boolean equals( Object other )
	{
		if ( other instanceof PgcAttachmentPK ) {
			final PgcAttachmentPK otherPgcAttachmentPK = ( PgcAttachmentPK )other;
			final boolean areEqual =
						 ( otherPgcAttachmentPK.mediaId.equals( mediaId ) && otherPgcAttachmentPK.pgcId.equals( pgcId ) );
			return areEqual;
		}
		return false;
	}

	public int hashCode()
	{
		return super.hashCode();
	}

	Integer getMediaId()
	{
		return mediaId;
	}

	void setMediaId( Integer med_id_in )
	{
		this.mediaId = med_id_in;
	}

	Integer getPgcId()
	{
		return pgcId;
	}

	void setPgcId( Integer pgc_id_in )
	{
		this.pgcId = pgc_id_in;
	}
}
