INSERT INTO clients(id, limite, saldo) VALUES (1,100000,0);
INSERT INTO clients(id, limite, saldo) VALUES (2,80000,0);
INSERT INTO clients(id, limite, saldo) VALUES (3,1000000,0);
INSERT INTO clients(id, limite, saldo) VALUES (4,10000000,0);
INSERT INTO clients(id, limite, saldo) VALUES (5,500000,0);


CREATE OR REPLACE FUNCTION funcoes(cliente_id int, valor_d int, descricao VARCHAR(10), tipo VARCHAR(1))
    RETURNS int 
    LANGUAGE plpgsql
    as
    $$
    declare 
        s_atual int;
        l_atual int;

    BEGIN 
        PERFORM pg_advisory_xact_lock(cliente_id);
        SELECT
            limite,
            saldo
        INTO
            l_atual,
            s_atual
        FROM clients WHERE id = cliente_id;

        IF tipo = 'd' THEN
            IF s_atual - valor_d >= l_atual * -1 THEN
                INSERT INTO transactions(client_Id,valor,tipo,descricao) VALUES (cliente_id,valor_d,'d',descricao);
                UPDATE clients SET saldo = s_atual - valor_d WHERE id = cliente_id;
                RETURN 1;
            ELSE
                RETURN 0;
            END IF;
        ELSE
            INSERT INTO transactions(client_Id,valor,tipo,descricao) VALUES (cliente_id,valor_d,'c',descricao);
            UPDATE clients SET saldo = s_atual + valor_d WHERE id = cliente_id;
            RETURN 1;
        END IF;
    END;
    $$;