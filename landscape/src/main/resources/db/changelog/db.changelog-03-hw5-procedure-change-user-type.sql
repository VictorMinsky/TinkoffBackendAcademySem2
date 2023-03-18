--liquibase formatted sql

--changeset VictorMinsky:3
CREATE OR REPLACE PROCEDURE update_user_type(p_set_user_type INTEGER, p_batch_size INTEGER, p_pause_time INTEGER)
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_total_rows     INTEGER;
    v_processed_rows INTEGER := 0;
BEGIN
    --  get total number of rows in database
    SELECT count(*)
    INTO v_total_rows
    FROM users;

    --  loop while not all rows ace processed
    WHILE v_processed_rows < v_total_rows
        LOOP
            -- notify about the start of the operation
            RAISE NOTICE '% - processing batch of % rows...', now(), p_batch_size;

            -- updates user type's to `p_set_user_type`
            -- from `v_processed_rows` to `v_processed_rows + p_batch_size`
            UPDATE users
            SET user_type_id = p_set_user_type
            WHERE id IN (SELECT id
                         FROM users
                         ORDER BY created_at
                         LIMIT p_batch_size OFFSET v_processed_rows);

            -- increase total amount of processed rows
            v_processed_rows := v_processed_rows + p_batch_size;

            -- sleep for `p_pause_time` seconds
            PERFORM pg_sleep(p_pause_time);

            -- notify changes and sleep time
            RAISE NOTICE '% - Committed % rows. Migration paused for % sec.', now(), p_batch_size, p_pause_time;
        END LOOP;
END;
$$;