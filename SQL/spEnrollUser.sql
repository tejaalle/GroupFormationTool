DELIMITER $$

DROP PROCEDURE IF EXISTS spEnrollUser $$

CREATE PROCEDURE spEnrollUser (
	IN courseID BIGINT,
    IN userID BIGINT,
    IN roleToAssign VARCHAR(10)
)
BEGIN
	SELECT id
    INTO @roleID
    FROM Role
    WHERE Role.role = roleToAssign;
    INSERT INTO CourseRole(courseID, roleID, userID)
    VALUES (courseID, @roleID, userID);
END $$

DELIMITER ;