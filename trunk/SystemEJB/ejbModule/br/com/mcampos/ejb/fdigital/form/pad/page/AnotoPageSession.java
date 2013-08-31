package br.com.mcampos.ejb.fdigital.form.pad.page;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.entity.fdigital.AnotoForm;
import br.com.mcampos.entity.fdigital.AnotoPage;

@Remote
public interface AnotoPageSession extends BaseSessionInterface<AnotoPage>
{
	List<AnotoPage> getAll( AnotoForm from, DBPaging page );
}
