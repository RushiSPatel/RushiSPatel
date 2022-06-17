
use finalproject;

/*Creating family tree database*/
create table FamilyTree (id int primary key,individualName varchar(90) not null,dateOfBirth date,locationOfBirth varchar(90), dateOfDeath date, locationOfDeath varchar(90), gender varchar(90), occupation varchar(90), referencesToSourceMaterial varchar(90), notes varchar(90), partner varchar(90), dissolution varchar(90));

/*Creating family tree database*/
create table MediaArchive (id int primary key not null, fileName varchar(90) not null, dateOfMedia date, location varchar(90));

/*Creating table to map references of individual*/
create table ReferencesOfIndividual (id int , individualName varchar(90), referencesToSourceMaterial varchar(90), foreign key(id) references familytree(id));

/*Creating table to map notes of individual*/
create table NotesOfIndividual (id int, individualName varchar(90), notes varchar(90), foreign key(id) references familytree(id));

/*Creating table to record child*/
create table Children (parent int, child int,foreign key(parent) references familytree(id),foreign key(child) references familytree(id));

/*Creating table to record partnering relations*/
create table Partnering (partner1 int, partner2 int,foreign key(partner1) references familytree(id),foreign key(partner2) references familytree(id));

/*Creating table to record dissolution relations*/
create table Dissolutions (partner1 int, partner2 int,foreign key(partner1) references familytree(id),foreign key(partner2) references familytree(id));

/*Creating table mediatag*/
create table mediatag (id int,fileName varchar(90), tag varchar(90), dateOfMedia date,foreign key(id) references MediaArchive(id));

/*media people updated created table*/
create table mediapeople (id int,fileName varchar(90), individualName varchar(90), dateOfMedia date,foreign key(id) references MediaArchive(id),foreign key(id) references familytree(id));

