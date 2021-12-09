DROP TABLE IF EXISTS public.game_state;
CREATE TABLE public.game_state
(
    id          serial                                                NOT NULL PRIMARY KEY,
    current_map text                                                  NOT NULL,
    other_map   text                                                  NOT NULL,
    saved_at    timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    player_id   serial                                                NOT NULL,
    save_name   text                                                  NOT NULL
);

DROP TABLE IF EXISTS public.player;
CREATE TABLE public.player
(
    id          serial  NOT NULL PRIMARY KEY,
    player_name text    NOT NULL,
    hp          integer NOT NULL,
    x           integer,
    y           integer,
    strength    integer,
    armor       integer
);

ALTER TABLE ONLY public.game_state
    ADD CONSTRAINT fk_player_id FOREIGN KEY (player_id) REFERENCES public.player (id);