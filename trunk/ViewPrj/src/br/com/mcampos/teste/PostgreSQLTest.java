package br.com.mcampos.teste;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLTest
{
    public PostgreSQLTest()
    {
        super();
    }

    public static void main( String[] args )
    {
        //PostgreSQLTest postgreSQLTest = new PostgreSQLTest();

        System.out.println( "Checking if Driver is registered with DriverManager." );

        try {
            Class.forName( "org.postgresql.Driver" );
        }
        catch ( ClassNotFoundException cnfe ) {
            System.out.println( "Couldn't find the driver!" );
            System.out.println( "Let's print a stack trace, and exit." );
            cnfe.printStackTrace();
            System.exit( 1 );
        }

        System.out.println( "Registered the driver ok, so let's make a connection." );

        Connection c = null;

        try {
            // The second and third arguments are the username and password,
            // respectively. They should be whatever is necessary to connect
            // to the database.
            c =
  DriverManager.getConnection( "jdbc:postgresql://127.0.0.1:5432/db_cloud?user=cloud&password=cloud", "cloud", "cloud" );
        }
        catch ( SQLException se ) {
            System.out.println( "Couldn't connect: print out a stack trace and exit." );
            se.printStackTrace();
            System.exit( 1 );
        }

        if ( c != null )
            System.out.println( "Hooray! We connected to the database!" );
        else
            System.out.println( "We should never get here." );
    }
}
