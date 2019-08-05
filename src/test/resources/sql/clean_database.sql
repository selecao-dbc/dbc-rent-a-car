    
    DELETE FROM tb_acessorio;

    
    DELETE FROM tb_equipamento;

    
    DELETE FROM tb_locacao;

    
    DELETE FROM tb_marca;

    
    DELETE FROM tb_modelo;

    
    DELETE FROM tb_reserva;

    
    DELETE FROM tb_veiculo;

    
    ALTER SEQUENCE  sq_acessorio RESTART WITH 1;

    
    ALTER SEQUENCE  sq_equipamento RESTART WITH 1;

    
    ALTER SEQUENCE  sq_locacao RESTART WITH 1;

    
    ALTER SEQUENCE  sq_marca RESTART WITH 1;

    
    ALTER SEQUENCE  sq_modelo RESTART WITH 1;

    
    ALTER SEQUENCE  sq_reserva RESTART WITH 1;

    
    ALTER SEQUENCE  sq_veiculo RESTART WITH 1;