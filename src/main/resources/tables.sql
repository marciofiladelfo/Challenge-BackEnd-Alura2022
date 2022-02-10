create table incomes
(
    id              bigint not null auto_increment primary key,
    descricao       varchar(100),
    data_lancamento date,
    valor           double
);
create table expenses
(
    id              bigint not null auto_increment primary key,
    descricao       varchar(100),
    data_lancamento date,
    valor           double,
    category       varchar(100)
);

create table category
(
    id              bigint not null auto_increment primary key,
    nome       varchar(100)
);