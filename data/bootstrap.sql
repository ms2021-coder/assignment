DROP TABLE IF EXISTS Person;
CREATE TABLE Person (
    id TEXT PRIMARY KEY,
    name TEXT    
);

DROP TABLE IF EXISTS Movie;
CREATE TABLE Movie (
    id TEXT PRIMARY KEY,
    name TEXT    
);

DROP TABLE IF EXISTS Role;
CREATE TABLE Role (
    id TEXT PRIMARY KEY,
    name TEXT    
);

DROP TABLE IF EXISTS WorkedOn;
CREATE TABLE WorkedOn (
    id TEXT PRIMARY KEY,
    person_id TEXT,
    movie_id TEXT,
    role_id TEXT
);

INSERT INTO Person(id, name)
VALUES
    ('1', 'Troy McClure'),
    ('2', 'Rainier Wolfcastle'),
    ('3', 'Steve Zissou'),
    ('4', 'Antonio Calculon, Sr');

INSERT INTO Movie(id, name)
VALUES
    ('1', 'Earwigs: Eww!'),
    ('2', 'Man vs. Nature: The Road to Victory'),
    ('3', 'The Contrabulous Fabtraption of Professor Horatio Hufnagel'),
    ('4', 'McBain I'),
    ('5', 'McBain II'),
    ('6', 'McBain III'),
    ('7', 'McBain IV'),
    ('8', 'Radioactive Man'),
    ('9', 'The Jaguar Shark'),
    ('10', 'All My Circuits');


INSERT INTO Role(id, name)
VALUES
    ('1', 'starred'),
    ('2', 'directed'),
    ('3', 'stars');

INSERT INTO WorkedOn(id, person_id, movie_id, role_id)
VALUES
    ('1', '1', '1', '1'),
    ('2', '1', '2', '1'),
    ('3', '1', '7', '1'),
    ('4', '1', '3', '1'),
    ('5', '2', '4', '1'),
    ('6', '2', '5', '1'),
    ('7', '2', '6', '1'),
    ('8', '2', '7', '1'),
    ('9', '2', '8', '1'),
    ('10', '3', '9', '2'),
    ('11', '4', '10', '3');
