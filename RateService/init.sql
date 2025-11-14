CREATE DATABASE music_rating_db ENCODING = 'UTF8';

\c music_rating_db;

CREATE TABLE rating (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    track_id BIGINT NOT NULL,
    stars INTEGER NOT NULL CHECK (stars >= 1 AND stars <= 5),
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unique_user_track UNIQUE (user_id, track_id)
);

CREATE TABLE download_count (
    track_id BIGINT PRIMARY KEY,
    download_count BIGINT DEFAULT 0
);
