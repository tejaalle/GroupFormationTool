DELIMITER $$
CREATE PROCEDURE `spRemoveQuestionFromSurvey`(IN QuestionID BIGINT)
BEGIN
delete from SurveyQuestions where SurveyQuestions.QuestionID=QuestionID;
END$$
DELIMITER ;