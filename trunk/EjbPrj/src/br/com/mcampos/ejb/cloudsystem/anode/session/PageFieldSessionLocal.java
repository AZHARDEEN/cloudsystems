package br.com.mcampos.ejb.cloudsystem.anode.session;


import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anode.entity.AnotoPageField;
import br.com.mcampos.ejb.cloudsystem.anode.entity.key.AnotoPageFieldPK;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Local;


@Local
public interface PageFieldSessionLocal
{
    AnotoPageField add( AnotoPageField entity ) throws ApplicationException;

    AnotoPageField update( AnotoPageField entity ) throws ApplicationException;

    void delete( AnotoPageFieldPK key ) throws ApplicationException;

    AnotoPageField get( AnotoPageFieldPK key ) throws ApplicationException;

    List<AnotoPageField> getAll() throws ApplicationException;

    List<AnotoPageField> getAll( AnotoPage page ) throws ApplicationException;

    void add( AnotoPage anotoPage, List<AnotoPageField> list ) throws ApplicationException;

    void refresh( AnotoPage anotoPage, List<AnotoPageField> list ) throws ApplicationException;

}
