package br.com.mcampos.ejb.system.revisionstatus;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.system.RevisionStatus;

@Remote
public interface RevisionStatusSession extends BaseSessionInterface<RevisionStatus>
{

}
