package br.com.mcampos.ejb.cloudsystem.anoto.pgcpage;


import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface PgcPageSessionLocal extends Serializable
{
    @SuppressWarnings( "compatibility:-7372095468374414564" )
    static final long serialVersionUID = -5066473496215477597L;

    void delete( PgcPagePK key ) throws ApplicationException;

    PgcPage get( PgcPagePK key ) throws ApplicationException;

    PgcPage add( PgcPage entity ) throws ApplicationException;

    void setRevisedStatus( PgcPage page, Integer status ) throws ApplicationException;

    List<PgcPage> getAll( AnotoForm form ) throws ApplicationException;

}
