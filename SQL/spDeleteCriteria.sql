DELIMITER $$
CREATE PROCEDURE `spDeleteCriteria`(IN procCourseID BIGINT)
BEGIN
Delete from QuestionCriteria where QuestionCriteria.CourseID=procCourseID;
END$$
DELIMITER ;