package br.com.targettrust.traccadastros.config;

public class ErroModel {
	private String erro;
	private String message;

	public ErroModel(String erro, String message) {
		super();
		this.erro = erro;
		this.message = message;
	}

	public String getErro() {
		return erro;
	}

	public String getMessage() {
		return message;
	}

}
