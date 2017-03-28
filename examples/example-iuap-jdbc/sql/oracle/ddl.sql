-- DROP TABLE ipuquotation;
CREATE TABLE ipuquotation (
  qtexpiredate        DATE DEFAULT sysdate,
  description         VARCHAR2(20),
  status              VARCHAR2(20),
  subject             VARCHAR2(180),
  ipuquotaionid       VARCHAR2(64)                         NOT NULL,
  createtime          DATE  DEFAULT sysdate,
  prid                VARCHAR2(20),
  btax                VARCHAR2(50),
  corp_account        VARCHAR2(20),
  corp_sub_account    VARCHAR2(20),
  corp_accountid      VARCHAR2(20),
  corp_sub_accountid  VARCHAR2(20),
  erp_product_version VARCHAR2(20),
  erp_vender          VARCHAR2(20),
  processor           VARCHAR2(20),
  processtime         DATE  DEFAULT sysdate,
  currencyid          VARCHAR2(20),
  currency_code       VARCHAR2(10),
  buyoffertime        DATE  DEFAULT sysdate,
  ts                  DATE DEFAULT sysdate,
  ecbillcode          VARCHAR2(20),
  contact             VARCHAR2(20),
  phone               VARCHAR2(20),
  dr                  SMALLINT,
  PRIMARY KEY (ipuquotaionid)
);

-- DROP TABLE ipuquotationdetail;

CREATE TABLE ipuquotationdetail (
  ipuquotationdetailid VARCHAR2(50) NOT NULL,
  pritemid             VARCHAR2(50),
  productdesc          VARCHAR2(50),
  productid            VARCHAR2(50),
  productname          VARCHAR2(50),
  purchaseamount       DECIMAL(28, 8),
  pureqid              VARCHAR2(50),
  quotationid          VARCHAR2(50),
  unit                 VARCHAR2(50),
  unitid               VARCHAR2(50),
  ts                   DATE,
  dr                   SMALLINT DEFAULT '0',
  PRIMARY KEY (ipuquotationdetailid)
);
