package br.com.mcampos.ejb.fdigital.pgcstatus;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.fdigital.PgcStatus;

@Remote
public interface PgcStatusSession extends BaseSessionInterface<PgcStatus>
{

}
