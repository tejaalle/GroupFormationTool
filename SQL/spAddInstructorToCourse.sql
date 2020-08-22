DELIMITER $$

DROP PROCEDURE IF EXISTS spAddInstructorToCourse $$

CREATE PROCEDURE spAddInstructorToCourse (
	IN instructorID BIGINT,
    IN courseID BIGINT
)
BEGIN
	SELECT id
    INTO @instructorRoleID
    FROM Role
    WHERE role = 'Instructor';

	DELETE FROM CourseRole
    WHERE CourseRole.courseID = courseID
    AND CourseRole.roleID = @instructorRoleID
    AND CourseRole.userID = instructorID;
    
    INSERT INTO CourseRole(courseID, roleID, userID)
    VALUES (courseID, @instructorRoleID, instructorID);
END $$

DELIMITER ;