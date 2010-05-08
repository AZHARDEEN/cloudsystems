package br.com.mcampos.ejb.cloudsystem.anoto.pgc;


import br.com.mcampos.dto.anoto.AnotoResultList;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.attachment.PgcAttachment;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPage;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpenpage.PgcPenPage;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.image.PgcProcessedImage;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field.PgcField;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Local;


@Local
public interface PGCSessionLocal
{

    Pgc add( Pgc entity ) throws ApplicationException;

    void delete( Integer key ) throws ApplicationException;

    Pgc get( Integer key ) throws ApplicationException;

    List<Pgc> getAll() throws ApplicationException;

    List<Pgc> getSuspended() throws ApplicationException;

	PgcPenPage attach( Pgc pgc, AnotoPenPage penPage ) throws ApplicationException;

    void setPgcStatus( Pgc pgc, Integer newStatus ) throws ApplicationException;

    List<AnotoPenPage> get( AnotoPage page ) throws ApplicationException;

    List<PgcPenPage> get( Pgc pgc ) throws ApplicationException;

    List<PgcPenPage> getAll( AnotoPenPage penPage ) throws ApplicationException;

    List<PgcPenPage> getAll( AnotoPen pen ) throws ApplicationException;

    void add( PgcProcessedImage processedImage ) throws ApplicationException;

    void add( PgcField pgcField ) throws ApplicationException;

    void add( PgcAttachment pgcField ) throws ApplicationException;

    void add( PgcPage entity ) throws ApplicationException;

    List<Media> getImages( PgcPage page ) throws ApplicationException;

    List<PgcField> getFields( PgcPage page ) throws ApplicationException;

    void update( PgcField field ) throws ApplicationException;

    Integer remove( AnotoResultList item ) throws ApplicationException;

    List<PgcAttachment> getAttachments( PgcPage page ) throws ApplicationException;

    Boolean isEnabled ( Pgc pgc, String pageAddress );
}