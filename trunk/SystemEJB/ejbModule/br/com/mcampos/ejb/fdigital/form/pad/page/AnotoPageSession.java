package br.com.mcampos.ejb.fdigital.form.pad.page;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.jpa.fdigital.AnotoForm;
import br.com.mcampos.jpa.fdigital.AnotoPage;

@Remote
public interface AnotoPageSession extends BaseCrudSessionInterface<AnotoPage>
{
	List<AnotoPage> getAll( AnotoForm from, DBPaging page );
}
