INSERT INTO
    fileinfo(fno, fbno, dir, oriname, savename, len)
VALUES(
    (SELECT NVL(MAX(fno) + 1, 1000001) FROM fileinfo),
    (
        SELECT
            max(bno)
        FROM
            board, member
        WHERE
            bmno = mno
            AND id = ?
    ),
    '/img/upload/', ?, ?, ?
);

SELECT
    max(bno)
FROM
    board
WHERE
    bmno = (
                SELECT mno FROM member WHERE id = 'jiwoo'
            )
;

SELECT
    max(bno)
FROM
    board, member
WHERE
    bmno = mno
    AND id = 'jiwoo'
;

commit;