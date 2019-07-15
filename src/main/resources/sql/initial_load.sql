    insert
    into
        tb_marca
        (version, mar_nome, id)
    values
        (0, 'Ford', nextval ('sq_marca'));
    insert
    into
        tb_marca
        (version, mar_nome, id)
    values
        (0, 'Chevrolet', nextval ('sq_marca'));
    insert
    into
        tb_marca
        (version, mar_nome, id)
    values
        (0, 'Fiat', nextval ('sq_marca'));
    insert
    into
        tb_marca
        (version, mar_nome, id)
    values
        (0, 'Volkswagen', nextval ('sq_marca'));
    insert
    into
        tb_marca
        (version, mar_nome, id)
    values
        (0, 'BMW', nextval ('sq_marca'));
    insert
    into
        tb_marca
        (version, mar_nome, id)
    values
        (0, 'Mercedes Bens', nextval ('sq_marca'));
    insert
    into
        tb_marca
        (version, mar_nome, id)
    values
        (0, 'Renault', nextval ('sq_marca'));
    insert
    into
        tb_marca
        (version, mar_nome, id)
    values
        (0, 'Citroen', nextval ('sq_marca'));
    insert
    into
        tb_marca
        (version, mar_nome, id)
    values
        (0, 'Peugeot', nextval ('sq_marca'));
    insert
    into
        tb_marca
        (version, mar_nome, id)
    values
        (0, 'Hyunday', nextval ('sq_marca'));
    insert
    into
        tb_marca
        (version, mar_nome, id)
    values
        (0, 'Kia', nextval ('sq_marca'));
    insert
    into
        tb_marca
        (version, mar_nome, id)
    values
        (0, 'Ferrari', nextval ('sq_marca'));
    insert
    into
        tb_marca
        (version, mar_nome, id)
    values
        (0, 'Masserati', nextval ('sq_marca'));
    insert
    into
        tb_marca
        (version, mar_nome, id)
    values
        (0, 'Cherry', nextval ('sq_marca'));
    insert
    into
        tb_marca
        (version, mar_nome, id)
    values
        (0, 'Lifan', nextval ('sq_marca'));
    insert
    into
        tb_marca
        (version, mar_nome, id)
    values
        (0, 'Mitsubish', nextval ('sq_marca'));

INSERT INTO tb_modelo
	(id,version, mdl_ano, mdl_nome, mdl_versao, id_marca)
VALUES
	(nextval ('sq_modelo'),1, 2010, 'Uno', 'Mile', 3);

INSERT INTO tb_veiculo
	(tipo, id, version, vcl_ano_fabricacao, vcl_ano_modelo, vcl_cor, vcl_placa, vcl_portas, vcl_cilindradas, id_modelo)
values
	('CARRO',nextval('sq_veiculo') ,1, 2010, 2010, 'Preto', 'PPP-9999', 2, null, 1);