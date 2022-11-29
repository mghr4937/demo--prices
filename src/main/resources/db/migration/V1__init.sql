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
   start_date DATETIME NOT NULL,
   end_date DATETIME NOT NULL,
   price_list INT NOT NULL,
   product_id BIGINT NOT NULL,
   priority INT NOT NULL,
   price FLOAT NOT NULL,
   currency VARCHAR(3) NOT NULL,
   CONSTRAINT pk_price PRIMARY KEY (id)
);

ALTER TABLE price ADD CONSTRAINT FK_PRICE_ON_BRAND FOREIGN KEY (brand_id) REFERENCES brand (id);
