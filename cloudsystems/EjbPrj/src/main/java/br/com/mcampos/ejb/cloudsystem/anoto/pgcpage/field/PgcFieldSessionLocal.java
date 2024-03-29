package br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.anoto.AnotoSummary;
import br.com.mcampos.ejb.cloudsystem.anoto.pgc.Pgc;
import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPage;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import javax.ejb.Local;


@Local
public interface PgcFieldSessionLocal extends Serializable
{
    PgcField add( PgcField entity ) throws ApplicationException;

    PgcField update( PgcField entity ) throws ApplicationException;

    void delete( PgcFieldPK key ) throws ApplicationException;

    PgcField get( PgcFieldPK key ) throws ApplicationException;

    List<PgcField> getAll( PgcPage pgcPage ) throws ApplicationException;

    List<PgcField> getAllBook( PgcPage pgcPage ) throws ApplicationException;

    List<Pgc> getAll( Properties props ) throws ApplicationException;

    AnotoSummary summaryType( Pgc pgc ) throws ApplicationException;

    AnotoSummary summarySituation( Pgc pgc ) throws ApplicationException;

    AnotoSummary summaryCategory( Pgc pgc ) throws ApplicationException;

    AnotoSummary summaryPayment( Pgc pgc ) throws ApplicationException;

}
