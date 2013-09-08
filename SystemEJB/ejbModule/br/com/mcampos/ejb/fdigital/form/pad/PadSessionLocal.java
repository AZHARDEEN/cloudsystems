package br.com.mcampos.ejb.fdigital.form.pad;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.fdigital.AnotoPage;
import br.com.mcampos.entity.fdigital.Pad;

@Local
public interface PadSessionLocal extends BaseCrudSessionInterface<Pad>
{
	Pad add( Pad pad, List<AnotoPage> pages );
}
