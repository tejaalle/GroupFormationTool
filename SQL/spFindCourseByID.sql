DELIMITER $$

DROP PROCEDURE IF EXISTS spFindCourseByID $$

CREATE PROCEDURE spFindCourseByID (
	IN courseID BIGINT
)
BEGIN
	SELECT id, title
	FROM Course
    WHERE Course.id = courseID;
END $$

DELIMITER ;