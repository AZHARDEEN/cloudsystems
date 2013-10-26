package br.com.mcampos.controller.typing;


import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.address.CityDTO;
import br.com.mcampos.ejb.cloudsystem.typing.iab.entity.DadosIab;
import br.com.mcampos.ejb.cloudsystem.typing.iab.facade.IABFacade;
import br.com.mcampos.sysutils.SysUtils;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;

public class IabController extends LoggedBaseController
{
    private Intbox id;
    private Intbox cityId;
    private Bandbox findCity;
    private Textbox school;
    private Textbox teacher;
    private Intbox intYear;
    private Intbox intSerie;
    private Combobox comboTurno;
    private Combobox comboType;
    private Textbox name;
    private Datebox birthDate;
    private Combobox gender;
    private Intbox nota1;
    private Intbox nota2;
    private Intbox nota3;
    private Intbox nota4;


    private IABFacade session;

    public IabController( char c )
    {
        super( c );
    }

    public IabController()
    {
        super();
    }

    @Override
    protected String getPageTitle()
    {
        return "Sistema de Digitação - IAB";
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        id.setFocus( true );
        loadCombobox( gender, getSession().getGenders() );
        gender.setSelectedIndex( 0 );

        nota4.addEventListener( Events.ON_OK, new EventListener()
            {
                public void onEvent( Event event )
                {
                    addRecord();
                }
            } );

        cityId.addEventListener( Events.ON_OK, new EventListener()
            {
                public void onEvent( Event event )
                {
                    findCityById();
                }
            } );

        id.addEventListener( Events.ON_OK, new EventListener()
            {
                public void onEvent( Event event )
                {
                    loadDadosIab();
                }
            } );
    }

    public void addRecord()
    {
        DadosIab newRecord = new DadosIab();
        load( newRecord );
        try {
            getSession().add( getLoggedInUser(), newRecord );
            id.setRawValue( null );
            id.setFocus( true );
            name.setValue( "" );
            birthDate.setRawValue( null );
            gender.setSelectedIndex( 0 );
            nota1.setRawValue( null );
            nota2.setRawValue( null );
            nota3.setRawValue( null );
            nota4.setRawValue( null );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage() );
        }
    }


    private void load( DadosIab newRecord )
    {
        if ( SysUtils.isZero( intYear.getValue() ) == false )
            newRecord.setAno_corrigido( intYear.getValue().toString() );

        newRecord.setCit_id_in( cityId.getValue() );
        newRecord.setData_nascimento( birthDate.getValue() );
        newRecord.setEscola( school.getValue() );
        newRecord.setInicial_professor( teacher.getValue() );
        newRecord.setNome( name.getValue() );
        newRecord.setNota1( nota1.getValue() );
        newRecord.setNota2( nota2.getValue() );
        newRecord.setNota3( nota3.getValue() );
        newRecord.setNota4( nota4.getValue() );
        newRecord.setSexo( gender.getValue() );
        newRecord.setTurno( comboTurno.getValue() );
        newRecord.setUrbano_rural( comboType.getValue() );

        if ( id.getValue() != null )
            newRecord.setId2( id.getValue().toString() );
    }

    public IABFacade getSession()
    {
        if ( session == null )
            session = ( IABFacade )getRemoteSession( IABFacade.class );
        return session;
    }

    public void findCityById()
    {
        if ( SysUtils.isZero( cityId.getValue() ) == false ) {
            try {
                CityDTO city = loadCity( cityId.getValue() );
                if ( city == null ) {
                    findCity.setFocus( true );
                }
                else {
                    school.setFocus( true );
                }
            }
            catch ( ApplicationException e ) {
                showErrorMessage( e.getMessage() );
            }
        }
    }

    private CityDTO loadCity( Integer id ) throws ApplicationException
    {
        CityDTO city = null;
        city = getSession().getCity( id );
        if ( city != null ) {
            findCity.setValue( city.getDescription() );
        }
        return city;
    }

    public void loadDadosIab()
    {
        if ( SysUtils.isZero( id.getValue() ) == false ) {
            try {
                DadosIab record = null;

                if ( id.getValue() != null )
                    record = getSession().get( id.getValue().toString() );
                if ( record == null ) {
                    cityId.setFocus( true );
                    cityId.setRawValue( null );
                    school.setRawValue( null );
                    teacher.setRawValue( null );
                    comboTurno.setSelectedIndex( 0 );
                    comboType.setSelectedIndex( 0 );
                    findCity.setRawValue( null );
                    intYear.setRawValue( null );
                    intSerie.setRawValue( null );
                }
                else {
                    if ( record.getType_status().equals( 1 ) == false ) {
                        showErrorMessage( "Esta prova já possui dupla digitação" );
                        return;
                    }
                    name.setFocus( true );
                    cityId.setValue( record.getCit_id_in() );
                    loadCity( cityId.getValue() );
                    school.setValue( record.getEscola() );
                    teacher.setValue( record.getInicial_professor() );
                    comboTurno.setValue( record.getTurno() );
                    comboType.setValue( record.getUrbano_rural() );
                    try {
                        intYear.setValue( Integer.parseInt( record.getAno_corrigido() ) );
                    }
                    catch ( Exception e ) {
                        intYear.setValue( 0 );
                    }
                }
            }
            catch ( ApplicationException e ) {
                showErrorMessage( e.getMessage() );
            }
        }
    }
}
