DELIMITER $$

DROP PROCEDURE IF EXISTS spCreateUser $$

CREATE PROCEDURE spCreateUser (
	IN bannerID VARCHAR(20),
    IN password VARCHAR(76),
    IN firstName VARCHAR(100),
    IN lastName VARCHAR(100),
    IN email VARCHAR(320),
    OUT id BIGINT
)
BEGIN
	INSERT INTO User(bannerID, password)
    VALUES (bannerID, password);
	SET @userID = LAST_INSERT_ID();
    
    INSERT INTO UserContactInfo(userID, firstName, lastName, email)
    VALUES (@userID, firstName, lastName, email);
    
    SELECT Role.id
    INTO @guestRoleID
    FROM Role
    WHERE role = 'Guest';

    INSERT INTO SystemRole(roleID, userID)
    VALUES (@guestRoleID, @userID);
END $$

DELIMITER ;