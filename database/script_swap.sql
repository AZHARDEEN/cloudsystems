DECLARE @inscricao, @t1, @t2, @result, @c1, @c2, @c3, @c4, @result2;

--comentário de teste
set @inscricao = '201201003487';
set @t1 = 3;
set @t2 = 4;


set @result = select isc_id_ch, tsk_id_in, col_seq_in, ids_id_in, dis_grade_in from inep.inep_distribution where isc_id_ch = '@inscricao'  and tsk_id_in in ( @t1, @t2 ) order by tsk_id_in;
print @result[0];
print @result[1];
print @result[2];
print @result[3];

IF LINES(@RESULT) > 4 
BEGIN
	print 'Numero de linhas maior que o permitido @result[1][1]!!!!!';
END
ELSE 
BEGIN
if @result[0][3] <> @result[1][3] and @result[0][3] <> 1 and @result[0][3] <> 2 and @result[1][3] <> 1 and @result[0][3] <> 2
begin
	print 'Favor verificar o caso da tarefa @result[1][1]!!!!!';
end
else
begin
	if @result[2][3] <> @result[3][3]
	begin
		print 'Favor verificar o caso da tarefa @result[2][1]!!!!!';
	end
	else
	begin
		BEGIN TRANSACTION;
			set @c1 = @result[0][2];
			set @c2 = @result[1][2];
			set @c3 = @result[2][2];
			set @c4 = @result[3][2];
			update inep.inep_distribution set col_seq_in = @c3, ids_id_in = 1, dis_grade_in = null
			where isc_id_ch = '@inscricao' and col_seq_in = @c1 and tsk_id_in = @t1;

			update inep.inep_distribution set col_seq_in = @c4, ids_id_in = 1, dis_grade_in = null
			where isc_id_ch = '@inscricao' and col_seq_in = @c2 and tsk_id_in = @t1;

			update inep.inep_distribution set col_seq_in = @c1, ids_id_in = 1, dis_grade_in = null
			where isc_id_ch = '@inscricao' and col_seq_in = @c3 and tsk_id_in = @t2;

			update inep.inep_distribution set col_seq_in = @c2, ids_id_in = 1, dis_grade_in = null
			where isc_id_ch = '@inscricao' and col_seq_in = @c4 and tsk_id_in = @t2;
			set @result2 = select isc_id_ch, tsk_id_in, col_seq_in, ids_id_in, dis_grade_in 
						from inep.inep_distribution where isc_id_ch = '@inscricao'  and tsk_id_in in ( @t1, @t2 ) order by tsk_id_in;
			print 'Resultado apos o processamento: ';
			print @result2[0];
			print @result2[1];
			print @result2[2];
			print @result2[3];
		END TRANSACTION;
	end
end
end


