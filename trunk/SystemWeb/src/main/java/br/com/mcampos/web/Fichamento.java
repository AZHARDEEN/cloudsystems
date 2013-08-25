package br.com.mcampos.web;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class Fichamento extends SelectorComposer<Window>
{
	@Wire
	Textbox assunto;

	@Wire
	Textbox autor;
	@Wire
	Textbox referencia;
	@Wire
	Textbox ficha;
	@Wire
	Textbox fonte;
	@Wire
	Combobox tipo;

	private static final long serialVersionUID = -1117069499642120024L;

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		assunto.setValue( "Inovação Tecnológica" );
		autor.setValue( "Ivan Rocha Neto" );
		fonte.setValue( "http://revistas.utfpr.edu.br/pb/index.php/revedutec-ct/article/viewFile/1014/606; Revista Educação e Tecnologia N. 2" );
		tipo.setSelectedIndex( 0 );
		referencia
				.setValue( "Schumpeter J. A-\"The Theory of Econômic Development\", 1934; "
						+
						"Jantsch, \"Design for Evolution: Self-Organization and Planning in the Life of Human Systems (The International Library of Systems Theory and Philosophy)\" "
						+
						"Rego P. M. \"C&T no Brasil: Políticas e Instituições - Secretaria de Ciência e Tecnologia\"" +
						"" );

		ficha.setValue( "O artigo apresenta os conceitos de inovação tecnológica, para, em seguida, apresentar suas relações " +
				"de interdependência com uma diversidade de fatores ( mercado, consumidores, valores sócios culturais, Estado, etc). " +
				"Um modelo de representação do sistema de inovação é proposto, incluindo o Conselho Nacional de Ciência e Tecnologia como um ator do " +
				"processo. Apresenta-se, então, a situação atual e prespectivas do sistema brasileiro de inovação, onde percebe-se " +
				"uma clara vinculação entre a inovação tecnológica, a capacidade inovativa das empresas e o desempenho da indústria brasileira. " +
				"Nota-se que a perspetiva competitiva-econômica, incluindo o cenário internacional, está ligada à capacidade de inovação do país. " +
				"Demonstra-se uma tendencia negativa quanto a inovação e, consequentemente, a balança comercial do Brasil. " +
				"Dentro deste cenário, aborda algumas possibilidades para reverter " +
				"tal quadro, bem como aponta algumas iniciativas governamentais (PADCT).\n " +
				"Já neste artigo, mostra-se a necessidade de intercâmbio de técnicos (neste caso leia-se técnico acadêmicos) brasileiros no " +
				"exterior, com a finalidade de melhorar a defasagem tecno-científico do país.\n\n" +
				"Resumo do autor: Este artigo desenvolve o conceito de sistema de inovação tecnológica, identificando seus atores, " +
				"processos e relações de interdependência, explorando as dimensões política, econômica e sócio-cultural da tecnologia. " +
				"A partir deste referencial, o autor analisa a situação do sistema nacional de inovação no Brasil e medidas são propostas " +
				"para melhorar a conectividade entre seus agentes, além de estratégias de aproveitamento dos programas e instrumentos de " +
				"fomento à capacitação tecnológica existentes. " );
	}

	@Listen( "onClick=#cmdCreate" )
	public void onAddRecord( Event evt )
	{
		assunto.setValue( "" );
		autor.setValue( "" );
		fonte.setValue( "" );
		tipo.setSelectedIndex( 0 );
		referencia.setValue( "" );
		ficha.setValue( "" );
	}

}
