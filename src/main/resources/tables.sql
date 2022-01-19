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
    valor           double
);