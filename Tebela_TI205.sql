create table Rooms(
	codRoom int primary key not null,
	name varchar(30) not null,
	password varchar(20) not null,
	time datetime not null
)

drop table Users
drop table RoomUser
drop table Mensagens

create table Users(
	codUser int primary key not null,
	nickname varchar(30) not null,
	passwordHash ntext not null,
	salt ntext not null
)

create table RoomUser(
	codRoomUser int primary key not null,
	codRoom int not null,
	codUser int not null,
	constraint fkRoom foreign key(codRoom) references Rooms(codRoom),
	constraint fkUser foreign key(codUser) references Users(codUser)
)

create table Mensagens(
	codMessage int primary key not null,
	messageHash ntext not null,
	codUser int not null,
	codRoom int not null,
	constraint fkUser1 foreign key(codUser) references Users(codUser),
	constraint fkRoom1 foreign key(codroom) references Rooms(codRoom)
)

select * from Mensagens
select * from Users
select * from Rooms
select * from RoomUser
