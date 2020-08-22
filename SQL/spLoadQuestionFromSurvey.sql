DELIMITER $$
CREATE PROCEDURE `spLoadQuestionFromSurvey`(IN CourseID BIGINT)
BEGIN
select QuestionID from SurveyQuestions where SurveyQuestions.CourseID=CourseID order by SurveyQuestions.SurveyID;
END$$
DELIMITER ;