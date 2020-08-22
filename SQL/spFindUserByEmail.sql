DELIMITER $$

DROP PROCEDURE IF EXISTS spFindUserByEmail $$

CREATE PROCEDURE spFindUserByEmail (
	IN email VARCHAR(320)
)
BEGIN
	SELECT U.id AS id,
		U.bannerID AS bannerID,
		U.password AS password,
        UC.firstName AS firstName,
        UC.lastName AS lastName,
        UC.email AS email
	FROM User U
    JOIN UserContactInfo UC ON (U.id = UC.userID)
    WHERE UC.email = email;
END $$

DELIMITER ;