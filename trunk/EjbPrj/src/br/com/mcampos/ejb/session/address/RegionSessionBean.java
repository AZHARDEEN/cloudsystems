package br.com.mcampos.ejb.session.address;

import br.com.mcampos.ejb.entity.address.Region;

import br.com.mcampos.ejb.entity.address.RegionPK;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.interceptor.Interceptors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless( name = "RegionSession", mappedName = "CloudSystems-EjbPrj-RegionSession" )
@Remote
@Local
public class RegionSessionBean implements RegionSession, RegionSessionLocal
{
    @PersistenceContext( unitName="EjbPrj" )
    private EntityManager em;

    public RegionSessionBean()
    {
    }

    public Region persistRegion( Region region )
    {
        em.persist(region);
        return region;
    }

    public Region mergeRegion( Region region )
    {
        return em.merge(region);
    }

    public void removeRegion( Region region )
    {
        region = em.find(Region.class, new RegionPK(region.getCountryId(), region.getId()));
        em.remove(region);
    }

    public List<Region> getRegionByCriteria( String jpqlStmt, int firstResult,
                                             int maxResults )
    {
        Query query = em.createQuery(jpqlStmt);
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    /** <code>select o from Region o</code> */
     @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public List<Region> getRegionFindAll()
    {
        return em.createNamedQuery("Region.findAll").getResultList();
    }


    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public List<Region> getByCountry( Integer countryId )
    {
        return em.createNamedQuery("Region.findAll").setParameter("countryId", countryId).getResultList();
    }

    /** <code>select o from Region o</code> */
    @TransactionAttribute ( value = TransactionAttributeType.NOT_SUPPORTED )
    public List<Region> getRegionFindAllByRange( int firstResult,
                                                 int maxResults )
    {
        Query query = em.createNamedQuery("Region.findAll");
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }
}
