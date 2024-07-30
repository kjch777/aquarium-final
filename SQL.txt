create table item (item_no number primary key,
item_name varchar2(50) not null,
item_desc varchar2(4000) not null,
item_price number(6) not null,
img_file varchar2(25) not null);

create sequence item_seq start with 1 increment by 1;

insert into item values (item_seq.nextval, '바닷 속 친구들 키링', '심플한 디자인의 키링입니다.', 8000, 'simpleKeyRing.jpg');
insert into item values (item_seq.nextval, '돌고래 모양 키링', '귀여운 돌고래 모양의 키링입니다.', 10000, 'dolphinKeyRing.jpg');
insert into item values (item_seq.nextval, '사이좋은 오리 자매 인형', '장난꾸러기 오리 모양의 인형입니다.\r\n\r\n둘은 사이좋기로 유명한 자매입니다.\r\n\r\n(1개 구매 시 2개의 인형이 한 쌍으로 출고됩니다.)', 12000, 'duckDuckDoll.jpg');
insert into item values (item_seq.nextval, '파랑 상어 인형', '드넓은 바다의 무법자 상어 모양의 인형입니다.\r\n\r\n배가 고파지면 이것저것 가리지 않고 삼켜버릴 수 있으니 주의하세요!\r\n\r\n(어른이 베고 자도 문제 없을 정도의 크기입니다.)', 15000, 'blueSharkDoll.jpg');
insert into item values (item_seq.nextval, '바다의 왕자 범고래 인형', '드넓은 바다의 왕자 범고래 모양의 인형입니다.\r\n\r\n파랑 상어와는 티격태격 하지만, 그래도 가장 친한 친구랍니다.\r\n\r\n(어린이가 베고 잘 수 있을 정도의 크기입니다.)', 11000, 'blackWhaleDoll.jpg');
insert into item values (item_seq.nextval, '먼작귀 삼총사 키링', '저 멀리 일본에서 건너 온 먼작귀 삼총사!\r\n\r\n가방이나 신발 주머니에 걸어보세요~\r\n\r\n(정식 라이센스를 취득한 제품이며, 3개가 한 세트입니다.)', 9000, 'chiikawasKeyRing.jpg');
insert into item values (item_seq.nextval, '커플 돌고래 헤어밴드', '커플 돌고래 헤어밴드는 사랑스럽고 매력적인 디자인으로, 특별한 연결을 상징하는 아이템입니다.\r\n\r\n각각의 밴드에 작은 돌고래 모양 장식이 달려 있어 로맨틱한 느낌을 즐길 수 있습니다.', 20000, 'dolphinHairBand.jpg');
insert into item values (item_seq.nextval, '죠스 남매 벌룬소드', '죠스 남매 벌룬소드는 전설적인 책인 죠스의 남매, 랜스로드와 파레스로드가 사용한 무기입니다.\r\n\r\n이 두 소드는 전투에서 그들의 재능과 용기를 나타내는 상징적인 아이템입니다.', 15000, 'a.jpg');
insert into item values (item_seq.nextval, '잠수부 수조 장식', '잠수부 수조 장식은 바다를 사랑하는 이들에게 어울리는 아름다운 장식품입니다. 이 장식품은 작은 잠수부가 포함된 다양한 해양 생물과 심연의 아름다움을 현실적으로 재현했습니다.', 10000, 'b.jpg');

commit;

create table order_tb (order_no number primary key,
order_quantity number(3) not null,
rec_name varchar2(15) not null,
rec_phone varchar2(15) not null,
rec_addr varchar2(150) not null,
rec_detail_addr varchar2(100) not null,
rec_postcode varchar2(15) not null,
rec_request varchar2(4000),
payment_method varchar2(15) not null,
card_type varchar2(15),
installment varchar2(15),
bank_type varchar2(15),
item_no number(1) not null,
memberId varchar2(50) not null);

create sequence order_seq start with 1 increment by 1;

commit;

create table member (
    memberNo number,
    memberId varchar2(50),
    memberPw varchar2(50) not null,
    memberEmail varchar2(100) not null,
    memberName varchar2(50) not null,
    memberBirth varchar2(8) not null,
    memberPhone varchar2(20),
    signUpDate date default sysdate,
    
    primary key (memberNo, memberId)
);

create sequence memberNo_seq;

CREATE TABLE RESERV (
    RESERV_NO NUMBER PRIMARY KEY,
    MEMBER_ID VARCHAR2(50) NOT NULL,
    PROGRAM_NO VARCHAR2(15) NOT NULL,
    RESERV_TOTAL_COUNT NUMBER(2) NOT NULL,
    CHILD_COUNT NUMBER(1) NOT NULL,
    ADULT_COUNT NUMBER(1) NOT NULL,
    RESERV_TOTAL_PRICE NUMBER(10) NOT NULL,
    PAYMENT_DATE DATE DEFAULT SYSDATE NOT NULL,
    RESERV_DATE DATE NOT NULL,
    RESERV_CANCLE VARCHAR2(1) NOT NULL,
    RESERV_EXPRT VARCHAR2(1) NOT NULL
);
CREATE SEQUENCE RESERV_SEQ;
CREATE TABLE PROGRAM (
    PROGRAM_NO VARCHAR2(15) PRIMARY KEY,
    PROGRAM_NAME VARCHAR2(100) NOT NULL,
    PROGRAM_PRICE NUMBER(10) NOT NULL,
    PROGRAM_CONTENT VARCHAR2(250)
);
INSERT INTO PROGRAM (PROGRAM_NO, PROGRAM_NAME, PROGRAM_PRICE) VALUES ('P1', '아쿠아리움 입장권', 10000);
INSERT INTO PROGRAM (PROGRAM_NO, PROGRAM_NAME, PROGRAM_PRICE) VALUES ('P2', '아쿠아리움 입장권 + 동물 먹이주기 체험', 20000);
INSERT INTO PROGRAM (PROGRAM_NO, PROGRAM_NAME, PROGRAM_PRICE) VALUES ('P3', '아쿠아리움 입장권 + 스킨스쿠버 체험', 30000);

COMMIT;