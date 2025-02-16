alter table
    if exists RedirectUrl
    alter column urlid
        set data type varchar(255);
alter table
    if exists RedirectUrl
    alter column redirectUrl
          set data type bytea;