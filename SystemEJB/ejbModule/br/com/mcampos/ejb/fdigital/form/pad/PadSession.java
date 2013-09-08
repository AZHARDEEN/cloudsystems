package br.com.mcampos.ejb.fdigital.form.pad;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.fdigital.Pad;

@Remote
public interface PadSession extends BaseCrudSessionInterface<Pad>
{

}
