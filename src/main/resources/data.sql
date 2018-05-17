CREATE TABLE IF NOT EXISTS PORTFOLIO(id IDENTITY PRIMARY KEY, title VARCHAR);
DELETE FROM PORTFOLIO;
INSERT INTO PORTFOLIO VALUES(1, 'Bitfinex');
INSERT INTO PORTFOLIO VALUES(2, 'Bittrex');
INSERT INTO PORTFOLIO VALUES(3, 'Binance');

CREATE TABLE IF NOT EXISTS CRYPTO_CURRENCY(id IDENTITY PRIMARY KEY, symbol VARCHAR, valueInBTC DOUBLE, valueInUSD DOUBLE);
DELETE FROM CRYPTO_CURRENCY;
INSERT INTO CRYPTO_CURRENCY VALUES(1, 'BTC', 1.0, 8700.0);
INSERT INTO CRYPTO_CURRENCY VALUES(2, 'ETH', 0.1, 870.0);
INSERT INTO CRYPTO_CURRENCY VALUES(3, 'LTC', 0.02, 130.0);

CREATE TABLE IF NOT EXISTS CRYPTO_INVESTMENT(id IDENTITY PRIMARY KEY, FOREIGN KEY (invested_coin_id) REFERENCES crypto_currency(id), FOREIGN KEY (investment_coin_id) REFERENCES crypto_currency(id));
DELETE FROM CRYPTO_INVESTMENT;
INSERT INTO CRYPTO_INVESTMENT VALUES(1, 1, 2);
INSERT INTO CRYPTO_INVESTMENT VALUES(2, 2, 3);
INSERT INTO CRYPTO_INVESTMENT VALUES(3, 2, 1);

