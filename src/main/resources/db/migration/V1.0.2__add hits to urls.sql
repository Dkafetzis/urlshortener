ALTER TABLE RedirectUrl
    ADD hits INTEGER;

UPDATE RedirectUrl
SET hits = 0
WHERE hits IS NULL;
ALTER TABLE RedirectUrl
    ALTER COLUMN hits SET NOT NULL;