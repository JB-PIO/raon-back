CREATE TABLE location
(
    location_id BIGINT          NOT NULL AUTO_INCREMENT COMMENT '지역 고유 ID',
    code        BIGINT          NOT NULL UNIQUE COMMENT '읍면동 코드',
    name        VARCHAR(10)     NOT NULL COMMENT '읍면동명',
    address     VARCHAR(50)     NOT NULL COMMENT '전체 주소',
    coordinates POINT SRID 4326 NOT NULL COMMENT '좌표(WGS84)',
    PRIMARY KEY (location_id)
);

CREATE TABLE user
(
    user_id       BIGINT                 NOT NULL AUTO_INCREMENT COMMENT '사용자 고유 ID',
    nickname      VARCHAR(30)            NOT NULL UNIQUE COMMENT '닉네임',
    email         VARCHAR(80)            NOT NULL UNIQUE COMMENT '이메일',
    password      VARCHAR(255)           NULL COMMENT '해시된 비밀번호',
    profile_image VARCHAR(255)           NULL COMMENT '프로필 사진 URL',
    location_id   BIGINT                 NOT NULL COMMENT '사용자 기본 지역',
    role          ENUM ('USER', 'ADMIN') NOT NULL DEFAULT 'USER' COMMENT '접근 권한',
    is_deleted    BOOLEAN                NOT NULL DEFAULT FALSE COMMENT '계정 삭제 여부',
    is_suspended  BOOLEAN                NOT NULL DEFAULT FALSE COMMENT '계정 정지 여부',
    created_at    DATETIME               NOT NULL COMMENT '가입 일시',
    updated_at    DATETIME               NULL COMMENT '정보 수정 일시',
    deleted_at    DATETIME               NULL COMMENT '계정 삭제 일시',
    suspended_at  DATETIME               NULL COMMENT '계정 정지 일시',
    PRIMARY KEY (user_id),
    FOREIGN KEY (location_id) REFERENCES location (location_id) ON DELETE RESTRICT
);

CREATE TABLE refresh_token
(
    token_id   BIGINT       NOT NULL AUTO_INCREMENT COMMENT '리프레시 토큰 고유 ID',
    user_id    BIGINT       NOT NULL UNIQUE COMMENT '사용자 ID',
    token      VARCHAR(128) NOT NULL UNIQUE COMMENT '해시된 리프레시 토큰',
    created_at DATETIME     NOT NULL COMMENT '리프레시 토큰 생성 일시',
    expires_at DATETIME     NOT NULL COMMENT '리프레시 토큰 만료 일시',
    PRIMARY KEY (token_id),
    FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE
);

CREATE TABLE category
(
    category_id BIGINT      NOT NULL AUTO_INCREMENT COMMENT '카테고리 고유 ID',
    level       BIGINT      NOT NULL DEFAULT 1 COMMENT '카테고리 레벨',
    parent_id   BIGINT      NULL COMMENT '상위 카테고리 ID',
    path        VARCHAR(30) NOT NULL COMMENT '카테고리 경로',
    name        VARCHAR(30) NOT NULL COMMENT '카테고리 이름',
    is_leaf     BOOLEAN     NOT NULL DEFAULT FALSE COMMENT '최하위 카테고리 여부',
    PRIMARY KEY (category_id),
    FOREIGN KEY (parent_id) REFERENCES category (category_id) ON DELETE CASCADE
);

CREATE TABLE product
(
    product_id  BIGINT                                 NOT NULL AUTO_INCREMENT COMMENT '상품 고유 ID',
    seller_id   BIGINT                                 NOT NULL COMMENT '등록자 ID',
    category_id BIGINT                                 NOT NULL COMMENT '카테고리 ID',
    location_id BIGINT                                 NOT NULL COMMENT '거래 지역 ID',
    title       VARCHAR(100)                           NOT NULL COMMENT '상품 제목',
    description TEXT                                   NULL COMMENT '상품 설명',
    price       BIGINT                                 NOT NULL COMMENT '가격(원)',
    view_count  BIGINT                                 NOT NULL DEFAULT 0 COMMENT '조회수',
    `condition` ENUM ('NEW', 'USED')                   NOT NULL COMMENT '상태(새 상품/중고)',
    trade_type  ENUM ('BOTH', 'DELIVERY', 'DIRECT')    NOT NULL COMMENT '거래 방식(직거래/택배)',
    status      ENUM ('AVAILABLE', 'RESERVED', 'SOLD') NOT NULL DEFAULT 'AVAILABLE' COMMENT '상태(판매 중/예약됨/판매 완료)',
    is_active   BOOLEAN                                NOT NULL DEFAULT TRUE COMMENT '상품 활성화 여부',
    is_deleted  BOOLEAN                                NOT NULL DEFAULT FALSE COMMENT '상품 삭제 여부',
    created_at  DATETIME                               NOT NULL COMMENT '상품 등록 일시',
    updated_at  DATETIME                               NULL COMMENT '상품 수정 일시',
    deleted_at  DATETIME                               NULL COMMENT '상품 삭제 일시',
    PRIMARY KEY (product_id),
    FOREIGN KEY (seller_id) REFERENCES user (user_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES category (category_id) ON DELETE RESTRICT,
    FOREIGN KEY (location_id) REFERENCES location (location_id) ON DELETE RESTRICT
);

CREATE TABLE product_image
(
    image_id   BIGINT       NOT NULL AUTO_INCREMENT COMMENT '이미지 고유 ID',
    product_id BIGINT       NOT NULL COMMENT '상품 ID',
    image_url  VARCHAR(255) NOT NULL COMMENT '이미지 URL',
    PRIMARY KEY (image_id),
    FOREIGN KEY (product_id) REFERENCES product (product_id) ON DELETE CASCADE
);

CREATE TABLE favorite
(
    favorite_id BIGINT   NOT NULL AUTO_INCREMENT COMMENT '찜 고유 ID',
    user_id     BIGINT   NOT NULL COMMENT '사용자 ID',
    product_id  BIGINT   NOT NULL COMMENT '상품 ID',
    created_at  DATETIME NOT NULL COMMENT '찜 등록 일시',
    PRIMARY KEY (favorite_id),
    FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product (product_id) ON DELETE CASCADE,
    UNIQUE KEY (user_id, product_id)
);

CREATE TABLE chat
(
    chat_id         BIGINT   NOT NULL AUTO_INCREMENT COMMENT '채팅방 고유 ID',
    product_id      BIGINT   NOT NULL COMMENT '상품 ID',
    buyer_id        BIGINT   NOT NULL COMMENT '구매자 ID',
    seller_id       BIGINT   NOT NULL COMMENT '판매자 ID',
    created_at      DATETIME NOT NULL COMMENT '채팅 시작 일시',
    last_message_at DATETIME NULL COMMENT '마지막 메시지 시간',
    PRIMARY KEY (chat_id),
    FOREIGN KEY (product_id) REFERENCES product (product_id) ON DELETE RESTRICT,
    FOREIGN KEY (buyer_id) REFERENCES user (user_id) ON DELETE RESTRICT,
    FOREIGN KEY (seller_id) REFERENCES user (user_id) ON DELETE RESTRICT,
    UNIQUE KEY (product_id, buyer_id, seller_id)
);

CREATE TABLE message
(
    message_id BIGINT       NOT NULL AUTO_INCREMENT COMMENT '메시지 고유 ID',
    chat_id    BIGINT       NOT NULL COMMENT '채팅방 고유 ID',
    sender_id  BIGINT       NOT NULL COMMENT '발신자 ID',
    content    TEXT         NULL COMMENT '메시지 내용(텍스트)',
    image_url  VARCHAR(255) NULL COMMENT '이미지 URL(옵션)',
    is_read    BOOLEAN      NOT NULL DEFAULT FALSE COMMENT '읽음 여부',
    is_deleted BOOLEAN      NOT NULL DEFAULT FALSE COMMENT '메시지 삭제 여부',
    sent_at    DATETIME     NOT NULL COMMENT '전송 일시',
    deleted_at DATETIME     NULL COMMENT '메시지 삭제 일시',
    PRIMARY KEY (message_id),
    FOREIGN KEY (chat_id) REFERENCES chat (chat_id) ON DELETE CASCADE,
    FOREIGN KEY (sender_id) REFERENCES user (user_id) ON DELETE RESTRICT
);

CREATE TABLE transaction
(
    transaction_id BIGINT                                     NOT NULL AUTO_INCREMENT COMMENT '거래 고유 ID',
    product_id     BIGINT                                     NOT NULL COMMENT '상품 ID',
    buyer_id       BIGINT                                     NOT NULL COMMENT '구매자 ID',
    seller_id      BIGINT                                     NOT NULL COMMENT '판매자 ID',
    status         ENUM ('PENDING', 'COMPLETED', 'CANCELLED') NOT NULL DEFAULT 'PENDING' COMMENT '거래 상태',
    created_at     DATETIME                                   NOT NULL COMMENT '거래 시작 일시',
    completed_at   DATETIME                                   NULL COMMENT '거래 완료 일시',
    PRIMARY KEY (transaction_id),
    FOREIGN KEY (product_id) REFERENCES product (product_id) ON DELETE RESTRICT,
    FOREIGN KEY (buyer_id) REFERENCES user (user_id) ON DELETE RESTRICT,
    FOREIGN KEY (seller_id) REFERENCES user (user_id) ON DELETE RESTRICT
);

CREATE TABLE report
(
    report_id        BIGINT   NOT NULL AUTO_INCREMENT COMMENT '신고 고유 ID',
    reporter_id      BIGINT   NOT NULL COMMENT '신고자 ID',
    product_id       BIGINT   NULL COMMENT '신고된 상품 ID(옵션)',
    reported_user_id BIGINT   NULL COMMENT '신고된 사용자 ID(옵션)',
    reason           TEXT     NOT NULL COMMENT '신고 사유',
    created_at       DATETIME NOT NULL COMMENT '신고 일시',
    PRIMARY KEY (report_id),
    FOREIGN KEY (reporter_id) REFERENCES user (user_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product (product_id) ON DELETE CASCADE,
    FOREIGN KEY (reported_user_id) REFERENCES user (user_id) ON DELETE CASCADE
);

CREATE TABLE search_history
(
    search_id   BIGINT       NOT NULL AUTO_INCREMENT COMMENT '검색 기록 ID',
    user_id     BIGINT       NOT NULL COMMENT '사용자 ID',
    keyword     VARCHAR(100) NOT NULL COMMENT '검색어',
    searched_at DATETIME     NOT NULL COMMENT '검색 일시',
    PRIMARY KEY (search_id),
    FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE
);

CREATE TABLE block
(
    block_id        BIGINT   NOT NULL AUTO_INCREMENT COMMENT '차단 고유 ID',
    user_id         BIGINT   NOT NULL COMMENT '차단한 사용자 ID',
    blocked_user_id BIGINT   NOT NULL COMMENT '차단된 사용자 ID',
    created_at      DATETIME NOT NULL COMMENT '차단 일시',
    PRIMARY KEY (block_id),
    FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE,
    FOREIGN KEY (blocked_user_id) REFERENCES user (user_id) ON DELETE CASCADE
);

CREATE VIEW product_detail AS
SELECT p.product_id,
       p.seller_id,
       p.category_id,
       p.location_id,
       (SELECT pi.image_url
        FROM product_image AS pi
        WHERE pi.product_id = p.product_id
        ORDER BY pi.image_id
        LIMIT 1)            AS thumbnail,
       p.title,
       p.description,
       p.price,
       p.view_count,
       COUNT(f.favorite_id) AS favorite_count,
       p.condition,
       p.trade_type,
       p.status,
       p.is_active,
       p.is_deleted,
       p.created_at,
       p.updated_at,
       p.deleted_at
FROM product AS p
         LEFT JOIN favorite AS f ON p.product_id = f.product_id
GROUP BY p.product_id;

LOAD DATA INFILE '/var/lib/mysql-files/location.csv'
    INTO TABLE location
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES
    (@code, @name, @address, @coordinates)
    SET
        code = @code,
        name = @name,
        address = @address,
        coordinates = ST_GeomFromText(@coordinates, 4326);

LOAD DATA INFILE '/var/lib/mysql-files/category.csv'
    INTO TABLE category
    FIELDS TERMINATED BY ','
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES
    (@category_id, @level, @parent_id, @path, @name, @is_leaf)
    SET
        category_id = @category_id,
        level = @level,
        parent_id = NULLIF(@parent_id, ''),
        path = @path,
        name = @name,
        is_leaf = @is_leaf;
