package br.com.mcampos.ejb.cloudsystem.anoto.penpage.user.session;


import br.com.mcampos.ejb.cloudsystem.anoto.penpage.entity.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.anoto.penpage.user.entity.PenUser;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import javax.ejb.Local;


@Local
public interface PenPageUserSessionLocal extends Serializable
{
    PenUser getCurrent( AnotoPenPage penPage ) throws ApplicationException;
}
