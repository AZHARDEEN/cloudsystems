package br.com.mcampos.ejb.cloudsystem.anoto.page.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPagePK;

import java.io.Serializable;

import javax.ejb.Local;


@Local
public interface AnotoPageSessionLocal extends Serializable
{
    AnotoPage get( AnotoPagePK key ) throws ApplicationException;

    AnotoPage add( AnotoPage anotoPage ) throws ApplicationException;
}
