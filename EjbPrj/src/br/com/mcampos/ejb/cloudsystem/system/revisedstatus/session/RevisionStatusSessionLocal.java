package br.com.mcampos.ejb.cloudsystem.system.revisedstatus.session;


import br.com.mcampos.ejb.cloudsystem.system.revisedstatus.entity.RevisionStatus;
import br.com.mcampos.exception.ApplicationException;

import java.io.Serializable;

import javax.ejb.Local;


@Local
public interface RevisionStatusSessionLocal extends Serializable
{
    RevisionStatus get( Integer key ) throws ApplicationException;
}
