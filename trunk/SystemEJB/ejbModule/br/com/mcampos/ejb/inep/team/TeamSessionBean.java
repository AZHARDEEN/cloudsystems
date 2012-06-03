package br.com.mcampos.ejb.inep.team;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.mcampos.dto.inep.InepTaskCounters;
import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.distribution.DistributionSessionLocal;
import br.com.mcampos.ejb.inep.distribution.DistributionStatusSessionLocal;
import br.com.mcampos.ejb.inep.entity.DistributionStatus;
import br.com.mcampos.ejb.inep.entity.InepDistribution;
import br.com.mcampos.ejb.inep.entity.InepDistributionPK;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.ejb.inep.entity.InepTask;
import br.com.mcampos.ejb.inep.entity.InepTest;
import br.com.mcampos.ejb.inep.packs.InepPackageSessionLocal;
import br.com.mcampos.ejb.inep.revisor.InepRevisorSessionLocal;
import br.com.mcampos.ejb.inep.task.InepTaskSessionLocal;
import br.com.mcampos.ejb.inep.test.InepTestSessionLocal;
import br.com.mcampos.ejb.media.Media;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;
import br.com.mcampos.ejb.user.company.collaborator.CollaboratorSessionLocal;

/**
 * Session Bean implementation class TeamSessionBean
 */
@Stateless( name = "TeamSession", mappedName = "TeamSession" )
@LocalBean
public class TeamSessionBean extends SimpleSessionBean<InepRevisor> implements TeamSession, TeamSessionLocal
{
	@EJB
	private InepTaskSessionLocal taskSession;

	@EJB
	DistributionSessionLocal distributionSession;

	@EJB
	DistributionStatusSessionLocal distributionStatusSession;

	@EJB
	InepPackageSessionLocal packageSession;

	@EJB
	CollaboratorSessionLocal collaboratorSession;

	@EJB
	InepRevisorSessionLocal revisorSession;

	@EJB
	InepTestSessionLocal testSession;

	public TeamSessionBean( )
	{
	}

	@Override
	protected Class<InepRevisor> getEntityClass( )
	{
		return InepRevisor.class;
	}

	@Override
	public List<InepTask> getTasks( )
	{
		return (List<InepTask>) this.taskSession.getAll( );
	}

	@Override
	public List<InepRevisor> getTeam( InepTask task )
	{
		InepTask entityTask = this.taskSession.get( task.getId( ) );
		if ( entityTask == null ) {
			return Collections.emptyList( );
		}
		List<InepRevisor> team = findByNamedQuery( InepRevisor.getTeamByTask, entityTask );
		return team;
	}

	@Override
	public void distribute( )
	{
		Query query = getEntityManager( ).createQuery( "delete from Distribution" );

		query.executeUpdate( );

		List<InepPackage> packs = (List<InepPackage>) this.packageSession.getAll( );
		List<InepRevisor> team = findByNamedQuery( InepRevisor.getAllTeam );
		int x = 0, y = 1;
		InepDistribution entity;
		DistributionStatus status = this.distributionStatusSession.get( new Integer( 1 ) );
		for ( InepPackage pack : packs )
		{
			if ( x >= team.size( ) - 1 ) {
				x = 0;
			}
			if ( y >= team.size( ) ) {
				y = x + 1;
			}
			System.out.println( "Distribuindo para dupla: " + x + " e " + y );
			entity = new InepDistribution( );
			entity.setStatus( status );
			this.distributionSession.merge( entity );

			entity = new InepDistribution( );
			entity.setStatus( status );
			this.distributionSession.merge( entity );
			y++;
			x++;
		}
	}

	@Override
	public List<InepPackage> getPackages( InepRevisor rev )
	{
		List<InepPackage> packages = Collections.emptyList( );
		return packages;
	}

	@Override
	public InepRevisor getRevisor( InepPackage event, Collaborator auth )
	{
		return getRevisorSession( ).get( event, auth );
	}

	@Override
	public List<InepDistribution> getTests( InepRevisor revisor, Integer status )
	{
		if ( revisor == null ) {
			return Collections.emptyList( );
		}
		if ( status == null || status.intValue( ) < 1 ) {
			status = 1;
		}
		return this.distributionSession.get( revisor, status );
	}

	@Override
	public InepDistribution getRevision( InepRevisor rev, InepDistribution t )
	{
		Query query = getEntityManager( )
				.createQuery(
						"from InepRevision o where o.id.packageId = ?1 and o.id.taskId = ?2 and o.id.companyId = ?3 and o.id.collaboratorId = ?4 and o.id.testId = ?5" );

		query.setParameter( 1, t.getId( ).getEventId( ) );
		query.setParameter( 2, t.getId( ).getTaskId( ) );
		query.setParameter( 3, t.getId( ).getCompanyId( ) );
		query.setParameter( 4, rev.getId( ).getSequence( ) );
		try {
			return (InepDistribution) query.getSingleResult( );
		}
		catch ( NoResultException e ) {
			e = null;
			return null;
		}

	}

	@Override
	public InepDistribution updateRevision( InepDistribution rev )
	{
		InepDistribution entity = getEntityManager( ).find( InepDistribution.class, rev.getId( ) );
		if ( entity != null ) {
			rev = getEntityManager( ).merge( rev );
		}
		else {
			getEntityManager( ).persist( rev );
		}
		rev.setStatus( getEntityManager( ).find( DistributionStatus.class, 2 ) );
		if ( rev.getRevisor( ).isCoordenador( ).equals( false ) ) {
			List<InepDistribution> others = this.distributionSession.findByNamedQuery( InepDistribution.getAllFromTest,
					rev.getTest( ) );
			for ( InepDistribution other : others )
			{
				if ( other.equals( rev ) ) {
					continue;
				}
				int variance = other.getNota( ).intValue( ) - rev.getNota( ).intValue( );
				if ( variance < 0 ) {
					variance *= -1;
				}
				Integer threshold = getProperty( ).getInt( "inep.threshold" );
				if ( threshold == null ) {
					threshold = 2;
					getProperty( ).setInt( "inep.threshold", "Valor máximo admitido de discrepância", threshold );
				}
				if ( variance >= threshold || ( other.getNota( ) > 5 && rev.getNota( ).equals( other.getNota( ) ) == false ) )
				{
					List<InepRevisor> coordinators = this.revisorSession.findByNamedQuery( InepRevisor.getAllCoordinatorsToTask,
							rev.getRevisor( ).getTask( ) );
					for ( InepRevisor coordinator : coordinators )
					{
						InepDistribution dist = this.distributionSession
								.get( new InepDistributionPK( coordinator, rev.getTest( ) ) );
						if ( dist == null ) {
							dist = new InepDistribution( coordinator, rev.getTest( ) );
						}
						dist.setStatus( this.distributionStatusSession.get( new Integer( 1 ) ) );
						dist = this.distributionSession.merge( dist );
					}
				}
			}
		}
		return rev;
	}

	@Override
	public List<InepDistribution> getOtherDistributions( InepTest test )
	{
		List<InepDistribution> others = this.distributionSession.findByNamedQuery( InepDistribution.getAllFromTest, test );

		return others;
	}

	public CollaboratorSessionLocal getCollaboratorSession( )
	{
		return this.collaboratorSession;
	}

	public InepRevisorSessionLocal getRevisorSession( )
	{
		return this.revisorSession;
	}

	@Override
	public List<InepPackage> getEvents( Collaborator auth )
	{
		return this.taskSession.getEvents( auth );
	}

	@Override
	public byte[ ] getMedia( InepTest test )
	{
		byte[ ] obj = null;

		InepTest merged = this.testSession.get( test.getId( ) );
		if ( merged == null ) {
			return null;
		}
		Media media = merged.getMedia( );
		if ( media != null ) {
			obj = media.getObject( );
		}
		return obj;
	}

	@Override
	public InepTaskCounters getCounters( InepRevisor rev )
	{
		InepTaskCounters dto = new InepTaskCounters( );
		if ( rev != null ) {
			Query query;

			if ( rev.isCoordenador( ) ) {
				query = getEntityManager( ).createNamedQuery( InepDistribution.getCoordCounter );
				query.setParameter( 1, rev.getTask( ) );
			}
			else {
				query = getEntityManager( ).createNamedQuery( InepDistribution.getRevisorCounter );
				query.setParameter( 1, rev );
			}
			try {
				@SuppressWarnings( "unchecked" )
				List<Object[ ]> values = query.getResultList( );
				if ( values != null ) {
					for ( Object[ ] obj : values )
					{
						int status = ( (Integer) obj[ 0 ] );
						Long count = ( (Long) obj[ 1 ] );
						switch ( status )
						{
						case 1:
							dto.setTasks( count.intValue( ) );
							break;
						case 2:
							dto.setRevised( count.intValue( ) );
							break;
						case 3:
							dto.setVariance( count.intValue( ) );
							break;
						}
					}
				}
			}
			catch ( Exception e )
			{
				e = null;
			}
		}
		return dto;
	}
}
