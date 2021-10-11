alter table receipt_product drop  foreign key receipt_fk;

alter table receipt_product drop  foreign key product_fk;

alter table role_language drop  foreign key role_r_fk;

alter table role_language drop  foreign key language_fk;

drop database cash_mashine_test;