ALTER TABLE inep.inep_revisor
   ADD COLUMN rvs_ignore_bt boolean DEFAULT false;
COMMENT ON COLUMN inep.inep_revisor.rvs_ignore_bt
  IS 'Se true, ignorar este revisor em um novo processo de distribuição. Este recurso será usado geralmente quanto existe uma distribuição excepcional - ou seja - fora do processo normal. Por exemplo:
Envie por FTP 169 provas do posto de Santa Cruz de La Sierra, total de 676 arquivos, utilizei a internet aqui do evento, consegue me confirmar se recebeu, vou ficar aqui até às 19 horas.
Essas provas que você terá distribuir hoje, serão distribuídas para todos os corretores da escrita, com exceção de 3 corretores listados abaixo:
Gerson Rodrigues da Silva - Tarefa 1
Fernanda Costa Demier Rodrigues - Tarefa 3
Miriam Josie Kurcbaum Futer - Tarefa 4
Dois desses corretores terão que ir embora mais cedo e a corretora da tarefa 4 esta muito atrasada.
';
