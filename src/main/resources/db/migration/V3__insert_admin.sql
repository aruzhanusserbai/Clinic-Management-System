INSERT INTO t_users (email, password, full_name)
VALUES ('aruzhan.usserbay@gmail.com', '$2a$10$FrXwMETHQyn6srhM5cWmQejNq3p9SymNgBQTJSESzUPDgcuWhGjfG', 'Aruzhan Usserbay');

INSERT INTO t_user_permissions (user_id, permission_id)
VALUES (1, (SELECT id FROM t_permissions WHERE permission='ADMIN'));
