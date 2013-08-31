package br.com.mcampos.ejb.system.revisionstatus;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.system.RevisionStatus;

@Local
public interface RevisionStatusSessionLocal extends BaseSessionInterface<RevisionStatus>
{

}
