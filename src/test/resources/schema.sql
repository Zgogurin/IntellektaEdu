DROP TABLE IF EXISTS sales_period;
DROP TABLE IF EXISTS product;

DROP SEQUENCE IF EXISTS sales_period_id_seq;

CREATE SEQUENCE sales_period_id_seq
  START WITH 8
  INCREMENT BY 1
  MINVALUE 1;


CREATE TABLE product
(
    id integer NOT NULL,
    name character varying (100),
    constraint product_pkey PRIMARY KEY (id)
);

CREATE TABLE sales_period
(
    id integer DEFAULT sales_period_id_seq.nextval NOT NULL,
    price integer NOT NULL,
    date_from date NOT NULL,
    date_to date,
    product integer NOT NULL,
    constraint sales_periods_pkey PRIMARY KEY (id)
);