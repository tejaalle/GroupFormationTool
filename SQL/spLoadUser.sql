DELIMITER $$

DROP PROCEDURE IF EXISTS spLoadUser $$

CREATE PROCEDURE spLoadUser (
	IN userID BIGINT
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
    WHERE U.id = userID;
END $$

DELIMITER ;