insert into project (id, name, finishing_date, version, dte_i, usr_i, dte_u, usr_u) values (10001, 'Boostup program', '2022-08-08', 1, sysdate(), 'abc', sysdate(), 'abc');
insert into developer (id, name, visa, version, dte_i, usr_i, dte_u, usr_u) values (10001, 'Alice', 'alc', 1, sysdate(), 'abc', sysdate(), 'abc');
insert into task (id, name, deadline, assignee_id, project_id, status, version, dte_i, usr_i, dte_u, usr_u) values (10001, 'Setup code base', '2021-09-20', 10001, 10001, 'open', 1, sysdate(), 'abc', sysdate(), 'abc');
