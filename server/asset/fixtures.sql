BEGIN;
INSERT INTO "balance" ("available") VALUES (0);

INSERT INTO "interval" ("id", "code") VALUES (1, '1m');
INSERT INTO "interval" ("id", "code") VALUES (2, '5m');
INSERT INTO "interval" ("id", "code") VALUES (3, '15m');
INSERT INTO "interval" ("id", "code") VALUES (4, '30m');
INSERT INTO "interval" ("id", "code") VALUES (5, '1h');
INSERT INTO "interval" ("id", "code") VALUES (6, '2h');
INSERT INTO "interval" ("id", "code") VALUES (7, '4h');
INSERT INTO "interval" ("id", "code") VALUES (8, '6h');
INSERT INTO "interval" ("id", "code") VALUES (9, '8h');
INSERT INTO "interval" ("id", "code") VALUES (10, '2h');
INSERT INTO "interval" ("id", "code") VALUES (11, '1d');
COMMIT;