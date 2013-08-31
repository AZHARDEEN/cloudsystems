package br.com.mcampos.ejb.user.person.title;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.user.Gender;
import br.com.mcampos.entity.user.Title;

@Local
public interface TitleSessionLocal extends BaseSessionInterface<Title>
{
	List<Title> getAll( Gender gender );
}
