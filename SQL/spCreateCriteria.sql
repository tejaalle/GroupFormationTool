DELIMITER $$
CREATE PROCEDURE `spCreateCriteria`(IN procCourseID BIGINT, IN procQuestionID BIGINT, IN procCriteria varchar(20))
BEGIN
Insert into QuestionCriteria (CourseID, QuestionID, Criteria) values(procCourseID, procQuestionID, procCriteria); 
END$$
DELIMITER ;