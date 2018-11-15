DBCC CHECKIDENT('Usuario', RESEED, 0)
DBCC CHECKIDENT('Mensagem', RESEED, 0)
DBCC CHECKIDENT('SalaUsuario', RESEED, 0)
DBCC CHECKIDENT('Sala', RESEED, 0)

delete from Mensagem
delete from SalaUsuario
delete from Sala
delete from Usuario

select * from Sala
select * from SalaUsuario
select * from Mensagem
select * from Usuario

insert into Sala values('global', '');