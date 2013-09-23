package br.com.mcampos.ejb.user.person.title;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.user.Gender;
import br.com.mcampos.jpa.user.Title;

@Remote
public interface TitleSession extends BaseCrudSessionInterface<Title>
{
	List<Title> getAll( Gender gender );
}
