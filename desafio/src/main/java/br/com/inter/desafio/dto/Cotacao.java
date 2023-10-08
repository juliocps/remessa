package br.com.inter.desafio.dto;

import java.util.ArrayList;

/**
 * DTO para conversão do json retornado pela api da cotacao
 * @author Júlio Silva
 *
 */
public class Cotacao {	  
	private ArrayList<Value> value;

		public ArrayList<Value> getValue() {
			return value;
		}

		public void setValue(ArrayList<Value> value) {
			this.value = value;
		} 	   	    
}




