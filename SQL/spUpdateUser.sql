DELIMITER $$

DROP PROCEDURE IF EXISTS spUpdateUser $$

CREATE PROCEDURE spUpdateUser (
	IN id BIGINT,
	IN bannerID VARCHAR(20),
    IN password VARCHAR(76),
    IN firstName VARCHAR(100),
    IN lastName VARCHAR(100),
    IN email VARCHAR(320)
)
BEGIN
	UPDATE User U
    SET U.bannerID = bannerID, U.password = password
    WHERE U.id = id;
    UPDATE UserContactInfo UC
    SET UC.firstName = firstName, UC.lastName = lastName, UC.email = email
    WHERE UC.userID = id;
END $$

DELIMITER ;