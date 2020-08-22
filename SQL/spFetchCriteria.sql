DELIMITER $$
CREATE PROCEDURE `spFetchCriteria`(courseId bigint)
BEGIN
select QuestionID, Criteria from CSCI5308_10_PRODUCTION.QuestionCriteria where CSCI5308_10_PRODUCTION.QuestionCriteria.CourseID = courseId;
END$$
DELIMITER ;