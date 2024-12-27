```
DELIMITER $$

CREATE TRIGGER `before_create_user`
BEFORE INSERT ON `school_sync_v1`.`users`
FOR EACH ROW
BEGIN

    DECLARE newId INT;
    DECLARE nicCount INT;
    DECLARE usernameCount INT;
    
    SELECT count(*) INTO newId FROM `school_sync_v1`.`users`;
    
    SET NEW.id = CONCAT('usr-', newId + 1);
    SET NEW.created_at = NOW();

END$$

DELIMITER ;
```

```
DELIMITER $$

CREATE TRIGGER `before_create_student`
BEFORE INSERT ON `school_sync_v1`.`student`
FOR EACH ROW
BEGIN

    DECLARE newId INT;
    DECLARE nicCount INT;
    DECLARE usernameCount INT;
    
    SELECT count(*) INTO newId FROM `school_sync_v1`.`student`;
    
    SET NEW.id = CONCAT('stu-', newId + 1);
    SET NEW.created_at = NOW();

END$$

DELIMITER ;
```
