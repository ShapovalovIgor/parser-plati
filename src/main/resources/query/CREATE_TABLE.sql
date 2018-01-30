CREATE TABLE user (
  id BIGINT NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE price (
  id BIGINT NOT NULL,
  price DOUBLE(50) DEFAULT NULL,
  date DATE DEFAULT NULL,
  FOREIGN KEY (id) REFERENCES product (id)
);
CREATE TABLE product (
  id                 BIGINT NOT NULL,
  name_goods         VARCHAR(100) DEFAULT NULL,
  price_id           BIGINT NOT NULL,
  cnt_sell           INTEGER (50) DEFAULT NULL,
  cnt_good_responses INTEGER (50) DEFAULT NULL
  cnt_bad_responses INTEGER (50) DEFAULT NULL,
  id_seller          INTEGER (50) DEFAULT NULL,
  type_product       INTEGER  (1)    DEFAULT NULL,
  FOREIGN KEY (id_seller) REFERENCES user (id),
  PRIMARY KEY (id)
);
CREATE TABLE price (
  id BIGINT NOT NULL,
  PRIMARY KEY (id)
);
SHUTDOWN;