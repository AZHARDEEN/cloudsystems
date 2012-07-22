package br.com.mcampos.ejb.fdigital.pgcstatus;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Remote
public interface PgcStatusSession extends BaseSessionInterface<PgcStatus>
{

}
