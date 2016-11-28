package br.com.amarosystem.compiladores;

import java.io.File;

import br.com.amarosystem.compiladores.controllers.IndexController;
import br.com.amarosystem.compiladores.enumtypes.Status;
import br.com.amarosystem.compiladores.models.Teste;
import br.com.amarosystem.compiladores.util.AsList;
import br.com.amarosystem.compiladores.util.AsUtil;

/**
 * Classe principal do sistema.
 * @author Matheus Amaro
 *
 */
public class Program {	
	private static Program instance;
	
	private AsList<Teste> listMassaTeste;
	
	/**
	 * Instância através de um singleton.
	 * @return
	 */
	public static Program getInstance() {
		if(instance == null) {
			instance = new Program();
		}
		return instance;
	}
	
	private Program() {
		criaArquivoLocal();
	}
	
	/**
	 * Através do automato do analisador léxico 4, ele verifica se esta válida ou não a expressão.
	 * @param codigo
	 * @return
	 * @throws AsException
	 */
	public boolean isCodigoValido(String codigo) throws AsException {
		if(!codigo.isEmpty()) {
			int estado = 0;
			
			for(int i = 0; i < codigo.length(); i++) {
				char objChar = codigo.toCharArray()[i];
				if(estado == 0) {
					if(objChar == ' ') {
						estado = 0;
					} else if(objChar == '/') {
						estado = 1;
					} else if(Character.isLetter(objChar)) {
						estado = 4;
					} else {
						throw new AsException("Expressão informada está Inválida!", "ESTADO: " + estado);
					}
				} else if(estado == 1) {
					if(objChar == '*') {
						estado = 2;
					} else {
						throw new AsException("Expressão informada está Inválida!", "ESTADO: " + estado);
					}
				} else if(estado == 2) {
					if(objChar == '*') {
						estado = 3;
					} else {
						estado = 2;
					}
				} else if(estado == 3) {
					if(objChar == '/') {
						estado = 0;
					} else if(objChar == '*') {
						estado = 3;
					} else {
						estado = 2;
					}
				} else if(estado == 4) {
					if(Character.isLetter(objChar)) {
						estado = 4;
					} else if(Character.isDigit(objChar)) {
						estado = 5;
					} else if(objChar == ' ') {
						estado = 6;
					} else if(objChar == '/') {
						estado = 7;
					} else if(objChar == '=') {
						estado = 10;
					} else {
						throw new AsException("Expressão informada está Inválida!", "ESTADO: " + estado);
					}
				} else if(estado == 5) {
					if(Character.isDigit(objChar)) {
						estado = 5;
					} else if(objChar == ' ') {
						estado = 6;
					} else if(objChar == '/') {
						estado = 7;
					} else if(objChar == '=') {
						estado = 10;
					} else {
						throw new AsException("Expressão informada está Inválida!", "ESTADO: " + estado);
					}
				} else if(estado == 6) {
					if(objChar == ' ') {
						estado = 6;
					} else if(objChar == '/') {
						estado = 7;
					} else if(objChar == '=') {
						estado = 10;
					} else {
						throw new AsException("Expressão informada está Inválida!", "ESTADO: " + estado);
					}
				} else if(estado == 7) {
					if(objChar == '*') {
						estado = 8;
					} else {
						throw new AsException("Expressão informada está Inválida!", "ESTADO: " + estado);
					}
				} else if(estado == 8) {
					if(objChar == '*') {
						estado = 9;
					} else {
						estado = 8;
					}
				} else if(estado == 9) {
					if(objChar == '/') {
						estado = 6;
					} else if(objChar == '*') {
						estado = 9;
					} else {
						estado = 8;
					}
				} else if(estado == 10) {
					if(objChar == ' ') {
						estado = 10;
					} else if(objChar == '/') {
						estado = 11;
					} else if(Character.isLetter(objChar)) {
						estado = 14;
					} else if(Character.isDigit(objChar)) {
						estado = 15;
					} else {
						throw new AsException("Expressão informada está Inválida!", "ESTADO: " + estado);
					}
				} else if(estado == 11) {
					if(objChar == '*') {
						estado = 12;
					} else {
						throw new AsException("Expressão informada está Inválida!", "ESTADO: " + estado);
					}
				} else if(estado == 12) {
					if(objChar == '*') {
						estado = 13;
					} else {
						estado = 12;
					}
				} else if(estado == 13) {
					if(objChar == '/') {
						estado = 10;
					} else if(objChar == '*') {
						estado = 13;
					} else {
						estado = 12;
					}
				} else if(estado == 14) {
					if(Character.isLetter(objChar)) {
						estado = 14;
					} else if(Character.isDigit(objChar)) {
						estado = 15;
					} else if(objChar == ' ') {
						estado = 16;
					} else if(objChar == '/') {
						estado = 17;
					} else if(AsUtil.getInstance().isOperator(objChar)) {
						estado = 20;
					} else {
						throw new AsException("Expressão informada está Inválida!", "ESTADO: " + estado);
					}
				} else if(estado == 15) {
					if(Character.isDigit(objChar)) {
						estado = 15;
					} else if(objChar == ' ') {
						estado = 16;
					} else if(objChar == '/') {
						estado = 17;
					} else if(AsUtil.getInstance().isOperator(objChar)) {
						estado = 20;
					} else {
						throw new AsException("Expressão informada está Inválida!", "ESTADO: " + estado);
					}
				} else if(estado == 16) {
					if(objChar == ' ') {
						estado = 16;
					} else if(objChar == '/') {
						estado = 17;
					} else if(AsUtil.getInstance().isOperator(objChar)) {
						estado = 20;
					} else {
						throw new AsException("Expressão informada está Inválida!", "ESTADO: " + estado);
					}
				} else if(estado == 17) {
					if(objChar == '*') {
						estado = 18;
					} else if(objChar == '/') {
						estado = 21;
					} else if(objChar == ' ') {
						estado = 20;
					} else if(Character.isLetter(objChar)) {
						estado = 24;
					} else if(Character.isDigit(objChar)) {
						estado = 25;
					} else {
						throw new AsException("Expressão informada está Inválida!", "ESTADO: " + estado);
					}
				} else if(estado == 18) {
					if(objChar == '*') {
						estado = 19;
					} else {
						estado = 18;
					}
				} else if(estado == 19) {
					if(objChar == '/') {
						estado = 16;
					} else if(objChar == '*') {
						estado = 19;
					} else {
						estado = 18;
					}
				} else if(estado == 20) {
					if(objChar == ' ') {
						estado = 20;
					} else if(objChar == '/') {
						estado = 21;
					} else if(Character.isLetter(objChar)) {
						estado = 24;
					} else if(Character.isDigit(objChar)) {
						estado = 25;
					} else {
						throw new AsException("Expressão informada está Inválida!", "ESTADO: " + estado);
					}
				} else if(estado == 21) {
					if(objChar == '*') {
						estado = 22;
					} else {
						throw new AsException("Expressão informada está Inválida!", "ESTADO: " + estado);
					}
				} else if(estado == 22) {
					if(objChar == '*') {
						estado = 23;
					} else {
						estado = 22;
					}
				} else if(estado == 23) {
					if(objChar == '/') {
						estado = 20;
					} else if(objChar == '*') {
						estado = 23;
					} else {
						estado = 22;
					}
				} else if(estado == 24) {
					if(Character.isLetter(objChar)) {
						estado = 24;
					} else if(Character.isDigit(objChar)) {
						estado = 25;
					} else if(objChar == ' ') {
						estado = 26;
					} else if(objChar == '/') {
						estado = 27;
					} else if(AsUtil.getInstance().isOperator(objChar)) {
						estado = 20;
					}
				} else if(estado == 25) {
					if(Character.isDigit(objChar)) {
						estado = 25;
					} else if(objChar == ' ') {
						estado = 26;
					} else if(objChar == '/') {
						estado = 27;
					} else if(AsUtil.getInstance().isOperator(objChar)) {
						estado = 20;
					}
				} else if(estado == 26) {
					if(objChar == ' ') {
						estado = 26;
					} else if(objChar == '/') {
						estado = 27;
					} else if(AsUtil.getInstance().isOperator(objChar)) {
						estado = 20;
					}
				} else if(estado == 27) {
					if(objChar == '*') {
						estado = 28;
					} else if(objChar == ' ') {
						estado = 20;
					} else if(objChar == '/') {
						estado = 21;
					} else if(Character.isLetter(objChar)) {
						estado = 24;
					} else if(Character.isDigit(objChar)) {
						estado = 25;
					} else {
						throw new AsException("Expressão informada está Inválida!", "ESTADO: " + estado);
					}
				} else if(estado == 28) {
					if(objChar == '*') {
						estado = 29;
					} else {
						estado = 28;
					}
				} else if(estado == 29) {
					if(objChar == '/') {
						estado = 26;
					} else if(objChar == '*') {
						estado = 29;
					} else {
						estado = 28;
					}
				}
			}
			
			if(estado == 24 || estado == 25 || estado == 26) {
				return true;
			} else {
				throw new AsException("Expressão informada está Inválida!", "AS8x10");
			}
		}
		
		throw new AsException("Expressão informada não pode ser vazio!", "AS8x11");
	}
	
	public AsList<Teste> getListMassaTeste() {
		return listMassaTeste;
	}
	
	@SuppressWarnings("unchecked")
	private void criaArquivoLocal() {
		File file = new File(System.getenv("LOCALAPPDATA") + "\\ASAAI");
		file.mkdir();
		
		try {
			file = new File(file.getPath() + "\\MassaTeste.aai");
			if(file != null && file.exists()) {
				listMassaTeste = (AsList<Teste>)AsUtil.getInstance().getFileObject(file.getAbsoluteFile().getAbsolutePath());
			} else {
				listMassaTeste = new AsList<Teste>();
				
				listMassaTeste.add(new Teste(Status.ERROR, "5v= 2 + 3"));
				listMassaTeste.add(new Teste(Status.SUCCESS, "v = 5 + 2"));
				listMassaTeste.add(new Teste(Status.ERROR, "6 = 3 + 12"));
				listMassaTeste.add(new Teste(Status.SUCCESS, "v = v /*aaa***/ + 2"));
				listMassaTeste.add(new Teste(Status.SUCCESS, "/*aaa***/ v = 5 / v + 2"));
				listMassaTeste.add(new Teste(Status.ERROR, "//     aaa   v + 2"));
				listMassaTeste.add(new Teste(Status.ERROR, "v =      /*aaa***/ + 2"));
				listMassaTeste.add(new Teste(Status.ERROR, "v = v + 2 = v ++ 1"));
				listMassaTeste.add(new Teste(Status.SUCCESS, "v =        v + /*aaa***/2"));
				listMassaTeste.add(new Teste(Status.ERROR, "v === + 3 -5 * 4"));
				listMassaTeste.add(new Teste(Status.SUCCESS, "VR = VR + 5"));
				listMassaTeste.add(new Teste(Status.SUCCESS, "VR1 = 5 + 3 / 2"));
				listMassaTeste.add(new Teste(Status.ERROR, "VR = VR1A + 15 / 4"));
				listMassaTeste.add(new Teste(Status.ERROR, "VR = VR1"));
				listMassaTeste.add(new Teste(Status.ERROR, "VR25B = VR + 3"));
				
				AsUtil.getInstance().setFileObject(file.getAbsoluteFile().getAbsolutePath(), listMassaTeste);
			}
		} catch (AsException ex) {
			listMassaTeste = new AsList<Teste>();
			
			ex.printStackTrace();
		}
	}
	
	public static void main(String [] args) {
		IndexController.getInstance();
	}
}
