package br.com.mcampos.ejb.cloudsystem.system.revisedstatus.session;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.ejb.cloudsystem.system.revisedstatus.entity.RevisionStatus;

import java.io.Serializable;

import javax.ejb.Local;


@Local
public interface RevisionStatusSessionLocal extends Serializable
{
    RevisionStatus get( Integer key ) throws ApplicationException;
}
