DELIMITER $$

DROP PROCEDURE IF EXISTS spLoadUserRolesForCourse $$

CREATE PROCEDURE spLoadUserRolesForCourse (
	IN courseID BIGINT,
    IN userID BIGINT
)
BEGIN
	SELECT DISTINCT R.role
    FROM Role R
    JOIN CourseRole CR ON (CR.roleID = R.id)
    WHERE CR.courseID = courseID AND CR.userID = userID;
END $$

DELIMITER ;