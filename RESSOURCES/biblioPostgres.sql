
CREATE SEQUENCE Auteur_id_seq;

CREATE TABLE Auteur (
 id        integer NOT NULL DEFAULT nextval('Auteur_id_seq'),
 nom       VARCHAR( 20 ) NOT NULL ,
 prenom    VARCHAR( 20 ) ,
 telephone VARCHAR( 10 ) NOT NULL,
 email     VARCHAR( 60 ) ,
 PRIMARY KEY ( id )
);

ALTER SEQUENCE Auteur_id_seq
OWNED BY Auteur.id;



CREATE SEQUENCE Livre_id_seq;

CREATE TABLE Livre (
 id         integer NOT NULL DEFAULT nextval('Livre_id_seq'),
 id_auteur  INT ,
 titre      VARCHAR( 50 ) NOT NULL ,
 nb_pages   INT     NOT NULL ,
 categorie  VARCHAR( 20 ),
 PRIMARY KEY ( id ) ,
 CONSTRAINT fk_id_auteur    
    FOREIGN KEY (id_auteur)  
    REFERENCES auteur(id)  
    ON DELETE SET NULL    
);

ALTER SEQUENCE Livre_id_seq
OWNED BY Livre.id;

CREATE SEQUENCE Page_id_seq;

CREATE TABLE Page (
 id         integer NOT NULL DEFAULT nextval('Page_id_seq'),
 id_livre  INT ,
 texte      VARCHAR( 500 ) NOT NULL ,
 nb_lignes   INT NOT NULL ,
 police  VARCHAR( 20 ),
 PRIMARY KEY ( id ) ,
 CONSTRAINT fk_id_livre    
    FOREIGN KEY (id_livre)  
    REFERENCES livre(id)  
    ON DELETE SET NULL    
);

ALTER SEQUENCE Page_id_seq
OWNED BY Page.id;

INSERT INTO Auteur (prenom,nom,telephone,email) VALUES
('Bruce', 'Eckel','0605040302', 'thinking@me.net'),
('Antonio', 'Goncalves', '0102030405', null),
('Petter', 'Haggar', '0655443322', 'petharg@hotmail.com'),
('Claude', 'Delannoy', '0677889900', 'claude@delanooy.com');

INSERT INTO Livre (id_auteur, titre, nb_pages, categorie) VALUES
(1, 'Thinking in Java', 320, 'java'),
(1, 'Thinking in C++', 640, 'cpp'),
(2, 'Les cahiers du programmeur Java EE', '240', 'java'),
(2, 'Beginning Java EE 7', 120, 'javaee'),
(3, 'Mieux programmer en Java', 540, 'java'),
(4, 'Exercices en Java', 184, 'java'),
(4, 'Initiation à la programmation', 350, 'algo'),
(4, 'C++ Guide complet', 842, 'cpp');

INSERT INTO Page (id_livre, texte, nb_lignes, police) VALUES
(1, 'Un, Dos, Tres', 7800, 'Arial'),
(1, 'Un, Deux, Trois', 6400, 'Time New Roman'),
(2, 'One, Two, Three', 65, 'Arial'),
(2, 'Mäuse, Gitarren, Delphine', 1200, 'Arial Black'),
(3, 'Bir, Iki, Üç', 5400, 'Comic Sans'),
(4, 'Eén, Twee, Drie', 1840, 'MS'),
(4, 'Adin, Dva, Tri', 3500, 'Time New Roman'),
(5, 'Um, Dois, Três', 8420, 'Arial');