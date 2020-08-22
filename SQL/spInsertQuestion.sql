DELIMITER $$
CREATE PROCEDURE `spInsertQuestion`(IN procTitle varchar(400), IN procText varchar(400), IN procTypeID bigint,
                                  IN procCreatedOn date, IN procInstructorID bigint, IN procOptionValues varchar(1000),
                                  IN procStoredAs varchar(400))
BEGIN

    DECLARE indexOfOptionValues TEXT DEFAULT NULL;
    DECLARE lengthOfOptionValues INT DEFAULT NULL;
    DECLARE itemOptionValues TEXT DEFAULT NULL;

    DECLARE indexOfStoredAs TEXT DEFAULT NULL;
    DECLARE lengthOfStoredAs INT DEFAULT NULL;
    DECLARE itemStoredAs TEXT DEFAULT NULL;

    START TRANSACTION;
    INSERT INTO Question(Title, Text, TypeID, createdOn) VALUES (procTitle, procText, procTypeID, procCreatedOn);

    SET @QuestionID = LAST_INSERT_ID();
    INSERT INTO InstructorQuestions(InstructorID, QuestionID) VALUES (procInstructorID, @QuestionID);

    insertIteration:
    loop
        IF LENGTH(TRIM(procOptionValues)) = 0 OR procOptionValues IS NULL THEN
            LEAVE insertIteration;
        end if;

        SET indexOfOptionValues = SUBSTRING_INDEX(procOptionValues, ',', 1);
        SET indexOfStoredAs = SUBSTRING_INDEX(procStoredAs, ',', 1);

        SET lengthOfOptionValues = LENGTH(indexOfOptionValues);
        SET lengthOfStoredAs = LENGTH(indexOfStoredAs);

        SET itemOptionValues = TRIM(indexOfOptionValues);
        SET itemStoredAs = TRIM(indexOfStoredAs);

        INSERT INTO Options(OptionValues, StoredAs, QuestionID)
        VALUES (itemOptionValues, itemStoredAs, @QuestionID);

        set procOptionValues = INSERT(procOptionValues, 1, lengthOfOptionValues + 1, '');
        set procStoredAs = INSERT(procStoredAs, 1, lengthOfStoredAs + 1, '');

    end loop;
    COMMIT;
END$$
DELIMITER ;