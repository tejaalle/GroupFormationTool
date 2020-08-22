DELIMITER $$
CREATE PROCEDURE `spDeleteQuestionByID`(IN procQuestionID bigint, IN procInstructorID bigint)
BEGIN
    START TRANSACTION;
    DELETE FROM Options where QuestionID = procQuestionID;
    DELETE FROM InstructorQuestions where InstructorQuestions.QuestionID = procQuestionID and InstructorID = procInstructorID;
    DELETE FROM SurveyQuestions where SurveyQuestions.QuestionID = procQuestionID;
    DELETE FROM SurveyResponses where SurveyResponses.QuestionID=procQuestionID;
    DELETE FROM QuestionCriteria where QuestionCriteria.QuestionID=procQuestionID;
     DELETE FROM Question WHERE Question.QuestionID = procQuestionID;
    COMMIT;
END$$
DELIMITER ;