package br.com.mcampos.sysutils;

public class RandomString
{
	public RandomString( )
	{
		super( );
	}

	public static String randomstring( int lo, int hi )
	{
		int n = rand( lo, hi );
		byte b[] = new byte[ n ];
		for ( int i = 0; i < n; i++ ) {
			b[ i ] = (byte) rand( 'a', 'z' );
		}
		return new String( b );
	}

	private static int rand( int lo, int hi )
	{
		java.util.Random rn = new java.util.Random( );
		int n = hi - lo + 1;
		int i = rn.nextInt( ) % n;
		if ( i < 0 ) {
			i = -i;
		}
		return lo + i;
	}

	public static String randomstring( )
	{
		return randomstring( 10, 15 );
	}

	/**
	 * @param args
	 */
	public static void main( String[ ] args )
	{
		System.out.println( randomstring( ) );

	}

}