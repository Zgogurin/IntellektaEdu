INSERT INTO public.product (id, name) VALUES (1,'car_test');
INSERT INTO public.product (id, name) VALUES (2,'bike_test');

INSERT INTO public.sales_period (id, price, date_from, date_to, product) VALUES (nextval('sales_period_id_seq'), 100, '2020-05-01', '2020-05-04', 1);
INSERT INTO public.sales_period (id, price, date_from, date_to, product) VALUES (nextval('sales_period_id_seq'), 150, '2020-05-06', '2020-05-08', 1);
INSERT INTO public.sales_period (id, price, date_from, date_to, product) VALUES (nextval('sales_period_id_seq'), 50, '2020-05-01', '2020-05-07', 2);
INSERT INTO public.sales_period (id, price, date_from, date_to, product) VALUES (nextval('sales_period_id_seq'), 60, '2020-05-08', null, 2);
INSERT INTO public.sales_period (id, price, date_from, date_to, product) VALUES (nextval('sales_period_id_seq'), 120, '2020-05-09', '2020-05-13', 1);
INSERT INTO public.sales_period (id, price, date_from, date_to, product) VALUES (nextval('sales_period_id_seq'), 2000, '2020-03-31', '2020-04-04', 1);