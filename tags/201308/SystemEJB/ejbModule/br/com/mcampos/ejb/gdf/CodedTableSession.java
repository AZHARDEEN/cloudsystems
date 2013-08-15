package br.com.mcampos.ejb.gdf;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.core.SimpleCodedTable;

@Remote
public interface CodedTableSession extends BaseSessionInterface<SimpleCodedTable<?>>
{

}
