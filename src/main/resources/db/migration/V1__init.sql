CREATE TABLE brand (
  id BIGINT AUTO_INCREMENT NOT NULL,
   name VARCHAR(256) NOT NULL,
   CONSTRAINT pk_brand PRIMARY KEY (id),
   CONSTRAINT min_length_name CHECK (char_length(name) >= 3),
   CONSTRAINT uc_brand_name UNIQUE (name)
);

CREATE TABLE price (
  id BIGINT AUTO_INCREMENT NOT NULL,
   brand_id BIGINT NOT NULL,
   start_date DATE NOT NULL,
   end_date DATE NOT NULL,
   price_list INT NOT NULL,
   product_id BIGINT NOT NULL,
   priority INT NOT NULL,
   price DECIMAL NOT NULL,
   currency VARCHAR(3) NOT NULL,
   CONSTRAINT pk_price PRIMARY KEY (id)
);

CREATE INDEX idx_price_start_date_end_date ON price(product_id, start_date, end_date);
ALTER TABLE price ADD CONSTRAINT FK_PRICE_ON_BRAND FOREIGN KEY (brand_id) REFERENCES brand (id);

--BRAND_ID         START_DATE                                    END_DATE                        PRICE_LIST                   PRODUCT_ID  PRIORITY                 PRICE           CURR
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
--1         2020-06-14-00.00.00                        2020-12-31-23.59.59                        1                        35455                0                        35.50            EUR
--1         2020-06-14-15.00.00                        2020-06-14-18.30.00                        2                        35455                1                        25.45            EUR
--1         2020-06-15-00.00.00                        2020-06-15-11.00.00                        3                        35455                1                        30.50            EUR
--1         2020-06-15-16.00.00                        2020-12-31-23.59.59                        4                        35455                1                        38.95            EUR