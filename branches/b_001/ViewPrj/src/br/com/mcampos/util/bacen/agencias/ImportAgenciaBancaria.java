package br.com.mcampos.util.bacen.agencias;


import br.com.mcampos.sysutils.SysUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.StringTokenizer;


public class ImportAgenciaBancaria
{
    private String cnpj;
    private String bankName;
    private String segment;
    private Integer agency;
    private String agencyName;
    private String address;
    private String bairro;
    private String cep;
    private String municipio;
    private String state;
    private String initDate;
    private String telefone;


    public ImportAgenciaBancaria()
    {
        super();
    }

    public static void main( String[] args )
    {
        ImportAgenciaBancaria importAgenciaBancaria = new ImportAgenciaBancaria();
        try {
            importAgenciaBancaria.importFile( "t:/temp/201007AGENCIAS.csv" );
        }
        catch ( Exception e ) {
            System.out.println( e.getMessage() );
        }
    }

    public void importFile( String filename ) throws FileNotFoundException, IOException
    {
        File file = new File( filename );
        if ( file.exists() == false )
            return;
        FileReader reader = new FileReader( file );
        BufferedReader br = new BufferedReader( reader );
        String line;
        Integer nIndex = 0;

        while ( ( line = br.readLine() ) != null ) {
            nIndex++;
            if ( nIndex <= 1 )
                continue;
            StringTokenizer token = new StringTokenizer( line, ";" );
            while ( token.hasMoreTokens() ) {
                cnpj = token.nextToken() + "/" + token.nextToken() + "-" + token.nextToken();
                bankName = token.nextToken().trim();
                segment = token.nextToken().trim();
                agency = Integer.parseInt( token.nextToken() );
                agencyName = token.nextToken().trim();
                getAddress( token );
                initDate = getToken( token );
                telefone = getToken( token ) + getToken( token );
            }
        }
        br.close();
        reader.close();
    }

    private void getAddress( StringTokenizer token )
    {
        String aux;
        address = getToken( token );
        aux = getToken( token );
        if ( SysUtils.isEmpty( aux ) == false )
            address = address + " - " + aux;
        bairro = getToken( token );
        cep = getToken( token );
        municipio = getToken( token );
        state = getToken( token );
    }


    private String getToken( StringTokenizer token )
    {
        String aux = null;
        aux = token.nextToken();
        if ( SysUtils.isEmpty( aux ) )
            return aux;
        return aux.trim();
    }
}
