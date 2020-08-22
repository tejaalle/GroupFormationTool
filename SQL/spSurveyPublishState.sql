DELIMITER $$
CREATE PROCEDURE `spSurveyPublishState`(IN procCourseID BIGINT, IN procState boolean)
BEGIN
delete from SurveyPublished where SurveyPublished.CourseID=procCourseID;
insert into SurveyPublished (CourseID, IsPublished) values (procCourseID, procState);
END$$
DELIMITER ;