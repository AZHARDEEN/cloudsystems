package br.com.mcampos.web.inep.controller.station;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

public class InterviewerController extends BaseStationController
{
	private static final long serialVersionUID = -8814974018344374047L;

	@Wire( "radiogroup" )
	private Radiogroup grade;

	@Wire
	private Label lbl5;
	@Wire
	private Label lbl4;
	@Wire
	private Label lbl3;
	@Wire
	private Label lbl2;
	@Wire
	private Label lbl1;
	@Wire
	private Label lbl0;

	@Override
	protected void cleanUp( )
	{
		super.cleanUp( );
		for ( Checkbox checkbox : this.elements ) {
			if ( checkbox.isChecked( ) ) {
				checkbox.setChecked( false );
			}
		}
		this.grade.setSelectedIndex( -1 );
	}

	@Wire( "checkbox" )
	private Checkbox[ ] elements;

	@Override
	protected boolean validate( )
	{
		if ( this.grade.getSelectedItem( ) == null ) {
			Messagebox.show( "Por favor, informe todos os items da avaliação - Nota da entrevista", "Nota da Entrevista", Messagebox.OK, Messagebox.ERROR );
			return false;
		}
		if ( this.validateElements( ) == false ) {
			Messagebox.show( "Por favor, informe 3(três) elementos provocadores", "Elementos Provocadores", Messagebox.OK, Messagebox.ERROR );
			return false;
		}
		return true;
	}

	@Override
	protected void proceed( )
	{
		int[ ] ids = new int[ 3 ];

		int nIndex = 0;
		for ( Checkbox item : this.elements ) {
			if ( item.isChecked( ) ) {
				ids[ nIndex++ ] = Integer.parseInt( (String) item.getValue( ) );
			}
			if ( nIndex >= 3 ) {
				break;
			}
		}
		int iGrade = Integer.parseInt( ( (String) this.grade.getSelectedItem( ).getValue( ) ) );
		this.getSession( ).setInterviewerInformation( this.getPrincipal( ), this.getCurrentSubscription( ), ids, iGrade );
	}

	public boolean validateElements( )
	{
		if ( this.elements == null ) {
			return false;
		}
		int nSize = 0;
		for ( Checkbox checkbox : this.elements ) {
			if ( checkbox.isChecked( ) ) {
				nSize++;
			}
			if ( nSize > 3 ) {
				return false;
			}
		}
		return nSize == 3;
	}

	private void setLabels( )
	{
		this.lbl5.setValue( "Demonstra autonomia e desenvoltura, contribuindo bastante para o desenvolvimento da interação. "
				+ "Apresenta fluência e variedade amplade vocabulário e de estruturas, com raras inadequações.\n"
				+ "Sua pronúncia é adequada e demonstra compreensão do fluxo natural da fala." );

		this.lbl4.setValue( "Demonstra autonomia e desenvoltura, contribuindo para o desenvolvimento da interação.\n"
				+ "Apresenta fluência e variedade ampla de vocabulário e de estruturas, com inadequações\nocasionais "
				+ "na comunicação. Sua pronúncia pode apresentar algumas inadequações.\nDemonstra compreensão"
				+ "do fluxo natural da fala. " );

		this.lbl3.setValue( "Contribui para o desenvolvimento da interação. Apresenta fluência, mas também algumas\n"
				+ "inadequações de vocabulário, estruturas e/ou pronúncia.\nDemonstra compreensão do fluxo natural da fala. " );

		this.lbl2.setValue( "Contribui para o desenvolvimento da interação. Apresenta poucas hesitações, com algumas\n"
				+ "interrupções no fluxo da conversa. Apresenta inadequações de vocabulário, estruturas e/ou\npronúncia."
				+ "Pode demonstrar alguns problemas de compreensão do fluxo da fala. " );

		this.lbl1.setValue( "Contribui pouco para o desenvolvimento da interação. Apresenta muitas pausas e hesitações,\n"
				+ "ocasionando interrupções no fluxo da conversa, ou apresenta alternância no uxo de fala entre\n"
				+ "língua portuguesa e outra língua. Apresenta muitas limitações e/ou inadequações de vocabulário,\n"
				+ "estruturas e/ou pronúncia. Demonstra problemas de compreensão do fluxo natural da fala. " );

		this.lbl0.setValue( "Raramente contribui para o desenvolvimento da interação. Apresenta pausas e hesitações\n"
				+ "muito frequentes que interrompem o fluxo da conversa, ou apresenta fluxo de fala em outra língua.\n"
				+ "Apresenta muitas limitações e/ou inadequações de vocabulário, estruturas e/ou pronúncia, \n"
				+ "que comprometem a comunicação.\nDemonstra problemas de compreensão de fala simplificada e pausada." );

	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		this.setLabels( );
	}

}
