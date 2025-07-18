# CONTRASENIA 1234
-- ============================================
-- ROLES
-- ============================================
INSERT INTO auth_role (name) VALUES ('ROLE_ADMIN'),('ROLE_DOCTOR'),('ROLE_RECEPTIONIST'),('ROLE_PATIENT');

-- ============================================
-- USERS
-- ============================================
INSERT INTO auth_user (username, password, account_locked, enabled, role_id) VALUES ('admin', '$2a$10$NRFP84zXJobHNEr1xlRzhOYb4lkY4ukRcG3oNMkexJAhqSGnX3pbu', 0, 1, 1),('recep', '$2a$10$GcXjFyWNnAGq4/wkiCQ2DO79c3bmeeqPo78zZmbRWSUK9mfQ46U5S', 0, 1, 2);
-- patient
INSERT INTO auth_user (username, password, account_locked, enabled, role_id) VALUES ('12345678', '$2a$10$NRFP84zXJobHNEr1xlRzhOYb4lkY4ukRcG3oNMkexJAhqSGnX3pbu', 0, 1, 4);
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
-- OAUTH CLIENT: patient app
-- ============================================
INSERT INTO client_app (client_id, client_secret, client_name, duration_in_minutes, require_proof_key) VALUES ('patientApp','$2a$10$NRFP84zXJobHNEr1xlRzhOYb4lkY4ukRcG3oNMkexJAhqSGnX3pbu','Patient app',36000,0);

INSERT INTO client_authentication_methods (client_id, authentication_method) VALUES (3, 'client_secret_basic');

INSERT INTO client_authorization_grant_type (client_id, authorization_grant_type) VALUES(3, 'authorization_code'),(3, 'refresh_token'),(3, 'client_credentials');

INSERT INTO client_redirect_uris (client_id, redirect_uri) VALUES (3, 'http://localhost:3000');

INSERT INTO client_logout_redirect_uri (client_id, logout_redirect_uri) VALUES (3, 'http://localhost:3000/authorized?logout=logout_action');

INSERT INTO client_scope (client_id, scope) VALUES (3, 'openid');

-- ============================================
-- SCOPES COMENTADOS (opcional)
-- ============================================
-- "email", "profile", "address", "phone"

INSERT INTO user_patient (id,user_id,family_code, tipo_documento, numero_documento, apellido_paterno, apellido_materno,nombres, fecha_nacimiento, numero_celular, correo_electronico, genero,peso, altura, direccion, contacto_emergencia, telefono_emergencia,created_at, updated_at) VALUES (1,3,'FAMILY0001', 'dni', '12345678', 'Collazos', 'Martinez','Jorge Luis', '2002-01-15', '987654321', 'jorge.collazos@email.com', 'masculino','70', '1.75', 'Av. Principal 123, Lima, Perú', 'María Collazos', '987123456',now(), now());
INSERT INTO family_member_patient (id,user_id,family_code,parentesco, tipo_documento, numero_documento, apellido_paterno, apellido_materno,nombres, fecha_nacimiento, numero_celular, correo_electronico, genero, created_at, updated_at) VALUES (1,1,'FAMILY0001','Otro', 'dni', '12345678', 'Collazos', 'Martinez','Jorge Luis', '2002-01-15', '987654321', 'jorge.collazos@email.com', 'masculino',now(), now());
-- Esposa
INSERT INTO family_member_patient (id, user_id, family_code, parentesco, tipo_documento, numero_documento,apellido_paterno, apellido_materno, nombres, fecha_nacimiento,numero_celular, correo_electronico, genero, created_at, updated_at) VALUES (2, 0, 'FAMILY0001', 'Esposo/a', 'dni', '87654321', 'Lopez', 'Garcia','María Elena', '2003-05-10', '987321654', 'maria.elena@email.com', 'femenino',now(), now());

-- Hijo
INSERT INTO family_member_patient (id, user_id, family_code, parentesco, tipo_documento, numero_documento,apellido_paterno, apellido_materno, nombres, fecha_nacimiento,numero_celular, correo_electronico, genero, created_at, updated_at) VALUES (3, 0, 'FAMILY0001', 'Hijo/a', 'dni', '11223344', 'Collazos', 'Lopez','Andrés Javier', '2020-08-25', '987456123', 'andres.collazos@email.com', 'masculino',now(), now());

-- Madre
INSERT INTO family_member_patient (id, user_id, family_code, parentesco, tipo_documento, numero_documento,apellido_paterno, apellido_materno, nombres, fecha_nacimiento,numero_celular, correo_electronico, genero, created_at, updated_at) VALUES (4, 0, 'FAMILY0001', 'Padre/Madre', 'dni', '55667788', 'Martinez', 'Rojas','Luisa Carmen', '1970-11-03', '987654987', 'luisa.martinez@email.com', 'femenino',now(), now());

-- Hermano
INSERT INTO family_member_patient (id, user_id, family_code, parentesco, tipo_documento, numero_documento,apellido_paterno, apellido_materno, nombres, fecha_nacimiento,numero_celular, correo_electronico, genero, created_at, updated_at) VALUES (5, 0, 'FAMILY0001', 'Hermano/a', 'dni', '99887766', 'Collazos', 'Martinez','Carlos Eduardo', '1998-03-12', '987789321', 'carlos.collazos@email.com', 'masculino',now(), now());

-- Abuelo
INSERT INTO family_member_patient (id, user_id, family_code, parentesco, tipo_documento, numero_documento,apellido_paterno, apellido_materno, nombres, fecha_nacimiento,numero_celular, correo_electronico, genero, created_at, updated_at) VALUES (6, 0, 'FAMILY0001', 'Abuelo/a', 'dni', '33445566', 'Collazos', 'Salazar','Julián Antonio', '1950-06-01', '987963741', 'julian.collazos@email.com', 'masculino',now(), now());

-- Otro
INSERT INTO family_member_patient (id, user_id, family_code, parentesco, tipo_documento, numero_documento,apellido_paterno, apellido_materno, nombres, fecha_nacimiento,numero_celular, correo_electronico, genero, created_at, updated_at) VALUES (7, 0, 'FAMILY0001', 'Otro', 'dni', '44556677', 'Sanchez', 'Perez','Ana Gabriela', '1995-09-15', '987111222', 'ana.sanchez@email.com', 'femenino',now(), now());
