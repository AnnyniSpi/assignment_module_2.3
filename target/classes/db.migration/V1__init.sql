CREATE TABLE writer
(
    id            BIGSERIAL PRIMARY KEY,
    firstname     VARCHAR(128),
    lastname      VARCHAR(128),
    writer_status VARCHAR(64)
);

CREATE TABLE post
(
    id          BIGSERIAL PRIMARY KEY,
    content     TEXT,
    created     TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated     TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    post_status VARCHAR(64),
    writer_id   BIGINT REFERENCES writer (id)

);

CREATE TABLE label
(
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(128),
    label_status VARCHAR(64),
    post_id      BIGINT REFERENCES post (id)
);

CREATE TABLE post_label
(
    post_id  BIGINT REFERENCES post (id),
    label_id BIGINT REFERENCES label (id),
    PRIMARY KEY (post_id, label_id)
);