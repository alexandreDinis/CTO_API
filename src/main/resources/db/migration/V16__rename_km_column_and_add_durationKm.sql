
ALTER TABLE maintenance RENAME COLUMN km TO init_km;

ALTER TABLE maintenance ADD COLUMN duration_km INT;
