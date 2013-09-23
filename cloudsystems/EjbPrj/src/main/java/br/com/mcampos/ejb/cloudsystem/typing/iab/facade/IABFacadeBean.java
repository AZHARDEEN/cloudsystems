package br.com.mcampos.ejb.cloudsystem.typing.iab.facade;


import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.address.CityDTO;
import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.typing.iab.IabProblemsDTO;
import br.com.mcampos.dto.user.attributes.GenderDTO;
import br.com.mcampos.ejb.cloudsystem.AbstractSecurity;
import br.com.mcampos.ejb.cloudsystem.locality.city.CityUtil;
import br.com.mcampos.ejb.cloudsystem.locality.city.entity.City;
import br.com.mcampos.ejb.cloudsystem.locality.city.session.CitySessionLocal;
import br.com.mcampos.ejb.cloudsystem.typing.iab.entity.DadosIab;
import br.com.mcampos.ejb.cloudsystem.typing.iab.session.IABSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.UserUtil;
import br.com.mcampos.ejb.cloudsystem.user.attribute.gender.GenderUtil;
import br.com.mcampos.ejb.cloudsystem.user.attribute.gender.entity.Gender;
import br.com.mcampos.ejb.cloudsystem.user.attribute.gender.session.GenderSessionLocal;
import br.com.mcampos.ejb.cloudsystem.user.person.session.NewPersonSessionLocal;
import br.com.mcampos.sysutils.SysUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless( name = "IABFacade", mappedName = "IABFacade" )
@TransactionAttribute( TransactionAttributeType.REQUIRES_NEW )
public class IABFacadeBean extends AbstractSecurity implements IABFacade
{
    public static final Integer messageId = 10;

    @PersistenceContext( unitName = "EjbPrj" )
    private transient EntityManager em;

    @EJB
    IABSessionLocal session;

    @EJB
    CitySessionLocal CitySession;

    @EJB
    GenderSessionLocal genderSession;

    @EJB
    NewPersonSessionLocal personSession;

    protected EntityManager getEntityManager()
    {
        return em;
    }

    public Integer getMessageTypeId()
    {
        return messageId;
    }

    public void add( AuthenticationDTO currentUser, DadosIab record ) throws ApplicationException
    {
        DadosIab firstType = session.getPrimeiraDigitacao( record.getId2() );
        DadosIab secondType = session.getSegundaDigitacao( record.getId2() );
        if ( secondType != null )
            throw new ApplicationException( "Esta prova já possui dupla digitação" );
        if ( firstType == null ) {
            record.setDate_1( new Date() );
            record.setTyper_1( currentUser.getUserId() );
            record.setType_status( 1 );
        }
        else {
            record.setDate_2( new Date() );
            record.setTyper_2( currentUser.getUserId() );
            record.setType_status( record.isEqual( firstType ) ? 4 : 2 );
            firstType.setType_status( record.isEqual( firstType ) ? 3 : 2 );

        }
        session.add( record );
    }

    public DadosIab get( String id ) throws ApplicationException
    {
        return session.getPrimeiraDigitacao( id );
    }


    public List<GenderDTO> getGenders() throws ApplicationException
    {
        List<Gender> genders = genderSession.getAll();
        return GenderUtil.toDTOList( genders );
    }


    public CityDTO getCity( Integer id ) throws ApplicationException
    {
        City city = CitySession.get( id );
        return CityUtil.copy( city );
    }


    public List<IabProblemsDTO> getProblems() throws ApplicationException
    {
        List<DadosIab> iab = session.getProblems();
        if ( SysUtils.isEmpty( iab ) )
            return Collections.emptyList();

        String old = "";
        IabProblemsDTO dto = null;
        List<IabProblemsDTO> out = new ArrayList<IabProblemsDTO>();
        for ( DadosIab item : iab ) {
            if ( old.equals( item.getId2() ) == false ) {
                dto = new IabProblemsDTO();
                dto.setId( item.getId2() );
                old = item.getId2();
                out.add( dto );
            }
            if ( SysUtils.isZero( item.getTyper_1() ) == false )
                dto.setUser_1( UserUtil.copy( personSession.get( item.getTyper_1() ) ) );
            if ( SysUtils.isZero( item.getTyper_2() ) == false )
                dto.setUser_2( UserUtil.copy( personSession.get( item.getTyper_2() ) ) );
            dto.setStatus( item.getType_status() );
        }
        return out;
    }

}
