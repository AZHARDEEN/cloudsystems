package br.com.mcampos.ejb.fdigital.form.pad;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.fdigital.Pad;

@Remote
public interface PadSession extends BaseSessionInterface<Pad>
{

}
