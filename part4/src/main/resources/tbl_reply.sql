create table tbl_reply (
    rno number(10, 0),
    bno number(10, 0) not null,
    reply varchar2(1000) not null,
    replyer varchar2(50) not null,
    replyDate date default sysdate,
    updateDate date DEFAULT sysdate
);

create SEQUENCE seq_reply;

alter table tbl_reply add constraint pk_reply PRIMARY KEY (rno);

alter table tbl_reply add CONSTRAINT fk_reply_board FOREIGN KEY (bno) REFERENCES tbl_board (bno);

COMMIT;