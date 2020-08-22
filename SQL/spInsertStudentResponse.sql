DELIMITER $$
CREATE PROCEDURE `spInsertStudentResponse`(IN courseId bigint, IN studentId bigint,IN questionId bigint, IN response varchar(200))
BEGIN
Insert into SurveyResponses(QuestionID,CourseID,UserID,Response) Values(questionId,courseId,studentId,response);
END$$
DELIMITER ;
