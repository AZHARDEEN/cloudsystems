package br.com.mcampos.ejb.fdigital.form.pad;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.fdigital.form.pad.page.AnotoPage;

@Local
public interface PadSessionLocal extends BaseSessionInterface<Pad>
{
	Pad add( Pad pad, List<AnotoPage> pages );
}
