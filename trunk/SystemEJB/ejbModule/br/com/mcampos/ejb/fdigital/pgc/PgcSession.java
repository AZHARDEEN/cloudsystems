package br.com.mcampos.ejb.fdigital.pgc;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.fdigital.Pgc;

@Remote
public interface PgcSession extends BaseCrudSessionInterface<Pgc>
{

}
