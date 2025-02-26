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

INSERT INTO public.provider(
	code, enabled, fixed_commission, sell_spread, buy_spread, attrs)
	VALUES ('BITVAVO', true, 0, 0.0015, 0.0025, null);

INSERT INTO public.settings(code, value) VALUES 
('keep-balance-available-ratio', 0.1),
('max-balance-allocation-per-ticker-ratio', 0.1),
('min-gain-margin', 0.02),
('stop-loss-warning', 0.25),
('stop-loss', 0.4);
COMMIT;