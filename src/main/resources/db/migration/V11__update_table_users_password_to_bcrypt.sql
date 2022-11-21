UPDATE users
SET password = '$2y$10$lpoBViCmlJftRbT2PL7XQOisnWuRf8rhP0mTZiLbI1nqVq7FhfCcO'
WHERE user_name = 'john';

UPDATE users
SET password = '$2y$10$/kPCUCHFI5L8/uwWaGJOSuGlutaZHPLo.GS/SUY..WGHl9wBLmAt.'
WHERE user_name = 'carl';

# The password is now stored as a bcrypt hash. The hash is 60 characters long, and it is stored in the password column.
