DELIMITER $$

DROP PROCEDURE IF EXISTS spLoadAllCourses $$

CREATE PROCEDURE spLoadAllCourses ()
BEGIN
	SELECT id, title
    FROM Course
    ORDER BY title ASC;
END $$

DELIMITER ;