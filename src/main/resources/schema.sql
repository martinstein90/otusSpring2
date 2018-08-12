
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
                    constraint fk_books_authors foreign key(author_id) references authors(id) on delete cascade,
                    constraint fk_books_genres foreign key(genre_id) references genres(id) on delete cascade);

drop table if exists comments;
create table comments(id int not null auto_increment,
                      comment varchar(100) not null,
                      book_id int,
                      primary key(id),
                      constraint fk_comments_books foreign key(book_id) references books(id) on delete cascade);


create unique index unicNameAuthor on authors(firstname, lastname);
create unique index unicTitleBook on books(title);
create unique index unicTitleGenre on genres(title);
