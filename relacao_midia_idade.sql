/*
STORED PROCEDURE RESPONSÁVEL POR ATUALIZAR A RELAÇÃO ENTRE IDADE E NÚMERO DE MÍDIA DE CADA TIPO COMPARTILHADA
CONFORME DESCRITO PELA TABELA midia.
*/

CREATE PROC sp_idademidia @nome VARCHAR(30), @tipo CHAR(1) AS -- É NECESSÁRIO SABERMOS O NOME DO USUÁRIO E O TIPO DE MIDIA

DECLARE @idade INT -- VARIÁVEL QUE ARMAZENA A IDADE
DECLARE @valor INT -- VARIÁVEL QUE ARMAZENA O VALOR ATUAL E EVENTUALMENTE O ATUALIZA

SELECT @idade = DATEDIFF(hour, (SELECT idade FROM Usuario WHERE nome = @nome) ,GETDATE())/8766 -- O VALOR DA IDADE É ATRIBUÍDO EM COMPARAÇÃO A DATA ATUAL

IF @tipo = 'v' BEGIN -- CASO SEJA UM VIDEO
	SELECT @valor = video FROM midia WHERE maxi >= @idade AND mini <= @idade -- PEGA O VALOR ATUAL
	SET @valor = @valor + 1	-- INCREMENTA EM 1
	UPDATE midia SET video = @valor WHERE maxi >= @idade AND mini <= @idade -- ATUALIZA O VALOR
END

IF @tipo = 'i' BEGIN -- CASO SEJA UMA IMAGEM
	SELECT @valor = imagem FROM midia WHERE maxi >= @idade AND mini <= @idade -- PEGA O VALOR ATUAL
	SET @valor = @valor + 1	-- INCREMENTA EM 1
	UPDATE midia SET imagem = @valor WHERE maxi >= @idade AND mini <= @idade -- ATUALIZA O VALOR
END

IF @tipo = 'a' BEGIN -- CASO SEJA UM AUDIO
	SELECT @valor = audio FROM midia WHERE maxi >= @idade AND mini <= @idade -- PEGA O VALOR ATUAL
	SET @valor = @valor + 1	-- INCREMENTA EM 1
	UPDATE midia SET audio = @valor WHERE maxi >= @idade AND mini <= @idade -- ATUALIZA O VALOR
END

/*
BASTA CONSULTAR OS VALORES
*/

SELECT * FROM midia
