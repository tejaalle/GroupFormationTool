DELIMITER $$

DROP PROCEDURE IF EXISTS spDeleteCourse $$

CREATE PROCEDURE spDeleteCourse (
	IN id BIGINT
)
BEGIN
	DELETE FROM CourseRole
    WHERE CourseRole.courseID = id;

	DELETE FROM Course
    WHERE Course.id = id;
END $$

DELIMITER ;