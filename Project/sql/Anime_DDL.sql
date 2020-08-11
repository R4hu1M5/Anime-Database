drop database animeDB;
create database animeDB;
use animeDB;

create table GenreCatalog (
  catalog_id smallint(6),
  genre_name varchar(10),
  constraint pk_gencat primary key (catalog_id)
);

-- create table Administrator (
--   admin_id smallint(6) primary key,
--   admin_email varchar(20),
--   admin_username varchar(20),
--   admin_password varchar(20),
--   cat_id smallint(6),
--   constraint fk_admin foreign key (cat_id) references GenreCatalog (catalog_id)
-- );

create table User (
  user_id smallint(6),
  email_id varchar(20),
  username varchar(20),
  password varchar(20),
  constraint pk_user primary key (user_id)
  -- admin_ref smallint(6),
  -- constraint fk_user foreign key (admin_ref) references Administrator (admin_id)
);

create table Anime (
  anime_id smallint(6),
  anime_name varchar(20),
  genre_id smallint(6),
  genre_name varchar(20),
  constraint pk_anime primary key (anime_id),
  constraint fk_anime foreign  key (genre_id) references GenreCatalog (catalog_id)
);

create table UserToAnime (
  uid smallint(6),
  aid smallint(6),
  constraint fk_uta1 foreign key (uid) references User (user_id),
  constraint fk_uta2 foreign key (aid) references Anime (anime_id),
  constraint pk_uta primary key (uid, aid)
);
