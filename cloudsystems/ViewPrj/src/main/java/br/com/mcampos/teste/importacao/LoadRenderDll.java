package br.com.mcampos.teste.importacao;

public class LoadRenderDll
{

	public static void main( String[ ] args )
	{
		System.loadLibrary( "renderdll" );
		System.loadLibrary( "NativeRendering" );
	}

}
