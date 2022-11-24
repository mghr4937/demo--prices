CREATE TABLE brand (
  id BIGINT AUTO_INCREMENT NOT NULL,
   name CLOB NOT NULL,
   CONSTRAINT pk_brand PRIMARY KEY (id),
   CONSTRAINT min_length_name CHECK (char_length(name) >= 3)
);

INSERT INTO brand (name) VALUES ('ZARA');
INSERT INTO brand (name) VALUES ('ZARA HOME');
