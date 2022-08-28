/*Criação do Banco de dados*/
create database locadora;

/*Seleção do Banco de dados*/
use locadora;

/*Criação das tabelas do banco de dados*/
create table filme (
id integer not null auto_increment,
nome varchar(40) not null,
primary key(id)
);

create table cliente (
id integer not null auto_increment,
nome varchar(40) not null,
telefone varchar(40) not null,
endereco varchar(50),
email varchar(50),
primary key(id)
);

create table locacao (
id integer not null auto_increment,
dt_emprestimo date not null,
dt_devolucao date not null,
observacao varchar(200),
id_cliente integer not null,
id_filme integer not null,
primary key(id),
foreign key(id_cliente) references cliente(id),
foreign key(id_filme) references filme(id)
);

/*Verificando as tabelas presentes no banco de dados*/
show tables;

/*Realizando o CRUD, Inserindo registros:*/
insert into 
filme (nome)
values
('Os vingadores'),
('Batman'),
('IT a coisa'),
('Aquamen');

insert into
cliente (nome, telefone, endereco, email)
values
('Gustavo Storch', '+55 47 98448-0062', 'Rio Cerro II', 'gustavo@gmail.com'),
('Igor Matheus', '+55 47 94838-3215', 'Barra do Rio cerro', 'igor@gmail.com'),
('Kayan Cioato', '+55 47 97848-4615', 'Jardim são luis', 'kayan@gmail.com'),
('Marlon Giese', '+55 47 93165-1577', 'Rio Cerro II', 'marlon@gmail.com');

insert into
locacao (dt_emprestimo, dt_devolucao, observacao, id_cliente, id_filme)
values
('2020-06-19', '2020-06-26', 'Cliente devolverá antes', 2, 3),
('2020-06-17', '2020-06-24', 'Cliente poderá renovar', 1, 2),
('2020-06-20', '2020-06-27', 'Cliente poderá renovar', 4, 1),
('2020-06-18', '2020-06-24', 'Cliente devolverá antes', 3, 4);

/*Realizando o CRUD, Consultando registros de uma tabela:*/
select nome from filme;
select * from filme;

select nome, telefone from cliente where endereco = 'Rio Cerro II';
select endereco, email from cliente where nome = 'Gustavo Storch';
select telefone, email from cliente where nome = 'Igor Matheus';
select telefone, endereco from cliente where nome = 'Kayan Cioato';
select * from cliente;

select * from locacao where day(dt_emprestimo) < 20;
select * from locacao where month(dt_devolucao) = 06;
select * from locacao;

/*Realizando o CRUD, Atualizando registros:*/
update filme
set nome = 'Homens e uma Sentença'
where id = 3 and nome = 'IT a coisa';

update cliente
set telefone = '+55 47 91534-0354',
endereco = 'Jaraguá do Sul'
where id = 4;

update locacao
set dt_devolucao = '2020-07-05',
	observacao = 'Filme renovado'
where id = 2 or id = 3; 

/*Realizando o CRUD, Deletando registros:*/
delete from locacao where id = 2 or id = 3;

delete from cliente where id = 4;

delete from filme where id = 2;

/*Consultas avançadas*/
select count(*) as "Número de locações no mês de junho" from locacao where month(dt_emprestimo) = 6;
select count(*) as "Clientes cadastrados que moram no Jardim são luis" from cliente where endereco = 'Jardim são luis';
select count(*) as "Filmes cadastrados" from filme;

select nome, dt_devolucao from cliente, locacao where cliente.id = locacao.id;
select nome, id_filme from cliente, locacao where cliente.id = locacao.id_filme;
select nome, dt_emprestimo from cliente, locacao where cliente.id = locacao.id;

select nome as "Nome dos clientes" from cliente where id = (select id from filme where nome = 'Os vingadores');
select nome as "Nome dos clientes" from cliente where id = (select id from filme where nome = 'Homens e uma sentença');
select nome as "Nome dos clientes" from cliente where id = (select id from filme where nome = 'Aquamen');