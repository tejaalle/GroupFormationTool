DELIMITER $$

DROP PROCEDURE IF EXISTS spFindUserByBannerID $$

CREATE PROCEDURE spFindUserByBannerID (
	IN bannerID VARCHAR(20)
)
BEGIN
	SELECT id, bannerID, password
	FROM User
    WHERE User.bannerID = bannerID;
END $$

DELIMITER ;