package br.com.mcampos.ejb.system.fieldtype;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.system.FieldType;

@Local
public interface FieldTypeSessionLocal extends BaseCrudSessionInterface<FieldType>
{

}
