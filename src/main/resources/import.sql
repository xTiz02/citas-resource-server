# CONTRASENIA 1234
-- ============================================
-- ROLES
-- ============================================
INSERT INTO auth_role (name) VALUES ('ROLE_ADMIN'),('ROLE_DOCTOR'),('ROLE_RECEPTIONIST'),('ROLE_PATIENT');

-- ============================================
-- USERS
-- ============================================
INSERT INTO auth_user (username, password, account_locked, enabled, role_id) VALUES ('admin', '$2a$10$NRFP84zXJobHNEr1xlRzhOYb4lkY4ukRcG3oNMkexJAhqSGnX3pbu', 0, 1, 1),('recep', '$2a$10$GcXjFyWNnAGq4/wkiCQ2DO79c3bmeeqPo78zZmbRWSUK9mfQ46U5S', 0, 1, 2);

-- ============================================
-- OAUTH CLIENT: citas_app
-- ============================================
INSERT INTO client_app (client_id, client_secret, client_name, duration_in_minutes, require_proof_key) VALUES('citasApp','$2a$10$GcXjFyWNnAGq4/wkiCQ2DO79c3bmeeqPo78zZmbRWSUK9mfQ46U5S','Citas medicas app',36000,1);

INSERT INTO client_authentication_methods (client_id, authentication_method) VALUES (1, 'client_secret_basic');

INSERT INTO client_authorization_grant_type (client_id, authorization_grant_type) VALUES (1, 'authorization_code'),(1, 'refresh_token'),(1, 'client_credentials');

INSERT INTO client_redirect_uris (client_id, redirect_uri) VALUES (1, 'https://oauthdebugger.com/debug'),(1, 'http://localhost:5173/authorized');

INSERT INTO client_logout_redirect_uri (client_id, logout_redirect_uri) VALUES (1, 'http://localhost:5173/authorized?logout=logout_action');

INSERT INTO client_scope (client_id, scope) VALUES (1, 'openid');

-- ============================================
-- OAUTH CLIENT: demo app
-- ============================================
INSERT INTO client_app (client_id, client_secret, client_name, duration_in_minutes, require_proof_key) VALUES ('client','$2a$10$NRFP84zXJobHNEr1xlRzhOYb4lkY4ukRcG3oNMkexJAhqSGnX3pbu','Demo app',36000,1);

INSERT INTO client_authentication_methods (client_id, authentication_method) VALUES (2, 'client_secret_basic');

INSERT INTO client_authorization_grant_type (client_id, authorization_grant_type) VALUES(2, 'authorization_code'),(2, 'refresh_token'),(2, 'client_credentials');

INSERT INTO client_redirect_uris (client_id, redirect_uri) VALUES (2, 'http://localhost:5173/authorized');

INSERT INTO client_logout_redirect_uri (client_id, logout_redirect_uri) VALUES (2, 'http://localhost:5173/authorized?logout=logout_action');

INSERT INTO client_scope (client_id, scope) VALUES (2, 'openid');

-- ============================================
-- SCOPES COMENTADOS (opcional)
-- ============================================
-- "email", "profile", "address", "phone"