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
        
        
        
        
-- INSERT INTO MODELO CARRO       

insert into tb_modelo (id, version, mdl_nome, mdl_versao, mdl_ano, id_marca ) 
values (nextval('sq_modelo'), 0, 'Palio 1.0', 'ECONOMY Fire Flex 8V', 2013, ( SELECT id FROM tb_marca WHERE mar_nome = 'Fiat'));
	                        
insert into tb_modelo (id, version, mdl_nome, mdl_versao, mdl_ano, id_marca ) 
values (nextval('sq_modelo'), 0, 'Fiesta 1.0', 'Flex 8V', 2015, ( SELECT id FROM tb_marca WHERE mar_nome = 'Ford'));




-- INSERT INTO MODELO MOTO    

insert into tb_modelo (id, version, mdl_nome, mdl_versao, mdl_ano, id_marca ) 
values (nextval('sq_modelo'), 0, 'F700', 'GS', 2013, ( SELECT id FROM tb_marca WHERE mar_nome = 'BMW'));
	                        
insert into tb_modelo (id, version, mdl_nome, mdl_versao, mdl_ano, id_marca ) 
values (nextval('sq_modelo'), 0, 'R1200', 'RT', 2015, ( SELECT id FROM tb_marca WHERE mar_nome = 'BMW'));




-- INSERT INTO VEICULO TIPO CARRO     
	                     
insert into tb_veiculo(id, version, tipo, vcl_placa, vcl_ano_fabricacao, vcl_ano_modelo, vcl_cor, vcl_portas, id_modelo) 
values (nextval ('sq_veiculo'), 0, 'CARRO','JSQ-0101', 2013, 2013, 'vermelho', 4, (SELECT id FROM tb_modelo WHERE mdl_nome = 'Palio 1.0'));

insert into tb_veiculo(id, version, tipo, vcl_placa, vcl_ano_fabricacao, vcl_ano_modelo, vcl_cor, vcl_portas, id_modelo) 
values (nextval ('sq_veiculo'), 0, 'CARRO','JSQ-9999', 2015, 2015, 'prata', 4, (SELECT id FROM tb_modelo WHERE mdl_nome = 'Fiesta 1.0'));

	
	
	
-- INSERT INTO VEICULO TIPO MOTO     
	                     
insert into tb_veiculo(id, version, tipo, vcl_placa, vcl_ano_fabricacao, vcl_ano_modelo, vcl_cor, vcl_cilindradas, id_modelo) 
values (nextval ('sq_veiculo'), 0, 'MOTO','JSQ-0202', 2013, 2013, 'vermelho', 700, (SELECT id FROM tb_modelo WHERE mdl_nome = 'F700'));

insert into tb_veiculo(id, version, tipo, vcl_placa, vcl_ano_fabricacao, vcl_ano_modelo, vcl_cor, vcl_cilindradas, id_modelo) 
values (nextval ('sq_veiculo'), 0, 'MOTO','JSQ-4444', 2015, 2015, 'prata', 1200, (SELECT id FROM tb_modelo WHERE mdl_nome = 'R1200'));

	
	
	
-- INSERT INTO LOCACAO	

insert into tb_locacao(id, version, vlr_pago, dt_inicio, dt_fim, id_veiculo) 
values (nextval ('sq_locacao'), 0, 20, '2019-08-06', '2019-08-16', (SELECT id FROM tb_veiculo WHERE vcl_placa = 'JSQ-0101'));

   
insert into tb_locacao(id, version, vlr_pago, dt_inicio, dt_fim, id_veiculo) 
values (nextval ('sq_locacao'), 0, 20, '2019-08-14', '2019-08-23', (SELECT id FROM tb_veiculo WHERE vcl_placa = 'JSQ-0202'));
        
        