-- Create tables for Semester 2 2022 CTG example ER Model
PRAGMA foreign_keys = OFF;
drop table if exists LGA;
drop table if exists PopulationStatistics;
drop table if exists LTHC;
drop table if exists SchoolCompletion;
drop table if exists WeeklyIncome;
drop table if exists SocioeconomicOutcome;
drop table if exists Persona;
drop table if exists PersonaAttribute;
PRAGMA foreign_keys = ON;

CREATE TABLE LGA (
    lga_code16        INTEGER NOT NULL,
    lga_name16        TEXT NOT NULL,
    lga_type16        CHAR (2),
    area_sqkm         DOUBLE,
    latitude          DOUBLE,
    longitude         DOUBLE,
    PRIMARY KEY (lga_code16)
);

CREATE TABLE PopulationStatistics (
    lga_code16        INTEGER NOT NULL,
    indigenous_status TEXT NOT NULL,
    sex               CHAR (1) NOT NULL,
    age               TEXT NOT NULL,
    count             INTEGER NOT NULL,
    PRIMARY KEY (lga_code16, indigenous_status, sex, age)
    FOREIGN KEY (lga_code16) REFERENCES LGA(lga_code16)
);

CREATE TABLE LTHC (
    lga_code16        INTEGER NOT NULL,
    indigenous_status TEXT NOT NULL,
    sex               CHAR (1) NOT NULL,
    condition         TEXT NOT NULL,
    count             INTEGER NOT NULL,
    PRIMARY KEY (lga_code16, indigenous_status, sex, condition)
    FOREIGN KEY (lga_code16) REFERENCES LGA(lga_code16)
);

CREATE TABLE SchoolCompletion (
    lga_code16        INTEGER NOT NULL,
    indigenous_status TEXT NOT NULL,
    sex               CHAR (1) NOT NULL,
    school_year       TEXT NOT NULL,
    count             INTEGER NOT NULL,
    PRIMARY KEY (lga_code16, indigenous_status, sex, school_year)
    FOREIGN KEY (lga_code16) REFERENCES LGA(lga_code16)
);

CREATE TABLE WeeklyIncome (
    lga_code16        INTEGER NOT NULL,
    indigenous_status TEXT NOT NULL,
    sex               CHAR (1) NOT NULL,
    income_bracket    TEXT NOT NULL,
    count             INTEGER NOT NULL,
    PRIMARY KEY (lga_code16, indigenous_status, sex, income_bracket)
    FOREIGN KEY (lga_code16) REFERENCES LGA(lga_code16)
);

CREATE TABLE SocioeconomicOutcome (
    id_SO             INTEGER NOT NULL,
    title             TEXT NOT NULL,
    descriptions_SO   TEXT NOT NULL,
    PRIMARY KEY (id_SO)
);

CREATE TABLE Persona (
    persona_name      TEXT NOT NULL,
    image_path        TEXT NOT NULL,
    PRIMARY KEY (persona_name)
 
);

CREATE TABLE PersonaAttribute (
    id_PA             INTEGER PRIMARY KEY AUTOINCREMENT,
    persona_name      TEXT NOT NULL,
    attribute         TEXT NOT NULL,
    descriptions_PA   LONGTEXT NOT NULL,
    FOREIGN KEY (persona_name) REFERENCES Persona(persona_name)
);