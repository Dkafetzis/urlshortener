
    create table RedirectUrl (
        urlid uuid not null,
        userid uuid,
        redirectUrl bytea,
        primary key (urlid)
    );

    create table RedirectUser (
        userid uuid not null,
        username varchar(255),
        primary key (userid)
    );

    alter table if exists RedirectUrl 
       add constraint FKrkmq5yvbrll6p4i6kjjdye784 
       foreign key (userid) 
       references RedirectUser;
