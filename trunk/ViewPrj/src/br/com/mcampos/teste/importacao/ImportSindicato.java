package br.com.mcampos.teste.importacao;

import br.com.mcampos.dto.address.AddressDTO;
import br.com.mcampos.dto.address.AddressTypeDTO;
import br.com.mcampos.dto.address.CityDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.UserContactDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.dto.user.attributes.CivilStateDTO;
import br.com.mcampos.dto.user.attributes.ContactTypeDTO;
import br.com.mcampos.dto.user.attributes.DocumentTypeDTO;
import br.com.mcampos.dto.user.attributes.GenderDTO;
import br.com.mcampos.dto.user.attributes.TitleDTO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.sql.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import javax.ejb.EJBException;


public class ImportSindicato
{
    //RegisterLocator locator;


    public ImportSindicato()
    {
        super();
        //locator = new RegisterLocator();
    }

    public String[] splitName( String name )
    {
        String splitted[] = name.split( " " );
        String firstName = null, lastName = null, middleName = null;
        int nIndex;


        for ( nIndex = 0; nIndex < splitted.length; nIndex++ ) {
            if ( nIndex == 0 )
                firstName = splitted[ nIndex ];
            else if ( nIndex == splitted.length - 1 )
                lastName = splitted[ nIndex ];
            else {
                if ( middleName == null || middleName.isEmpty() )
                    middleName = splitted[ nIndex ];
                else
                    middleName += " " + splitted[ nIndex ];
            }
        }

        splitted = new String[ 3 ];
        splitted[ 0 ] = firstName;
        splitted[ 1 ] = middleName;
        splitted[ 2 ] = lastName;

        return splitted;
    }


    public CivilStateDTO getCivilState( String civilState )
    {
        CivilStateDTO dto = new CivilStateDTO( 2, null );

        /*
         *
         * Estado Civil
            Casado(a)
            Divorciado(a)
            Separado(a)
            Solteiro(a)
            Viúvo(a)
         */
        if ( civilState.equals( "Casado(a)" ) )
            dto.setId( 2 );
        else if ( civilState.equals( "Divorciado(a)" ) )
            dto.setId( 4 );
        else if ( civilState.equals( "Separado(a)" ) )
            dto.setId( 7 );
        else if ( civilState.equals( "Solteiro(a)" ) )
            dto.setId( 1 );
        else if ( civilState.equals( "Viúvo(a)" ) )
            dto.setId( 5 );
        return dto;
    }

    public GenderDTO getGender()
    {
        GenderDTO dto = new GenderDTO();

        dto.setId( 1 );
        return dto;
    }

    public TitleDTO getTitle()
    {
        TitleDTO dto = new TitleDTO();

        dto.setId( 1 );
        return dto;
    }

    public CityDTO getCity( String cidade, String estado, String bairro )
    {
        CityDTO dto = new CityDTO();

        if ( estado.equals( "GO" ) ) {
            if ( cidade.equals( "CIDADE OCIDENTAL" ) )
                dto.setId( 942 );
            else if ( cidade.length() >= 10 &&
                      ( cidade.substring( 0, 10 ).equals( "VALPARAIZO" ) || cidade.substring( 0, 10 ).equals( "VALPARAISO" ) ) )
                dto.setId( 1115 );
            else if ( cidade.equals( "LUZIANIA" ) || bairro.equals( "LUZIANIA" ) )
                dto.setId( 1009 );
            else if ( cidade.equals( "NOVO GAMA" ) )
                dto.setId( 1041 );
            else if ( cidade.equals( "PLANALTINA" ) )
                dto.setId( 1061 );
            else if ( cidade.equals( "GOIANIA" ) )
                dto.setId( 971 );
            else if ( cidade.equals( "PEDREGAL" ) )
                dto.setId( 1115 );
            else if ( cidade.equals( "AGUAS LINDAS" ) )
                dto.setId( 885 );
            else if ( cidade.equals( "FORMOSA" ) )
                dto.setId( 966 );
        }
        if ( dto.getId() == null || dto.getId() == 0 )
            dto.setId( 801 );
        return dto;
    }

    public CityDTO getCity()
    {
        CityDTO dto = new CityDTO();
        dto.setId( 801 );
        return dto;
    }


    public AddressDTO getAddress( String[] address )
    {
        CityDTO city;
        String zip;
        String hood;

        if ( address[ 9 ].equals( "DF" ) )
            city = getCity();
        else
            city = getCity( address[ 8 ], address[ 9 ], address[ 7 ] );

        hood = address[ 7 ].trim();
        if ( hood.isEmpty() )
            hood = address[ 8 ].trim();
        zip = address[ 10 ].replaceAll( "[.-]", "" );
        AddressDTO dto = new AddressDTO();
        dto.setAddressType( new AddressTypeDTO( 1, null ) );
        dto.setCity( city );
        dto.setAddress( address[ 6 ] );
        dto.setDistrict( hood );
        dto.setComment( address[ 9 ] + " - " + address[ 8 ] );
        dto.setZip( zip );
        return dto;
    }


    public Timestamp getTimestamp( String birthDate )
    {
        SimpleDateFormat df = new SimpleDateFormat( "dd/MM/yyyy" );
        Timestamp birth;
        Date dtBirth;

        if ( birthDate != null && birthDate.length() < 10 )
            return null;
        birthDate = birthDate.substring( 0, 10 ).trim();
        try {
            dtBirth = df.parse( birthDate );
            birth = new Timestamp( dtBirth.getTime() );
        }
        catch ( ParseException e ) {
            birth = new Timestamp( new Date().getTime() );
        }

        return birth;
    }


    protected void addDocument( PersonDTO dto, String document, int documentType )
    {
        String convertedDocument;


        if ( document == null || document.isEmpty() )
            return;
        convertedDocument = document.replaceAll( "[ .-]", "" );
        /*
        if ( getLocator().documentExists( convertedDocument ) == false ) {
            UserDocumentDTO doc;

            doc = new UserDocumentDTO ();
            doc.setDocumentType( new DocumentTypeDTO ( documentType, null, null ) );
            doc.setCode( convertedDocument );
            doc.setAdditionalInfo( "Conteudo Oringinal: " + document );
            dto.add ( doc );
        }
        else
            addDocument ( dto, document + "$", documentType );
        */
    }


    public static void main( String[] args )
    {
        ImportSindicato importSindicato = new ImportSindicato();
        BufferedReader readbuffer;
        BufferedWriter out;
        String strRead;
        String splitarray[], splitnome[];
        PersonDTO dto;
        int nLine = 0;
        String part = "";
        String nome, comment;

        String path = "F:\\sindicato\\";


        try {
            readbuffer = new BufferedReader( new FileReader( path + "filiados.txt" ) );
            out = new BufferedWriter( new FileWriter( path + "rejeitados.txt" ) );
            while ( ( strRead = readbuffer.readLine() ) != null ) {
                nLine++;

                if ( nLine == 1 )
                    continue;
                if ( part.isEmpty() == false )
                    strRead = part + " " + strRead;
                splitarray = strRead.split( ";" );
                if ( splitarray.length < 60 )
                    part = strRead;
                else
                    part = "";

                for ( int nIndex = 0; nIndex < splitarray.length; nIndex++ ) {
                    splitarray[ nIndex ] = splitarray[ nIndex ].trim();
                }

                try {
                    splitnome = importSindicato.splitName( splitarray[ 1 ] );
                    nome = splitarray[ 1 ];
                    comment = splitarray[ 20 ].isEmpty() ? "Importado da base de dados do sindicato" : splitarray[ 20 ];
                    String data = splitarray[ 32 ];
                    String firstName = splitnome[ 0 ].isEmpty() ? null : splitnome[ 0 ];
                    String middleName = ( splitnome[ 1 ] != null && splitnome[ 1 ].isEmpty() ) ? null : splitnome[ 1 ];
                    String lastName = splitnome[ 2 ].isEmpty() ? null : splitnome[ 2 ];
                    String fatherName = splitarray[ 31 ].isEmpty() ? null : splitarray[ 31 ];
                    String motherName = splitarray[ 30 ].isEmpty() ? null : splitarray[ 30 ];

                    dto = new PersonDTO();
                    dto.setName( nome );
                    dto.setNickName( nome );
                    dto.setComment( comment );
                    dto.setBirthDate( importSindicato.getTimestamp( data ) );
                    dto.setFirstName( firstName );
                    dto.setMiddleName( middleName );
                    dto.setLastName( lastName );
                    dto.setFatherName( fatherName );
                    dto.setMotherName( motherName );
                    dto.setCivilState( importSindicato.getCivilState( splitarray[ 33 ] ) );
                    dto.setGender( importSindicato.getGender() );
                    dto.setTitle( importSindicato.getTitle() );

                    dto.add( importSindicato.getAddress( splitarray ) );

                    if ( nLine == 4 ) {
                        int a = 0;
                    }
                    importSindicato.addDocument( dto, splitarray[ 0 ], 8 );
                    importSindicato.addDocument( dto, splitarray[ 2 ], 7 );
                    importSindicato.addDocument( dto, splitarray[ 15 ], 1 );
                    importSindicato.addDocument( dto, splitarray[ 16 ], 2 );
                    importSindicato.addDocument( dto, splitarray[ 18 ], 4 );

                    if ( splitarray[ 11 ].length() > 0 )
                        dto.add( new UserContactDTO( new ContactTypeDTO( 1, null, null, null ),
                                                     splitarray[ 11 ].replaceAll( "[ .-]", "" ),
                                                     "Conteudo Oringinal: " + splitarray[ 11 ] ) );
                    if ( splitarray[ 12 ].length() > 0 && splitarray[ 11 ].equals( splitarray[ 12 ] ) == false )
                        dto.add( new UserContactDTO( new ContactTypeDTO( 2, null, null, null ),
                                                     splitarray[ 12 ].replaceAll( "[ .-]", "" ),
                                                     "Conteudo Oringinal: " + splitarray[ 12 ] ) );
                    if ( splitarray[ 13 ].length() > 0 && splitarray[ 13 ].equals( splitarray[ 12 ] ) == false &&
                         splitarray[ 13 ].equals( splitarray[ 11 ] ) == false )
                        dto.add( new UserContactDTO( new ContactTypeDTO( 3, null, null, null ),
                                                     splitarray[ 13 ].replaceAll( "[ .-]", "" ),
                                                     "Conteudo Oringinal: " + splitarray[ 13 ] ) );
                    if ( splitarray[ 14 ].length() > 0 )
                        dto.add( new UserContactDTO( new ContactTypeDTO( 4, null, null, null ), splitarray[ 14 ], null ) );

                    try {
                        //importSindicato.getLocator().add ( dto );
                    }
                    catch ( EJBException e ) {
                        System.out.println( "Erro ao incluir a linha " + nLine + "\n" +
                                strRead + "\n" );
                        System.out.println( e.getMessage() + "\n" );
                        try {
                            out.write( strRead + "\n" );
                        }
                        catch ( Exception exp ) {
                            exp = null;
                        }
                    }
                }
                catch ( Exception e ) {
                    System.out.println( "Erro ao incluir a linha " + nLine + "\n" +
                            strRead + "\n" );
                    System.out.println( e.getMessage() + "\n" );
                    try {
                        out.write( strRead + "\n" );
                    }
                    catch ( Exception exp ) {

                    }
                }
            }
            readbuffer.close();
        }
        catch ( FileNotFoundException e ) {
            return;
        }
        catch ( IOException e ) {
            return;
        }
    }
}
