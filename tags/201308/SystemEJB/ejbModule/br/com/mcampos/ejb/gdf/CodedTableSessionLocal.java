package br.com.mcampos.ejb.gdf;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.core.SimpleCodedTable;

@Local
public interface CodedTableSessionLocal extends BaseSessionInterface<SimpleCodedTable<?>>
{

}
