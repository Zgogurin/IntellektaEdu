DROP TABLE IF EXISTS sales_period;
DROP TABLE IF EXISTS product;

CREATE TABLE product
(
    id integer NOT NULL,
    name character varying (100),
    constraint product_pkey PRIMARY KEY (id)
);

CREATE TABLE sales_period
(
    id integer NOT NULL,
    price integer NOT NULL,
    date_from date NOT NULL,
    date_to date,
    product integer NOT NULL,
    constraint sales_periods_pkey PRIMARY KEY (id)
);