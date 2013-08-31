package br.com.mcampos.ejb.fdigital.pgcstatus;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.fdigital.PgcStatus;

@Local
public interface PgcStatusSessionLocal extends BaseSessionInterface<PgcStatus>
{

}
