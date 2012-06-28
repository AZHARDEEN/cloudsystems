package br.com.mcampos.web.inep.utils;

public class TasksDTO
{
	private String taskId;
	private Integer corretor;
	private Integer nota;
	private Boolean anulado;

	public String getTaskId( )
	{
		return this.taskId;
	}

	public void setTaskId( String taskId )
	{
		this.taskId = taskId;
	}

	public Integer getCorretor( )
	{
		return this.corretor;
	}

	public void setCorretor( Integer corretor )
	{
		this.corretor = corretor;
	}

	public Integer getNota( )
	{
		if ( this.nota == null ) {
			this.nota = 0;
		}
		return this.nota;
	}

	public void setNota( Integer nota )
	{
		this.nota = nota;
	}

	public Boolean getAnulado( )
	{
		return this.anulado;
	}

	public void setAnulado( Boolean anulado )
	{
		this.anulado = anulado;
	}

	@Override
	public String toString( )
	{
		return this.taskId;
	}

}
