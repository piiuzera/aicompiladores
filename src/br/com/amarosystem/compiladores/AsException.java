package br.com.amarosystem.compiladores;

/**
 * Classe que manipula as exce��es do sistema.
 * @author Matheus Amaro
 *
 */
public class AsException extends Exception {
	private static final long serialVersionUID = 4843989142094211595L;
	
	private String code;
	private String details;
	
	public AsException(String message) {
		super(message);
		this.code = "";
		this.details = "";
	}
	public AsException(String message, String code) {
		super(message);
		this.code = code;
		this.details = "";		
	}
	public AsException(String message, String code, String details) {
		super(message);
		this.code = code;
		this.details = details;
	}
	
	@Override
	public String getMessage() {
		if(this.code.isEmpty()) {
			return super.getMessage();
		}
		return this.code + " - " + super.getMessage();
	}
	
	public String getCode() {
		return code;
	}

	public String getDetails() {
		return details;
	}
}
