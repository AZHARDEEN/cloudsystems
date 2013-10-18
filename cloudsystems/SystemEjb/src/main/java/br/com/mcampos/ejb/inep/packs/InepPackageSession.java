package br.com.mcampos.ejb.inep.packs;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.dto.inep.InepStationSubscriptionResponsableImportDTO;
import br.com.mcampos.dto.inep.InepSubscriptionImportDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.system.FileUpload;

@Remote
public interface InepPackageSession extends BaseCrudSessionInterface<InepEvent>
{
	@Override
	List<InepEvent> getAll( PrincipalDTO auth );

	List<InepEvent> getAll( PrincipalDTO auth, DBPaging page );

	Integer getNextId( PrincipalDTO auth );

	void add( PrincipalDTO auth, List<InepSubscriptionImportDTO> subscriptions, InepEvent event );

	void add( PrincipalDTO auth, InepSubscriptionImportDTO subscriptions, InepEvent event );

	void add( PrincipalDTO auth, InepStationSubscriptionResponsableImportDTO record, InepEvent event );

	FileUpload storeUploadInformation( PrincipalDTO auth, MediaDTO media, int processed, int rejected );

}
