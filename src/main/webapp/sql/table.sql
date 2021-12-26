-- member 테이블 (mem_auth : 0:탈퇴회원 1:정지회원 2:일반회원 3:일반관리자 4:최고관리자)
CREATE TABLE member
(
    mem_num     NUMBER          NOT NULL, 
    mem_id      VARCHAR2(30)    UNIQUE NOT NULL, 
    mem_auth    NUMBER(1)       DEFAULT 2 NOT NULL, 
    CONSTRAINT member_pk PRIMARY KEY (mem_num)
)

--member_detail 테이블
CREATE TABLE member_detail
(
    mem_passwd      VARCHAR2(30)     NOT NULL, 
    mem_name        VARCHAR2(20)     NOT NULL, 
    mem_phone       VARCHAR2(20)     NOT NULL, 
    mem_email       VARCHAR2(50)     NOT NULL, 
    mem_zipcode     VARCHAR2(10)     NOT NULL, 
    mem_address1    VARCHAR2(100)    NOT NULL, 
    mem_address2    VARCHAR2(100)    NOT NULL, 
    mem_date        DATE             DEFAULT SYSDATE NOT NULL, 
    mem_num         NUMBER           NOT NULL,
    constraint member_detail_pk primary key (mem_num),
    CONSTRAINT member_detail_fk foreign key (mem_num) references member (mem_num)
)

-- 상위 카테고리 테이블
CREATE TABLE category_top
(
    c_top_no      NUMBER          NOT NULL, 
    c_top_name    VARCHAR2(30)    NOT NULL, 
    CONSTRAINT category_top_pk PRIMARY KEY (c_top_no)
)

--하위 카테고리 테이블
CREATE TABLE category_sub
(
    c_sub_no      NUMBER          NOT NULL, 
    c_sub_name    VARCHAR2(50)    NOT NULL, 
    c_top_no     NUMBER          NOT NULL, 
    CONSTRAINT category_sub_pk PRIMARY KEY (c_sub_no),
    CONSTRAINT category_sub_fk foreign key (c_top_no) references category_top (c_top_no)
)

-- 상품 테이블
CREATE TABLE product
(
    p_no            NUMBER           NOT NULL, 
    p_name          VARCHAR2(20)     NOT NULL, 
    p_price         NUMBER           NOT NULL, 
    p_amount        NUMBER           NOT NULL, 
    p_image_name    VARCHAR2(100)    NULL, 
    p_image         BLOB             NULL, 
    p_discount      NUMBER          DEFAULT 0 NOT NULL,
    p_sub_text      CLOB             NULL, 
    c_top_no		NUMBER			 NOT NULL,
    c_sub_no		NUMBER			 NOT NULL,
    CONSTRAINT product_pk PRIMARY KEY (p_no),
    CONSTRAINT product_category_top_fk foreign key (c_top_no) references category_top (c_top_no),
    CONSTRAINT product_category_sub_fk foreign key (c_sub_no) references category_sub (c_sub_no)
    
)

-- 옵션 테이블
CREATE TABLE soption
(
    option_no       NUMBER          NOT NULL, 
    p_no            NUMBER          NOT NULL, 
    option_name     VARCHAR2(50)    NOT NULL, 
    option_value    VARCHAR2(50)    NOT NULL, 
    CONSTRAINT option_pk PRIMARY KEY (option_no),
    CONSTRAINT option_fk foreign key (p_no) references product (p_no)
)

-- 장바구니 테이블
CREATE TABLE cart
(
    cart_no          NUMBER    NOT NULL, 
    mem_num          NUMBER    NOT NULL, 
    p_no             NUMBER    NOT NULL, 
    cart_date        DATE      DEFAULT SYSDATE NOT NULL, 
    cart_amount    NUMBER    NOT NULL, 
    CONSTRAINT cart_pk PRIMARY KEY (cart_no),
    CONSTRAINT cart_member_fk foreign key (mem_num) references member (mem_num),
    CONSTRAINT cart_product_fk foreign key (p_no) references product (p_no)
)

--주문 테이블(order_method : 0:카드 1:현금)
CREATE TABLE sorder
(
    order_no          NUMBER           NOT NULL, 
    mem_num           NUMBER           NOT NULL, 
    order_zipcode     VARCHAR2(10)     NOT NULL, 
    order_address1    VARCHAR2(100)    NOT NULL, 
    order_address2    VARCHAR2(100)    NOT NULL, 
    order_method      NUMBER(1)       NOT NULL, 
    order_date        DATE             NOT NULL, 
    order_pay         NUMBER           NOT NULL, 
    CONSTRAINT sorder_pk PRIMARY KEY (order_no),
    CONSTRAINT sorder_member_fk foreign key (mem_num) references member (mem_num)
)

-- 주문 상세 테이블
CREATE TABLE sorder_detail
(
    order_d_no        NUMBER    NOT NULL, 
    order_no          NUMBER    NOT NULL, 
    p_no              NUMBER    NOT NULL, 
    order_d_amount    NUMBER    NOT NULL, 
    order_d_price     NUMBER    NOT NULL, 
    CONSTRAINT sorder_detail_pk PRIMARY KEY (order_d_no),
    CONSTRAINT sorder_detail_sorder_fk foreign key (order_no) references sorder (order_no),
    CONSTRAINT sorder_detail_product_fk foreign key (p_no) references product (p_no)
)
-- 배송 상태 테이블(d_status_num : 0:결제완료 1:배송준비중 2:배송중 3:배송완료 4:구매확정 5:반품 6:교환)
CREATE TABLE delivery_status
(
    d_status_num     NUMBER(1,0)     NOT NULL, 
    d_status_name    VARCHAR2(20)    NOT NULL, 
    CONSTRAINT delivery_status_pk PRIMARY KEY (d_status_num)
)

-- 배송 테이블
CREATE TABLE delivery
(
    delivery_no      NUMBER           NOT NULL, 
    order_no         NUMBER           NOT NULL, 
    mem_num          NUMBER           NOT NULL, 
    d_status_num     NUMBER(1,0)      DEFAULT 1 NOT NULL, 
    tracking_num     NVARCHAR2(20)    NOT NULL, 
    delivery_date    DATE             NOT NULL, 
    dcompany_name    varchar2(30)     DEFAULT '우체국' NOT NULL, 
    CONSTRAINT delivery_pk PRIMARY KEY (delivery_no),
    CONSTRAINT delivery_order_fk foreign key (order_no) references sorder (order_no),
    CONSTRAINT delivery_delivery_status_fk foreign key (d_status_num) references delivery_status (d_status_num),
    CONSTRAINT delivery_member_fk foreign key (mem_num) references member (mem_num)
    
)

-- 상품후기 테이블
CREATE TABLE review
(
    review_no            NUMBER           NOT NULL, 
    mem_num              NUMBER           NOT NULL, 
    p_no                 NUMBER           NOT NULL, 
    order_no			 NUMBER			  NOT NULL,
    review_content       CLOB             NOT NULL, 
    review_image_name    VARCHAR2(100)    NULL, 
    review_image         BLOB             NULL, 
    review_rating        NUMBER           NOT NULL, 
    CONSTRAINT review_pk PRIMARY KEY (review_no),
    CONSTRAINT review_member_fk foreign key (mem_num) references member (mem_num),
    CONSTRAINT review_product_fk foreign key (p_no) references product (p_no),
    CONSTRAINT review_order_fk foreign key (order_no) references sorder (order_no)
)

-- 문의게시판 테이블 (board_kind : 0:상품문의 1:배송문의 2:교환/반품문의 3:기타문의)
CREATE TABLE QNA
(
    board_no         NUMBER           NOT NULL, 
    grpno            NUMBER   		  NOT NULL, 
    grpord           NUMBER     	  NOT NULL, 
    depth            NUMBER			  NOT NULL, 
    board_title      VARCHAR2(100)    NOT NULL, 
    board_content    CLOB             NOT NULL, 
    board_date       DATE             NOT NULL, 
    mem_num          NUMBER           NOT NULL, 
    board_kind       NUMBER(1,0)      NULL, 
    p_no             NUMBER           NULL, 
    CONSTRAINT qna_pk PRIMARY KEY (board_no),
    CONSTRAINT qua_product_fk foreign key (p_no) references product (p_no),
    CONSTRAINT qua_member_fk foreign key (mem_num) references member (mem_num)
)

--시퀀스
create sequence member_seq;
create sequence qna_seq;
create sequence review_seq;
create sequence sorder_seq;
create sequence sorder_detail_seq;
create sequence cart_seq;
CREATE SEQUENCE category_top_seq INCREMENT BY 100 start with 100;
create sequence category_sub_seq;
create sequence product_seq;
create sequence option_seq;
create sequence delivery_no_seq;
create sequence delivery_status_seq;


