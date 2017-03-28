CREATE TABLE
    good_demo 
    (
        productid CHARACTER VARYING NOT NULL,
        productname CHARACTER VARYING,
        productnum BIGINT,
        price DOUBLE PRECISION,
        supplier CHARACTER VARYING,
        prodate DATE,
        orgin CHARACTER VARYING,
        ts TIMESTAMP(6) WITHOUT TIME ZONE
    );

INSERT INTO good_demo (productid, productname, productnum, price, supplier, prodate, orgin, ts) VALUES ('60f5c9f6-c1bd-411d-a963-54c566e6b66d', '联想笔记本电脑', 100, 4500.0, '联想', '2016-06-01', '北京', '2016-06-12 12:41:40');
INSERT INTO good_demo (productid, productname, productnum, price, supplier, prodate, orgin, ts) VALUES ('86d71ef8-ab99-451b-aa43-e49c3ed6791a', '无线鼠标', 2200, 22.0, '微软', '2016-03-16', '江苏', '2016-06-12 12:42:13');
INSERT INTO good_demo (productid, productname, productnum, price, supplier, prodate, orgin, ts) VALUES ('4675e503-7914-499c-b016-c440ecbc0228', '显示器', 30, 870.0, '冠捷', '2016-03-14', '北京', '2016-06-12 13:10:46');
INSERT INTO good_demo (productid, productname, productnum, price, supplier, prodate, orgin, ts) VALUES ('b074bc37-713f-4afd-8ec2-edc6332593c2', '传真机', 120, 142.5, '西门子', '2016-03-08', '德国', '2016-06-12 13:11:24');
INSERT INTO good_demo (productid, productname, productnum, price, supplier, prodate, orgin, ts) VALUES ('86d71ef8-ab99-451b-aa43-e49c3ed6791e', '打印机', 300, 3000.0, '惠普', '2016-03-07', '江苏', '2016-06-12 13:10:05');
INSERT INTO good_demo (productid, productname, productnum, price, supplier, prodate, orgin, ts) VALUES ('c3ec9686-e437-4fa7-afa5-b21ff3bd1be6', '办公位', 600, 300.0, '立信', '2016-06-06', '河南', '2016-06-12 16:24:20');
INSERT INTO good_demo (productid, productname, productnum, price, supplier, prodate, orgin, ts) VALUES ('c3ec9686-e437-4fa7-afa5-b21ff3bd1be8', '加湿器', 600, 300.0, '海尔', '2016-06-06', '河南', '2016-06-12 16:24:20');
INSERT INTO good_demo (productid, productname, productnum, price, supplier, prodate, orgin, ts) VALUES ('c3ec9686-e437-4fa7-afa5-b21ff3bd1be9', '电源', 600, 300.0, '万能', '2016-06-06', '河南', '2016-06-12 16:24:20');
INSERT INTO good_demo (productid, productname, productnum, price, supplier, prodate, orgin, ts) VALUES ('c3ec9686-e437-4fa7-afa5-b21ff3bd1be5', '网线', 600, 300.0, '惠连', '2016-06-06', '河南', '2016-06-12 16:24:20');
INSERT INTO good_demo (productid, productname, productnum, price, supplier, prodate, orgin, ts) VALUES ('c3ec9686-e437-4fa7-afa5-b21ff3bd1be4', '打印纸', 600, 300.0, '大白兔', '2016-06-06', '河南', '2016-06-12 16:24:20');
INSERT INTO good_demo (productid, productname, productnum, price, supplier, prodate, orgin, ts) VALUES ('c3ec9686-e437-4fa7-afa5-b21ff3bd1be3', '电话', 600, 300.0, '海信', '2016-06-06', '河南', '2016-06-12 16:24:20');
INSERT INTO good_demo (productid, productname, productnum, price, supplier, prodate, orgin, ts) VALUES ('c3ec9686-e437-4fa7-afa5-b21ff3bd1be2', '键盘', 600, 300.0, '联想', '2016-06-06', '河南', '2016-06-12 16:24:20');
