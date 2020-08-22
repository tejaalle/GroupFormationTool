DELIMITER $$

DROP PROCEDURE IF EXISTS spFindUsersByName $$

CREATE PROCEDURE spFindUsersByName (
	IN name VARCHAR(200)
)
BEGIN
	SELECT userID, firstName, lastName
    FROM UserContactInfo
    WHERE firstName LIKE CONCAT(name, '%') OR lastName LIKE CONCAT(name, '%') OR CONCAT(firstName, ' ', lastName) LIKE CONCAT(name, '%');
END $$

DELIMITER ;