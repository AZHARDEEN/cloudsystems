package br.com.mcampos.ejb.fdigital.pgc;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.fdigital.Pgc;

@Local
public interface PgcSessionLocal extends BaseCrudSessionInterface<Pgc>
{

}
