package br.com.mcampos.ejb.fdigital.form.pad.page;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.entity.fdigital.AnotoForm;
import br.com.mcampos.entity.fdigital.AnotoPage;

@Local
public interface AnotoPageSessionLocal extends BaseSessionInterface<AnotoPage>
{
	List<AnotoPage> getAll( AnotoForm from, DBPaging page );
}
