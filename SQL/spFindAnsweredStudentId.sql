DELIMITER $$
CREATE PROCEDURE `spFindAnsweredStudentId`(IN courseId bigint)
BEGIN
SELECT SurveyResponses.userId from SurveyResponses where SurveyResponses.CourseID = courseId;
END$$
DELIMITER ;