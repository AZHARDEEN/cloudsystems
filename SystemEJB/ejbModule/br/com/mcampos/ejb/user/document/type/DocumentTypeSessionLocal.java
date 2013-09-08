package br.com.mcampos.ejb.user.document.type;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.user.DocumentType;

@Local
public interface DocumentTypeSessionLocal extends BaseCrudSessionInterface<DocumentType>
{

}
