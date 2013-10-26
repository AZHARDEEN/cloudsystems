package br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.attachment;

import java.io.Serializable;

public class PgcPageAttachmentPK implements Serializable
{
	private Integer bookId;
	private Integer pageId;
	private Integer sequence;
	private Integer pgcId;

	public PgcPageAttachmentPK()
	{
	}

	public PgcPageAttachmentPK( Integer pat_book_id, Integer pat_page_id, Integer pat_seq_in, Integer pgc_id_in )
	{
		this.bookId = pat_book_id;
		this.pageId = pat_page_id;
		this.sequence = pat_seq_in;
		this.pgcId = pgc_id_in;
	}

	public boolean equals( Object other )
	{
		if ( other instanceof PgcPageAttachmentPK ) {
			final PgcPageAttachmentPK otherPgcAttachmentPK = ( PgcPageAttachmentPK )other;
			final boolean areEqual =
						 ( otherPgcAttachmentPK.bookId.equals( bookId ) && otherPgcAttachmentPK.pageId.equals( pageId ) && otherPgcAttachmentPK.sequence.equals( sequence ) &&
						   otherPgcAttachmentPK.pgcId.equals( pgcId ) );
			return areEqual;
		}
		return false;
	}

	public int hashCode()
	{
		return super.hashCode();
	}

	Integer getBookId()
	{
		return bookId;
	}

	void setBookId( Integer pat_book_id )
	{
		this.bookId = pat_book_id;
	}

	Integer getPageId()
	{
		return pageId;
	}

	void setPageId( Integer pat_page_id )
	{
		this.pageId = pat_page_id;
	}

	Integer getSequence()
	{
		return sequence;
	}

	void setSequence( Integer pat_seq_in )
	{
		this.sequence = pat_seq_in;
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
