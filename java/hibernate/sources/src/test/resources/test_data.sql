insert into project (id, name, finishing_date, version, dte_i, usr_i, dte_u, usr_u) values (10002, 'Boostup program hibernate', '2021-31-12', 1, sysdate(), 'sys', sysdate(), 'sys');
insert into project (id, name, finishing_date, version, dte_i, usr_i, dte_u, usr_u) values (10003, 'Boostup program spring', '2021-31-12', 1, sysdate(), 'sys', sysdate(), 'sys');

insert into developer (id, name, visa, version, dte_i, usr_i, dte_u, usr_u) values (10002, 'Alice', 'alc', 1, sysdate(), 'sys', sysdate(), 'sys');
insert into developer (id, name, visa, version, dte_i, usr_i, dte_u, usr_u) values (10003, 'Bob', 'bob', 1, sysdate(), 'sys', sysdate(), 'sys');
insert into developer (id, name, visa, version, dte_i, usr_i, dte_u, usr_u) values (10004, 'Admin', 'adm', 1, sysdate(), 'sys', sysdate(), 'sys');

insert into task (name, deadline, status, project_id, assignee_id, version, dte_i, usr_i, dte_u, usr_u) values ('Analyze spec', '2021-09-20', 'open', 10002, 10002, 1, sysdate(), 'sys', sysdate(), 'sys');
insert into task (name, deadline, status, project_id, assignee_id, version, dte_i, usr_i, dte_u, usr_u) values ('Setup code base', '2021-09-20', 'open', 10002, 10003, 1, sysdate(), 'sys', sysdate(), 'sys');
insert into task (name, deadline, status, project_id, assignee_id, version, dte_i, usr_i, dte_u, usr_u) values ('US1', '2021-09-20', 'open', 10002, 10004, 1, sysdate(), 'sys', sysdate(), 'sys');
insert into task (name, deadline, status, project_id, assignee_id, version, dte_i, usr_i, dte_u, usr_u) values ('US2', '2021-09-20', 'open', 10002, 10001, 1, sysdate(), 'sys', sysdate(), 'sys');
insert into task (name, deadline, status, project_id, assignee_id, version, dte_i, usr_i, dte_u, usr_u) values ('US3', '2021-09-20', 'open', 10002, 10004, 1, sysdate(), 'sys', sysdate(), 'sys');

insert into task (name, deadline, status, project_id, assignee_id, version, dte_i, usr_i, dte_u, usr_u) values ('Analyze spec', '2021-09-20', 'open', 10003, 10002, 1, sysdate(), 'sys', sysdate(), 'sys');
insert into task (name, deadline, status, project_id, assignee_id, version, dte_i, usr_i, dte_u, usr_u) values ('Setup code base', '2021-09-20', 'open', 10003, 10003, 1, sysdate(), 'sys', sysdate(), 'sys');
insert into task (name, deadline, status, project_id, assignee_id, version, dte_i, usr_i, dte_u, usr_u) values ('US1', '2021-09-20', 'open', 10003, 10004, 1, sysdate(), 'sys', sysdate(), 'sys');
insert into task (name, deadline, status, project_id, assignee_id, version, dte_i, usr_i, dte_u, usr_u) values ('US2', '2021-09-20', 'open', 10003, 10001, 1, sysdate(), 'sys', sysdate(), 'sys');
insert into task (name, deadline, status, project_id, assignee_id, version, dte_i, usr_i, dte_u, usr_u) values ('US3', '2021-09-20', 'open', 10003, 10004, 1, sysdate(), 'sys', sysdate(), 'sys');
