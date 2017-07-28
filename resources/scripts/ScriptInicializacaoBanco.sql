-- region Criação do banco de dados
CREATE DATABASE IF NOT EXISTS BIBLIOTECAFABIANO2;

USE BIBLIOTECAFABIANO2;

-- region Criação de registros de inicialização.
INSERT INTO RELATORIO (Id, Descricao, FlObrigaDatas, FlObrigaUsuario, Nome, FlObrigaClassificacao)
SELECT  
-10,'Neste relatório serão demonstrados todos os usuários marcados como\ninativos no sistema.',0,1,'Relação de todos os usuários inativos',0 ,
-9,'Neste relatório serão demonstrados todos os usuários cadastrados no\nsistema.',0,1,'Relação de todos os usuários cadastrados',0 ,
-8,'Neste relatório serão demonstradas todas as classificações de livros\ncadastradas no sistema.',0,0,'Relação de classificações de livros cadastrados',0 ,
-7,'Neste relatório serão demonstrados todos os livros que foram marcados\ncomo doados e que não se encontram mais disponíveis no acervo.',0,0,'Relação de livros doados',0 ,
-6,'Neste relatório serão demonstrados todos os livros que foram marcados\ncomo removidos do acervo',0,0,'Relação de livros removidos',0 ,
-5,'Neste relatório serão demonstrados todos os livros que ainda não possuem \ndata de devolução efetiva informada (estão em atraso), juntamente com o \nnome do usuário que emprestou o livro e a data da devolução prevista.',0,0,'Relação de livros em atraso',0 ,
-4,'Geração do arquivo com as etiquetas de identificação dos livros. Podem ser \ngeradas todas as etiquetas de uma só vez (não recomendado) e etiquetas \npor classificação.',1,0,'Relação de etiquetas',0 ,
-3,'Neste relatório serão demonstrados todos os empréstimos realizados em \numa determinada faixa de tempo. Serão exibidos o nome do usuário e os \nlivros que foram emprestados, ordenados por data do empréstimo.',1,0,'Relação de empréstimos por intervalo de tempo',0,
-2,'Neste relatório serão demonstrados todos os empréstimos realizados por um \nusuário informado na hora da geração do relatório. Este relatório pode ser \nutilizado para conferência da quantidade de exemplares físicos em posse do \nusuário em questão.',0,1,'Relação de empréstimos por usuário',0 ,
-1,'Neste relatório serão demonstrados todos os livros cadastrados no sistema,\nindependente de estarem emprestados, removidos, doados ou indisponíveis\nno acervo. Este relatório pode ser utilizado para realização de revisão\nperiódica da quantidade de exemplares cadastrados, além da comparação\ncom o acervo (realização de levantamento de patrimônio).',0,0,'Relação de todos os livros cadastrados no sistema',0 
FROM RELATORIO
having (COUNT(*) > 0) ;


INSERT INTO USUARIO (DDD, DicaSenha, FlAdministrador, FlInativo, FlPossuiAtraso, NomeCompleto, NomeUsuarioAcesso, Senha, Telefone)
VALUES(043, 'Admin', 1, 0, 0, 'Administrador', 'Admin', 'e3afed0047b08059d0fada10f400c1e5',  00000000);

-- region Criação da view para relatório de Livros cadastrados
drop view `LivrosCadastrados`;
CREATE VIEW `VwLivrosCadastrados` (TomboPatrimonial , Titulo , Autor, Classificacao) AS
    SELECT 
        Livro.TomboPatrimonial,
        Livro.Titulo + " - " +  Livro.Subtitulo,
        Livro.NomeAutor
    FROM
        Livroclassificacao
	INNER JOIN Classificacao.Descricao AS Classificacao 
    ON Classificacao.Id = Livro.IdClassificacao;

-- region Criação do procedimento que atualiza a Flag de atraso de um usuário
DROP PROCEDURE IF EXISTS SPAtualizaUsuariosComAtraso;

DELIMITER $$
CREATE PROCEDURE SPAtualizaUsuariosComAtraso (IN Id_Usuario INT)
BEGIN
  SET @PossuiAtraso := (select count(E.Id) from livro L
	join emprestimo E 
	inner join usuario U
	ON E.TomboPatrimonial = L.TomboPatrimonial
	AND E.IdUsuario = U.Id
	where E.DataDevolucaoEfetiva is null
    AND E.DataDevolucaoPrevista < CURDATE()
	and U.Id = Id_Usuario);
    
    IF((Select @PossuiAtraso > 0)) THEN UPDATE Usuario U SET U.FlPossuiAtraso = 1 where U.Id = Id_Usuario;
    ELSE UPDATE Usuario U SET U.FlPossuiAtraso = 0 where U.Id = Id_Usuario;
    END IF;
END $$
DELIMITER ;