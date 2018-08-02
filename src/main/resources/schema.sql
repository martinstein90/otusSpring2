drop table if exists authors;
create table authors(	id int not null auto_increment,
						firstname varchar(32) not null,
						lastname varchar(32) not null,
                        primary key(id));

drop table if exists genres;
create table genres(id int not null auto_increment,
					title varchar(32) not null,
                    primary key(id));

drop table if exists books;
create table books(	id int not null auto_increment,
					title varchar(32) not null,
                    author_id int,
                    genre_id int,
                    primary key(id),
                    constraint fk_books_authors foreign key(author_id) references authors(id),
                    constraint fk_books_genres foreign key(genre_id) references genres(id));


create unique index unicNameAuthor on authors(firstname, lastname);
create unique index unicTitleBook on books(title);
create unique index unicTitleGenre on genres(title);
