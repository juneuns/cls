-- 게시판 테이블 생성
CREATE TABLE board(
    bno NUMBER(5)
        CONSTRAINT BRD_NO_PK PRIMARY KEY,
    title VARCHAR2(50 CHAR)
        CONSTRAINT BRD_TITLE_NN NOT NULL,
    body VARCHAR2(4000)
        CONSTRAINT BRD_BODY_NN NOT NULL,
    bmno NUMBER(4)
        CONSTRAINT BRD_MNO_FK REFERENCES member(mno)
        CONSTRAINT BRD_MNO_NN NOT NULL,
    bdate DATE default sysdate
        CONSTRAINT BRD_DATE_NN NOT NULL,
    bclick NUMBER(6) DEFAULT 0
        CONSTRAINT BRD_CLICK_NN NOT NULL,
    bisshow CHAR(1) DEFAULT 'Y'
        CONSTRAINT BRD_SHOW_CK CHECK(bisshow IN('Y', 'N'))
        CONSTRAINT BRD_SHOW_NN NOT NULL
);

-- 파일 정보테이블 생성
CREATE TABLE fileInfo(
    fno NUMBER(7)
        CONSTRAINT FI_NO_PK PRIMARY KEY,
    fbno NUMBER(5)
        CONSTRAINT FI_BNO_FK REFERENCES board(bno)
        CONSTRAINT FI_BNO_NN NOT NULL,
    oriname VARCHAR2(50 CHAR)
        CONSTRAINT FI_ONAME_NN NOT NULL,
    savename VARCHAR2(50 CHAR)
        CONSTRAINT FI_SNAME_UK UNIQUE
        CONSTRAINT FI_SNAME_NN NOT NULL,
    dir VARCHAR2(50 CHAR)
        CONSTRAINT FI_DIR_NN NOT NULL,
    len NUMBER
        CONSTRAINT FI_SIZE_NN NOT NULL,
    fdate DATE DEFAULT sysdate
        CONSTRAINT FI_DATE_NN NOT NULL,
    fcount NUMBER(8) DEFAULT 0
        CONSTRAINT FI_CNT_NN NOT NULL,
    fisshow CHAR(1) DEFAULT 'Y'
        CONSTRAINT FI_SHOW_CK CHECK(fisshow IN('Y', 'N'))
        CONSTRAINT FI_SHOW_NN NOT NULL
);


-- 게시판 더밎데이터 입력
INSERT INTO
    board(bno, bmno, title, body)
VALUES(
    (SELECT NVL(MAX(bno) + 1, 10001) FROM board ),
    (SELECT mno FROM member WHERE id='jiwoo'),
    'Test1', 'Test1.....'
);

INSERT INTO
    board(bno, bmno, title, body)
VALUES(
    (SELECT NVL(MAX(bno) + 1, 10001) FROM board ),
    (SELECT mno FROM member WHERE id='chan'),
    'Test2', 'Test2.....'
);

INSERT INTO
    board(bno, bmno, title, body)
VALUES(
    (SELECT NVL(MAX(bno) + 1, 10001) FROM board ),
    (SELECT mno FROM member WHERE id='jjang'),
    'Test3', 'Test3.....'
);

INSERT INTO
    board(bno, bmno, title, body)
VALUES(
    (SELECT NVL(MAX(bno) + 1, 10001) FROM board ),
    (SELECT mno FROM member WHERE id='jang'),
    'Test4', 'Test4.....'
);
rollback;


SELECT
    *
FROM
    (SELECT
        rownum rno, b.*
    FROM
        (
            SELECT
                bno, id, title, bdate, bclick
            FROM
                board, member
            WHERE
                mno = bmno
                AND bisshow = 'Y'
            order by
                bdate DESC
        ) b)
WHERE
    rno BETWEEN ? AND ?
;

select
    *
from
(select
    rownum rno, m.*
from
    (select
        *
    from
        member
    order by
        joindate
    ) m)
where
    rno between 4 and 6
;


SELECT
    COUNT(*) CNT
FROM
    MEMBER
WHERE
    name LIKE ('김' || '%') -- '김%'
;


select
    bno, title, body, bdate, fno, oriname, savename
from
    board, fileinfo
where
    bno = fbno
    and bno = 10021
;
