CREATE TABLE tournaments (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(2000) NOT NULL,
    max_players INTEGER NOT NULL,
    prize_fund NUMERIC(19, 2) NOT NULL,
    starts_at TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT chk_tournament_max_players CHECK (max_players >= 2),
    CONSTRAINT chk_tournament_prize_fund CHECK (prize_fund >= 0),
    CONSTRAINT chk_tournament_status CHECK (status IN ('REGISTRATION', 'IN_PROGRESS', 'FINISHED'))
);

CREATE UNIQUE INDEX uk_tournament_title ON tournaments (lower(title));

CREATE TABLE players (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP(6) WITH TIME ZONE NOT NULL,
    username VARCHAR(100) NOT NULL,
    rating INTEGER NOT NULL,
    seed INTEGER,
    tournament_id UUID,
    CONSTRAINT uk_player_username UNIQUE (username),
    CONSTRAINT chk_player_rating CHECK (rating >= 0),
    CONSTRAINT chk_player_seed CHECK (seed IS NULL OR seed > 0),
    CONSTRAINT fk_player_tournament FOREIGN KEY (tournament_id)
        REFERENCES tournaments (id) ON DELETE RESTRICT,
    CONSTRAINT uk_player_tournament_seed UNIQUE (tournament_id, seed)
);

CREATE INDEX idx_players_tournament_id ON players (tournament_id);
