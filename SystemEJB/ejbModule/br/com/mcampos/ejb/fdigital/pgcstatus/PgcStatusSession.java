package br.com.mcampos.ejb.fdigital.pgcstatus;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.fdigital.PgcStatus;

@Remote
public interface PgcStatusSession extends BaseCrudSessionInterface<PgcStatus>
{

}
