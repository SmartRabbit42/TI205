create table Rooms(
	codRoom int primary key not null,
	name varchar(30) not null,
	password varchar(20) not null,
	time datetime not null
)

create table Users(
	codUser int primary key not null,
	nickname varchar(30) not null,
	password varchar(20) not null
)

create table RoomUser(
	codRoomUser int primary key not null,
	codRoom int not null,
	codUser int not null,
	constraint fkRoom foreign key(codRoom) references Rooms(codRoom),
	constraint fkUser foreign key(codUser) references Users(codUser)
)

select * from Users
select * from Rooms
select * from RoomUser