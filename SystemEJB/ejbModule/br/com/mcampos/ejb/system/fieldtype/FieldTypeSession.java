package br.com.mcampos.ejb.system.fieldtype;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.system.FieldType;

@Remote
public interface FieldTypeSession extends BaseCrudSessionInterface<FieldType>
{

}
