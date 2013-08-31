package br.com.mcampos.ejb.user.person.title;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.user.Gender;
import br.com.mcampos.entity.user.Title;

@Remote
public interface TitleSession extends BaseSessionInterface<Title>
{
	List<Title> getAll( Gender gender );
}
