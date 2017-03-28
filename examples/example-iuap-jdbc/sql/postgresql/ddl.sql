-- DROP TABLE ipuquotation;
CREATE TABLE ipuquotation (
  qtexpiredate        TIMESTAMP without time zone DEFAULT now(),
  description         VARCHAR(20),
  status              VARCHAR(20),
  subject             VARCHAR(180),
  ipuquotaionid       VARCHAR(64)                         NOT NULL,
  createtime          TIMESTAMP without time zone DEFAULT now(),
  prid                VARCHAR(20),
  btax                VARCHAR(50),
  corp_account        VARCHAR(20),
  corp_sub_account    VARCHAR(20),
  corp_accountid      VARCHAR(20),
  corp_sub_accountid  VARCHAR(20),
  erp_product_version VARCHAR(20),
  erp_vender          VARCHAR(20),
  processor           VARCHAR(20),
  processtime         TIMESTAMP without time zone DEFAULT now(),
  currencyid          VARCHAR(20),
  currency_code       VARCHAR(10),
  buyoffertime        TIMESTAMP without time zone DEFAULT now(),
  ts                  TIMESTAMP without time zone DEFAULT now(),
  ecbillcode          VARCHAR(20),
  contact             VARCHAR(20),
  phone               VARCHAR(20),
  dr                  SMALLINT,
  PRIMARY KEY (ipuquotaionid)
);

-- DROP TABLE ipuquotationdetail;

CREATE TABLE ipuquotationdetail (
  ipuquotationdetailid VARCHAR(50)                         NOT NULL,
  pritemid             VARCHAR(50),
  productdesc          VARCHAR(50),
  productid            VARCHAR(50),
  productname          VARCHAR(50),
  purchaseamount       DECIMAL(28, 8),
  pureqid              VARCHAR(50),
  quotationid          VARCHAR(50),
  unit                 VARCHAR(50),
  unitid               VARCHAR(50),
  ts                   TIMESTAMP without time zone DEFAULT now(),
  dr                   SMALLINT DEFAULT '0',
  PRIMARY KEY (ipuquotationdetailid)
);
