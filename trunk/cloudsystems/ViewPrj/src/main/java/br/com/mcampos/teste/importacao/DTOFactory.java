package br.com.mcampos.teste.importacao;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;

public class DTOFactory
{
	private static DTOFactory staticFactory;
	private static Map cache;

	private DTOFactory( )
	{
		cache = Collections.synchronizedMap( new HashMap( ) );
	}

	private ClassData getClassData( Class target )
	{
		ClassData cData = (ClassData) cache.get( target.getName( ) );
		try
		{
			if( cData == null )
			{
				Field[ ] fields = target.getDeclaredFields( );
				cData = new ClassData( target, fields );
				cache.put( target, cData );
			}
		}
		catch( Exception e )
		{

		}
		return cData;
	}

	public static DTOFactory getInstance( )
	{
		if( staticFactory == null ) {
			staticFactory = new DTOFactory( );
		}

		return staticFactory;
	}

	public void copy( Object toObject, Object fromObject )
	{
		ClassData toData = this.getClassData( toObject.getClass( ) );
		ClassData fromData = this.getClassData( fromObject.getClass( ) );

		for( Field toField : toData.arrFields )
		{
			Field fromField = (Field) fromData.mapFields.get( toField.getName( ) );
			if( fromField != null )
			{
				if( toField.isAccessible( ) == false ) {
					toField.setAccessible( true );
				}
				if( fromField.isAccessible( ) == false ) {
					fromField.setAccessible( true );
				}
				try {
					Object var;

					var = fromField.get( fromObject );
					toField.set( toObject, var );
				}
				catch( IllegalAccessException e ) {
					System.out.println( e.getMessage( ) );
				}
			}
		}
	}

	public static void main( String[ ] args )
	{
		PersonDTO dto = new PersonDTO( );
		Person entity = new Person( );

		entity.setFirstName( "Marcelo" );
		entity.setLastName( "Campos" );
		entity.setMotherName( "Maria de Fátima Campos" );
		entity.setFatherName( "Edmilson Benedito de Campos" );

		DTOFactory factory = DTOFactory.getInstance( );
		factory.copy( dto, entity );
		System.out.println( dto.getFirstName( ) );
		System.out.println( dto.getLastName( ) );
		System.out.println( dto.getMotherName( ) );
		System.out.println( dto.getFatherName( ) );
	}

	/**
	 * Inner Class that contains class data for the Transfer Object classes
	 */
	class ClassData
	{
		// Transfer Object Class
		public Class clsTransferObject;

		// Transfer Object fields
		public Field[ ] arrFields;

		public Map mapFields;

		// Constructor
		public ClassData( Class cls, Field[ ] fields )
		{
			this.clsTransferObject = cls;
			this.arrFields = fields.clone( );
			this.mapFields = new HashMap( );
			for( Field field : this.arrFields ) {
				this.mapFields.put( field.getName( ), field );
			}
		}
	}
}
