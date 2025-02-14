CREATE TABLE fit_tracker.revinfo (
    rev BIGINT NOT NULL,
    revtstmp BIGINT,
    username VARCHAR(50),
    PRIMARY KEY (rev)
)