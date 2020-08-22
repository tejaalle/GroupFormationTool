DELIMITER $$

DROP PROCEDURE IF EXISTS spFindUsersWithSystemRole $$

CREATE PROCEDURE spFindUsersWithSystemRole (
	IN role VARCHAR(10)
)
BEGIN
	SELECT id
    INTO @roleID
    FROM Role
    WHERE Role.role = role;
    
    SELECT userID as id
    FROM SystemRole
    WHERE SystemRole.roleID = @roleID;
END $$

DELIMITER ;