DELIMITER $$
CREATE PROCEDURE `spAddQuestionToSurvey`(IN procCourseID BIGINT, IN procQuestionID BIGINT)
BEGIN
Insert into SurveyQuestions(QuestionID, CourseID) values(procQuestionID, procCourseID);
END$$
DELIMITER ;