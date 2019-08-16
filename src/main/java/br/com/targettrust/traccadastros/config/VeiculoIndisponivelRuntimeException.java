package br.com.targettrust.traccadastros.config;


public class VeiculoIndisponivelRuntimeException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ErroModel erro;

	public VeiculoIndisponivelRuntimeException(String message) {
		super(message);
		erro = new ErroModel(ErrorCodes.VEICULO_INDISPONIVEL, message);
	}
	
	public ErroModel getErro() {
		return erro;
	}

	
}
