/*
STORED PROCEDURE RESPONS�VEL POR ATUALIZAR A RELA��O ENTRE IDADE E N�MERO DE M�DIA DE CADA TIPO COMPARTILHADA
CONFORME DESCRITO PELA TABELA midia.
*/

CREATE PROC sp_idademidia @nome VARCHAR(30), @tipo CHAR(1) AS -- � NECESS�RIO SABERMOS O NOME DO USU�RIO E O TIPO DE MIDIA

DECLARE @idade INT -- VARI�VEL QUE ARMAZENA A IDADE
DECLARE @valor INT -- VARI�VEL QUE ARMAZENA O VALOR ATUAL E EVENTUALMENTE O ATUALIZA

SELECT @idade = DATEDIFF(hour, (SELECT idade FROM Usuario WHERE nome = @nome) ,GETDATE())/8766 -- O VALOR DA IDADE � ATRIBU�DO EM COMPARA��O A DATA ATUAL

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
