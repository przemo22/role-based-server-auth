--liquibase formatted sql
--changeset mysql:02 context:test
INSERT INTO roles(name) VALUES('ADMIN');

--changeset mysql:03 context:test
INSERT INTO permissions (role_id, permission_type) VALUES ((SELECT id FROM roles WHERE name = 'ADMIN' LIMIT 1), 'LIST_USERS');
INSERT INTO permissions (role_id, permission_type) VALUES ((SELECT id FROM roles WHERE name = 'ADMIN' LIMIT 1), 'CREATE_USERS');
INSERT INTO permissions (role_id, permission_type) VALUES ((SELECT id FROM roles WHERE name = 'ADMIN' LIMIT 1), 'DELETE_USERS');
INSERT INTO permissions (role_id, permission_type) VALUES ((SELECT id FROM roles WHERE name = 'ADMIN' LIMIT 1), 'EDIT_USERS');

--changeset mysql:04 context:test
INSERT INTO users(username, password, role_id) VALUES('admin', '$2y$10$xjIHMCNWm8G8KmqwRsibdOUNDocdS.cXOdocW8alePk996Nko2zjW', (SELECT id FROM roles WHERE name = 'ADMIN' LIMIT 1))
