package br.com.mcampos.ejb.cloudsystem.anoto.penpage.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.anoto.form.AnotoForm;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnotoPen;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.entity.AnotoPenPage;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;


@Local
public interface PenPageSessionLocal extends Serializable
{
    AnotoPenPage add( AnotoPage page, AnotoPen pen ) throws ApplicationException;

    void delete( AnotoPage page, AnotoPen pen ) throws ApplicationException;

    AnotoPenPage get( AnotoPage page, AnotoPen pen ) throws ApplicationException;

    AnotoPenPage get( AnotoPen pen, String pageAddress ) throws ApplicationException;

    List<AnotoPenPage> get( AnotoForm form ) throws ApplicationException;
}
