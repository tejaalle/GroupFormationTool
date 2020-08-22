DELIMITER $$

DROP PROCEDURE IF EXISTS spFindUsersWithCourseRole $$

CREATE PROCEDURE spFindUsersWithCourseRole (
	IN role VARCHAR(10),
	IN courseID BIGINT
)
BEGIN
	SELECT id
    INTO @roleID
    FROM Role
    WHERE Role.role = role;
    
    SELECT userID
    FROM CourseRole
    WHERE CourseRole.roleID = @roleID AND CourseRole.courseID = courseID;
END $$

DELIMITER ;