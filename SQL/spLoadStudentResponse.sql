DELIMITER $$
CREATE PROCEDURE `spLoadStudentResponse`(IN courseId bigint, In studentId bigint)
BEGIN
select QuestionId, Response  from SurveyResponses where SurveyResponses.CourseID = courseId and SurveyResponses.UserId = studentId;
END$$
DELIMITER ;