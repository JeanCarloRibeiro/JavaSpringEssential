INSERT INTO TB_CATEGORY (description) VALUES ('Curso');
INSERT INTO TB_CATEGORY (description) VALUES ('Oficina');

INSERT INTO TB_PARTICIPANT (name, email) VALUES ('José Silva', 'jose@gmail.com');
INSERT INTO TB_PARTICIPANT (name, email) VALUES ('Tiago Faria', 'tiago@gmail.com');
INSERT INTO TB_PARTICIPANT (name, email) VALUES ('Maria do Rosário', 'maria@gmail.com');
INSERT INTO TB_PARTICIPANT (name, email) VALUES ('Teresa Silva', 'teresa@gmail.com');

INSERT INTO TB_ACTIVITY (name, description, price, category_id) VALUES ('Curso de HTLML', 'Aprenda HTML de forma prática', 80.00, 1);
INSERT INTO TB_ACTIVITY (name, description, price, category_id) VALUES ('Oficina de Github', 'Controle versões de seus projetos', 50.00, 2);

INSERT INTO TB_BLOCK (inicio, fim, activity_id) VALUES (TIMESTAMP WITH TIME ZONE '2017-09-25T08:00:00Z', TIMESTAMP WITH TIME ZONE '2017-09-25T11:00:00Z', 1);
INSERT INTO TB_BLOCK (inicio, fim, activity_id) VALUES (TIMESTAMP WITH TIME ZONE '2017-09-25T14:00:00Z', TIMESTAMP WITH TIME ZONE '2017-09-25T18:00:00Z', 2);
INSERT INTO TB_BLOCK (inicio, fim, activity_id) VALUES (TIMESTAMP WITH TIME ZONE '2017-09-26T08:00:00Z', TIMESTAMP WITH TIME ZONE '2017-09-26T11:00:00Z', 2);

INSERT INTO TB_ACTIVITY_PARTICIPANT (activity_id, participant_id) VALUES (1, 1);
INSERT INTO TB_ACTIVITY_PARTICIPANT (activity_id, participant_id) VALUES (2, 1);
INSERT INTO TB_ACTIVITY_PARTICIPANT (activity_id, participant_id) VALUES (1, 2);
INSERT INTO TB_ACTIVITY_PARTICIPANT (activity_id, participant_id) VALUES (1, 3);
INSERT INTO TB_ACTIVITY_PARTICIPANT (activity_id, participant_id) VALUES (2, 3);
INSERT INTO TB_ACTIVITY_PARTICIPANT (activity_id, participant_id) VALUES (2, 4);