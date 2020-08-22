DELIMITER $$
CREATE PROCEDURE `spFindInstructorByCourseID`(IN CourseID bigint)
BEGIN
select userID from CourseRole where courseID = CourseRole.CourseID and CourseRole.roleID = 4;
END$$
DELIMITER ;