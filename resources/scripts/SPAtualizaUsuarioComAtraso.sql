DELIMITER $$
CREATE PROCEDURE SPAtualizaUsuariosComAtraso ()
BEGIN
  SET @IdUsuario := (Select COUNT(*) from Usuario U 
					join Emprestimo E ON U.Id = E.IdUsuario 
					where E.DataDevolucaoEfetiva is null);
          -- group by U.Nome, U.Sobrenome, U.Telefone);
          
  IF ((Select @IdUsuario) is not null) THEN UPDATE Usuario U SET U.FlPossuiAtraso = 1 where U.Id = (Select @IdUsuario);
  END IF;
END $$
DELIMITER ;

/*update Usuario U SET U.FlPossuiAtraso = 1 where U.Id IN (
select VU.Id from VwUsuario VU 
					join Emprestimo E ON VU.Id = E.IdUsuario 
					where E.DataDevolucaoEfetiva is null);
          -- group by U.Nome, U.Sobrenome, U.Telefone;
          
          
          
ALTER TABLE USUARIO ADD COLUMN FlPossuiAtraso int not null;

update Emprestimo SET DataDevolucaoEfetiva = CURDATE();

select * from usuario where FlPossuiAtraso <> 1*/

create database bibliotecafabiano2;

CREATE VIEW VwUsuario AS
SELECT * FROM Usuario;

select * from VwUsuario;

