create table developer
(
    id      bigint       not null auto_increment,
    version integer      not null,
    name    varchar(255) not null,
    visa    varchar(255) not null,
    dte_i   datetime(6)  not null,
    usr_i   varchar(20)  not null,
    dte_u   datetime(6)  not null,
    usr_u   varchar(20)  not null,
    primary key (id)
);

create table project
(
    id             bigint       not null auto_increment,
    version        integer      not null,
    finishing_date date,
    name           varchar(255) not null,
    dte_i          datetime(6)  not null,
    usr_i          varchar(20)  not null,
    dte_u          datetime(6)  not null,
    usr_u          varchar(20)  not null,
    primary key (id)
);

create table task
(
    id          bigint       not null auto_increment,
    version     integer      not null,
    deadline    date,
    name        varchar(255) not null,
    assignee_id bigint,
    project_id  bigint       not null,
    status      varchar(100) not null,
    dte_i       datetime(6)  not null,
    usr_i       varchar(20)  not null,
    dte_u       datetime(6)  not null,
    usr_u       varchar(20)  not null,
    primary key (id)
);

alter table task add constraint assignee_fk foreign key (assignee_id) references developer (id);
alter table task add constraint project_fk foreign key (project_id) references project (id);
alter table task
    add constraint status_ck check ( status in ('open', 'analyzed', 'accepted', 'implemented', 'closed') );

create table task_logwork
(
    id         bigint      not null auto_increment,
    version    integer     not null,
    spend_time bigint      not null,
    author_id  bigint      not null,
    task_id    bigint      not null,
    dte_i      datetime(6) not null,
    usr_i      varchar(20) not null,
    dte_u      datetime(6) not null,
    usr_u      varchar(20) not null,
    primary key (id)
);
alter table task_logwork add constraint author_fk foreign key (author_id) references developer (id);
alter table task_logwork add constraint task_fk foreign key (task_id) references task (id);
