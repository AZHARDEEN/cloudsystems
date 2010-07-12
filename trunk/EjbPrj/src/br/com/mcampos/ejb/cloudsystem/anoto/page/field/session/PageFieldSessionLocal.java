package br.com.mcampos.ejb.cloudsystem.anoto.page.field.session;


import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anoto.page.field.entity.AnotoPageField;
import br.com.mcampos.ejb.cloudsystem.anoto.page.field.entity.AnotoPageFieldPK;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import java.util.List;

import javax.ejb.Local;


@Local
public interface PageFieldSessionLocal extends Serializable
{
    AnotoPageField add( AnotoPageField entity ) throws ApplicationException;

    AnotoPageField update( AnotoPageField entity ) throws ApplicationException;

    void delete( AnotoPageFieldPK key ) throws ApplicationException;

    AnotoPageField get( AnotoPageFieldPK key ) throws ApplicationException;

    List<AnotoPageField> getAll() throws ApplicationException;

    List<AnotoPageField> getAll( AnotoPage page ) throws ApplicationException;

    List<AnotoPageField> getAll( AnotoForm form ) throws ApplicationException;

    List<AnotoPageField> getSearchable( AnotoForm form ) throws ApplicationException;

    void add( AnotoPage anotoPage, List<AnotoPageField> list ) throws ApplicationException;

    void refresh( AnotoPage anotoPage, List<AnotoPageField> list ) throws ApplicationException;

    List<AnotoPageField> getFieldsToExport() throws ApplicationException;


}
