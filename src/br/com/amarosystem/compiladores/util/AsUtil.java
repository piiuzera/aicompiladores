package br.com.amarosystem.compiladores.util;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.BorderFactory;

import br.com.amarosystem.compiladores.AsException;

public class AsUtil implements Serializable {
	private static final long serialVersionUID = -4285577937234044148L;
	
	private static AsUtil instance;
	
	public static AsUtil getInstance() {
		if(instance == null) {
			instance = new AsUtil();
		}
		return instance;
	}
	
	private AsUtil() {
		
	}
	
	/**
	 * Verifica se existem campos vazios, passando multiparâmetros.
	 * @param params todos os campos a serem visualizados
	 * @return
	 */
	public boolean isNotEmpty(Object... params) {
		boolean isReturn = true;
		for(Object obj : params) {
			if(obj instanceof javax.swing.JTextArea) {
				javax.swing.JTextArea objText = (javax.swing.JTextArea)obj;
				if(objText.getText().equals("")) {
					objText.setBorder(BorderFactory.createLineBorder(Color.RED));
					objText.requestFocus();
					isReturn = false;
				} else {
					objText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				}
			}
		}
		return isReturn;
	}
	
	/**
	 * Verifica através de um char, se o mesmo é um operador válido.
	 * @param objChar
	 * @return
	 */
	public boolean isOperator(char objChar) {
		return objChar == '+' || objChar == '-' || objChar == '*' || objChar == '/';
	}
	
	/**
	 * Elimina os Espaços de uma String
	 * @param value Texto
	 * @return Texto sem Espaço
	 */
	public String getStringNotSpace(String value) {
		String result = "";
		char [] vetChar = value.toCharArray();
		for(char objChar : vetChar) {
			String str = String.valueOf(objChar);
			if(!str.trim().isEmpty()) {
				result += str;
			}
		}
		return result;
	}
	
	/**
	 * Retorna o arquivo salvado em formato objeto.
	 * @param path caminho do arquivo
	 * @return
	 * @throws AsException
	 */
	public Object getFileObject(String path) throws AsException {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
			Object object = in.readObject();
			
			in.close();
			
			return object;
			
		} catch(IOException ex) {
			throw new AsException("Impossivel ler arquivo no servidor.",
					"AS8x122", ex.getMessage());
		} catch (ClassNotFoundException ex) {
			throw new AsException("Impossivel ler arquivo no servidor.",
					"AS8x123", ex.getMessage());
		}
	}
	 /**
	  * Salva o arquivo em formato objeto.
	  * @param fileName caminho mais o nome do arquivo
	  * @param object objeto delegado, para ser salvado em arquivo
	  * @throws AsException
	  */
	public void setFileObject(String fileName, Object object) throws AsException {
		try {
	        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
	        out.writeObject(object);
	        out.close();	
		} catch(IOException ex) {
			throw new AsException("Impossivel gravar arquivo no servidor.",
					"AS8x124", ex.getMessage());
		}
	}
}