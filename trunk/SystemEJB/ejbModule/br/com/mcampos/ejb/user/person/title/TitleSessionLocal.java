package br.com.mcampos.ejb.user.person.title;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.user.Gender;
import br.com.mcampos.entity.user.Title;

@Local
public interface TitleSessionLocal extends BaseCrudSessionInterface<Title>
{
	List<Title> getAll( Gender gender );
}
