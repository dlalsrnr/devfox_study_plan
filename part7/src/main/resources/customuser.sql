create table tbl_member(
    userid varchar2(50) NOT NULL PRIMARY KEY,
    userpw varchar2(100) NOT NULL,
    username varchar2(100) NOT NULL,
    regdate date DEFAULT sysdate,
    updatedate date DEFAULT sysdate,
    enabled char(1) DEFAULT '1'
);
create TABLE tbl_member_auth (
    userid VARCHAR2(50) NOT NULL,
    auth VARCHAR2(50) NOT NULL,
    constraint fk_member_auth foreign key(userid) REFERENCES tbl_member(userid)
);