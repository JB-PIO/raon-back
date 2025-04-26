CREATE TABLE location
(
    location_id BIGINT      NOT NULL AUTO_INCREMENT COMMENT '지역 고유 ID',
    city        VARCHAR(30) NOT NULL COMMENT '시/도',
    district    VARCHAR(30) NOT NULL COMMENT '구/군',
    PRIMARY KEY (location_id),
    UNIQUE KEY (city, district)
);

CREATE TABLE user
(
    user_id       BIGINT       NOT NULL AUTO_INCREMENT COMMENT '사용자 고유 ID',
    nickname      VARCHAR(30)  NOT NULL UNIQUE COMMENT '닉네임',
    email         VARCHAR(80)  NOT NULL UNIQUE COMMENT '이메일',
    password      VARCHAR(255) NULL COMMENT '해시된 비밀번호',
    profile_image VARCHAR(255) NULL COMMENT '프로필 사진 URL',
    location_id   BIGINT       NULL COMMENT '사용자 기본 지역',
    is_deleted    BOOLEAN      NOT NULL DEFAULT FALSE COMMENT '계정 삭제 여부',
    is_suspended  BOOLEAN      NOT NULL DEFAULT FALSE COMMENT '계정 정지 여부',
    created_at    DATETIME     NOT NULL COMMENT '가입 일시',
    updated_at    DATETIME     NULL COMMENT '정보 수정 일시',
    deleted_at    DATETIME     NULL COMMENT '계정 삭제 일시',
    suspended_at  DATETIME     NULL COMMENT '계정 정지 일시',
    PRIMARY KEY (user_id),
    FOREIGN KEY (location_id) REFERENCES location (location_id) ON DELETE SET NULL
);

CREATE TABLE category
(
    category_id BIGINT      NOT NULL AUTO_INCREMENT COMMENT '카테고리 고유 ID',
    name        VARCHAR(30) NOT NULL UNIQUE COMMENT '카테고리 이름',
    created_at  DATETIME    NOT NULL COMMENT '카테고리 생성 일시',
    PRIMARY KEY (category_id)
);

CREATE TABLE product
(
    product_id     BIGINT                              NOT NULL AUTO_INCREMENT COMMENT '상품 고유 ID',
    user_id        BIGINT                              NOT NULL COMMENT '등록자 ID',
    category_id    BIGINT                              NOT NULL COMMENT '카테고리 ID',
    location_id    BIGINT                              NULL COMMENT '거래 지역 ID',
    title          VARCHAR(100)                        NOT NULL COMMENT '상품 제목',
    description    TEXT                                NULL COMMENT '상품 설명',
    price          BIGINT                              NOT NULL COMMENT '가격(원)',
    view_count     BIGINT                              NOT NULL DEFAULT 0 COMMENT '조회수',
    favorite_count BIGINT                              NOT NULL DEFAULT 0 COMMENT '찜 개수',
    status         ENUM ('NEW', 'USED')                NOT NULL COMMENT '상태(새 상품/중고)',
    trade_type     ENUM ('DIRECT', 'DELIVERY', 'BOTH') NOT NULL COMMENT '거래 방식(직거래/택배)',
    is_sold        BOOLEAN                             NOT NULL DEFAULT FALSE COMMENT '판매 완료 여부',
    is_active      BOOLEAN                             NOT NULL DEFAULT TRUE COMMENT '상품 활성화 여부',
    is_deleted     BOOLEAN                             NOT NULL DEFAULT FALSE COMMENT '상품 삭제 여부',
    created_at     DATETIME                            NOT NULL COMMENT '상품 등록 일시',
    updated_at     DATETIME                            NULL COMMENT '상품 수정 일시',
    deleted_at     DATETIME                            NULL COMMENT '상품 삭제 일시',
    PRIMARY KEY (product_id),
    FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES category (category_id) ON DELETE RESTRICT,
    FOREIGN KEY (location_id) REFERENCES location (location_id) ON DELETE SET NULL
);

CREATE TABLE product_image
(
    image_id      BIGINT       NOT NULL AUTO_INCREMENT COMMENT '이미지 고유 ID',
    product_id    BIGINT       NOT NULL COMMENT '상품 ID',
    display_order BIGINT       NOT NULL DEFAULT 0 COMMENT '표시 순서',
    image_url     VARCHAR(255) NOT NULL COMMENT '이미지 URL',
    uploaded_at   DATETIME     NOT NULL COMMENT '업로드 일시',
    PRIMARY KEY (image_id),
    FOREIGN KEY (product_id) REFERENCES product (product_id) ON DELETE CASCADE,
    UNIQUE KEY (product_id, display_order)
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
    FOREIGN KEY (product_id) REFERENCES product (product_id) ON DELETE CASCADE,
    FOREIGN KEY (buyer_id) REFERENCES user (user_id) ON DELETE CASCADE,
    FOREIGN KEY (seller_id) REFERENCES user (user_id) ON DELETE CASCADE,
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
    FOREIGN KEY (sender_id) REFERENCES user (user_id) ON DELETE CASCADE
);

CREATE TABLE transaction
(
    transaction_id BIGINT                                     NOT NULL AUTO_INCREMENT COMMENT '거래 고유 ID',
    product_id     BIGINT                                     NOT NULL COMMENT '상품 ID',
    buyer_id       BIGINT                                     NOT NULL COMMENT '구매자 ID',
    seller_id      BIGINT                                     NOT NULL COMMENT '판매자 ID',
    status         ENUM ('PENDING', 'COMPLETED', 'CANCELLED') NOT NULL COMMENT '거래 상태',
    created_at     DATETIME                                   NOT NULL COMMENT '거래 시작 일시',
    completed_at   DATETIME                                   NULL COMMENT '거래 완료 일시',
    PRIMARY KEY (transaction_id),
    FOREIGN KEY (product_id) REFERENCES product (product_id) ON DELETE CASCADE,
    FOREIGN KEY (buyer_id) REFERENCES user (user_id) ON DELETE CASCADE,
    FOREIGN KEY (seller_id) REFERENCES user (user_id) ON DELETE CASCADE
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
