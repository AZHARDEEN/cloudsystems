package br.com.mcampos.ejb.user.document.type;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.user.DocumentType;

@Remote
public interface DocumentTypeSession extends BaseCrudSessionInterface<DocumentType>
{

}
