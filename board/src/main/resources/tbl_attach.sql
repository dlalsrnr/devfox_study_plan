create table tbl_attach (
    uuid VARCHAR2(100) NOT NULL,
    uploadPath VARCHAR2(200) NOT NULL,
    fileName VARCHAR2(100) NOT NULL,
    filetype char(1) DEFAULT 'I',
    bno number(10, 0)
);
alter table tbl_attach
add CONSTRAINT pk_attach primary key (uuid);
alter table tbl_attach
add CONSTRAINT fk_board_attach foreign key (bno) references tbl_board(bno);
commit;