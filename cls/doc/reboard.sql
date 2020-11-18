INSERT INTO
    reboard(bno, b_mno, body, upno)
VALUES(
    (
        SELECT NVL(MAX(bno) + 1, 10001) FROM reboard
    ),
    (
        SELECT
            mno
        FROM
            member
        WHERE
            id = 'euns'
    ),
    '지속적인 관심이 필요합니다!',
    10003
);


INSERT INTO
    reboard(bno, b_mno, body, upno)
VALUES(
    (
        SELECT NVL(MAX(bno) + 1, 10001) FROM reboard
    ),
    (
        SELECT
            mno
        FROM
            member
        WHERE
            id = 'jiwoo'
    ),
    '이번주도 풋살 콜~~!',
    10002
);


INSERT INTO
    reboard(bno, b_mno, body, upno)
VALUES(
    (
        SELECT NVL(MAX(bno) + 1, 10001) FROM reboard
    ),
    (
        SELECT
            mno
        FROM
            member
        WHERE
            id = 'sun'
    ),
    '살살 합시다! 콜~~~!',
    10022
);

commit;


SELECT
    *
FROM
    (SELECT
        rownum rno, r.*
    FROM
        (SELECT
            bno, b_mno mno, id, ano, afile sname, body, wdate, upno, (level - 1) step, connect_by_root bno as rbno
        FROM
            reboard r, member m, avatar a
        WHERE
            b_mno = mno
            AND avt = ano
            AND r.isshow = 'Y'
        START WITH  -- 계층이 시작될 조건을 부여하는 절
            upno IS NULL
        CONNECT BY  -- 이전과의 관계를 서술하는 절
            PRIOR bno = upno
        order by
            wdate desc) r
        )
--        ORDER SIBLINGS BY   -- 같은 계층인 경우 정렬기준
--            wdate desc) r)
WHERE
    rno BETWEEN 4 AND 6
;

update 
    reboard
set
    isshow = 'Y'
where
    isshow = 'N'
;

commit;