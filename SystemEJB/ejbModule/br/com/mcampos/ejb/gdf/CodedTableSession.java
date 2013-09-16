package br.com.mcampos.ejb.gdf;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.SimpleCodedTable;

@Remote
public interface CodedTableSession extends BaseCrudSessionInterface<SimpleCodedTable<?>>
{

}
