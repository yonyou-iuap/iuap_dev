-- DROP TABLE  ipuquotation;
CREATE TABLE ipuquotation (
  qtexpiredate        TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  description         VARCHAR(20),
  status              VARCHAR(20),
  subject             VARCHAR(180),
  ipuquotaionid       VARCHAR(64)                         NOT NULL,
  createtime          DATETIME  DEFAULT CURRENT_TIMESTAMP,
  prid                VARCHAR(20),
  btax                VARCHAR(50),
  corp_account        VARCHAR(20),
  corp_sub_account    VARCHAR(20),
  corp_accountid      VARCHAR(20),
  corp_sub_accountid  VARCHAR(20),
  erp_product_version VARCHAR(20),
  erp_vender          VARCHAR(20),
  processor           VARCHAR(20),
  processtime         DATETIME  DEFAULT CURRENT_TIMESTAMP,
  currencyid          VARCHAR(20),
  currency_code       VARCHAR(10),
  buyoffertime        DATETIME  DEFAULT CURRENT_TIMESTAMP,
  ts                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
  ecbillcode          VARCHAR(20),
  contact             VARCHAR(20),
  phone               VARCHAR(20),
  dr                  SMALLINT,
  PRIMARY KEY (ipuquotaionid)
);

-- DROP TABLE  ipuquotationdetail;

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
  ts                   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
  dr                   SMALLINT DEFAULT '0',
  PRIMARY KEY (ipuquotationdetailid)
);
