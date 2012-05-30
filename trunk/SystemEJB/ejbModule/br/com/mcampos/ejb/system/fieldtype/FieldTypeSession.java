package br.com.mcampos.ejb.system.fieldtype;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;

@Remote
public interface FieldTypeSession extends BaseSessionInterface<FieldType>
{

}
