package br.com.mcampos.ejb.cloudsystem.anode.session;


import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.BackgroundImage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.Pgc;
import br.com.mcampos.ejb.cloudsystem.anode.entity.PgcPenPage;
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

    PgcPenPage attach( Pgc pgc, AnotoPenPage penPage ) throws ApplicationException;

    void setPgcStatus( Pgc pgc, Integer newStatus ) throws ApplicationException;

    List<AnotoPenPage> get( AnotoPage page ) throws ApplicationException;

    List<PgcPenPage> getAll( AnotoPenPage penPage ) throws ApplicationException;
}
