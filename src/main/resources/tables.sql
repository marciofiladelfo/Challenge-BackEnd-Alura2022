create table incomes
(
    id              bigint not null auto_increment primary key,
    descricao       varchar(100) not null,
    data_lancamento date not null,
    valor           decimal(10,2) not null
);
create table expenses
(
    id              bigint not null auto_increment primary key,
    descricao       varchar(100) not null,
    data_lancamento date not null,
    valor           decimal(10,2) not null,
    category       varchar(100) not null
);