USE flood;

DELIMITER //
CREATE PROCEDURE flood_tables(n INT)
BEGIN
    WHILE n > 0
        DO
            SET @sql = CONCAT('create table if not exists table_', n, ' (id INT, request_time DATE, remote_addr VARCHAR(100), data VARCHAR(100))');
            PREPARE cmd FROM @sql;
            EXECUTE cmd;
            DEALLOCATE PREPARE cmd;
            SET n = n - 1;
        END WHILE;
END; //
DELIMITER ;
CALL flood_tables(100);