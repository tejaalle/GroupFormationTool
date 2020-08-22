DELIMITER $$
CREATE PROCEDURE `spFindQuestionTypeByID`(IN TypeID bigint)
BEGIN
    SELECT TypeID, Description
    FROM QuestionType
    WHERE QuestionType.TypeID = TypeID;
END$$
DELIMITER ;