package br.com.mcampos.ejb.gdf;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.ejb.core.SimpleCodedTable;

@Local
public interface CodedTableSessionLocal extends BaseCrudSessionInterface<SimpleCodedTable<?>>
{

}
