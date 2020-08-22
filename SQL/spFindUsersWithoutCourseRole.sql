DELIMITER $$

DROP PROCEDURE IF EXISTS spFindUsersWithoutCourseRole $$

CREATE PROCEDURE spFindUsersWithoutCourseRole (
	IN role VARCHAR(10),
	IN courseID BIGINT
)
BEGIN
	SELECT id
    INTO @roleID
    FROM Role
    WHERE Role.role = role;
    
    SELECT U.id, U.bannerID, UC.firstName, UC.lastName
    FROM User U
    JOIN UserContactInfo UC ON (U.id = UC.userID)
    LEFT JOIN CourseRole CR ON (CR.courseID = courseID AND U.id = CR.userID)
    WHERE ISNULL(CR.roleID) OR CR.roleID <> @roleID
    ORDER BY UC.lastName ASC;
END $$

DELIMITER ;