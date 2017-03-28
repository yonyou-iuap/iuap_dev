
INSERT INTO pub_bcr_rulebase (pk_billcodebase, pkbillobj, rulecode, rulename, codemode, iseditable, isautofill, format, isdefault, isused, islenvar, isgetpk, renterid, sysid, createdate) VALUES ('1000000', 'test', 'rulecode', 'rulename', 'after', 'N', 'Y', null, null, null, 'Y', 'N', null, null, '2016-12-26 09:26:10');

INSERT INTO pub_bcr_elem (pk_billcodeelem, pk_billcodebase, elemtype, elemvalue, elemlenth, isrefer, eorder, fillstyle, datedisplayformat, fillsign, createdate) VALUES ('10', '1000000', 2, null, 6, 3, 0, 0, null, null, '2016-12-26 09:26:10');
INSERT INTO pub_bcr_elem (pk_billcodeelem, pk_billcodebase, elemtype, elemvalue, elemlenth, isrefer, eorder, fillstyle, datedisplayformat, fillsign, createdate) VALUES ('11', '1000000', 0, null, 6, 0, 1, 0, null, null, '2016-12-26 09:26:10');
INSERT INTO pub_bcr_elem (pk_billcodeelem, pk_billcodebase, elemtype, elemvalue, elemlenth, isrefer, eorder, fillstyle, datedisplayformat, fillsign, createdate) VALUES ('12', '1000000', 1, 'TTT-', 4, 0, 2, 0, null, null, '2016-12-26 09:26:10');
INSERT INTO pub_bcr_elem (pk_billcodeelem, pk_billcodebase, elemtype, elemvalue, elemlenth, isrefer, eorder, fillstyle, datedisplayformat, fillsign, createdate) VALUES ('13', '1000000', 3, null, 6, 0, 3, 0, null, null, '2016-12-26 09:26:10');
