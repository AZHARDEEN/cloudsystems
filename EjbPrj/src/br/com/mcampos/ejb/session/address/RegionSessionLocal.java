package br.com.mcampos.ejb.session.address;

import br.com.mcampos.ejb.entity.address.Region;

import java.util.List;

import javax.ejb.Local;

@Local
public interface RegionSessionLocal
{
    Region persistRegion( Region region );

    Region mergeRegion( Region region );

    void removeRegion( Region region );

    List<Region> getRegionByCriteria( String jpqlStmt, int firstResult,
                                      int maxResults );

    List<Region> getRegionFindAll();

    List<Region> getRegionFindAllByRange( int firstResult, int maxResults );
}
