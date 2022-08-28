create database locadora;

use locadora;

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

show tables;
desc filme;
desc cliente;
desc locacao;
select * from locacao;