DELIMITER $$
CREATE  PROCEDURE `spCheckPublishByCourseID`(IN CourseID BIGINT)
BEGIN
	select IsPublished from SurveyPublished where SurveyPublished.CourseID=CourseID;
END$$
DELIMITER ;