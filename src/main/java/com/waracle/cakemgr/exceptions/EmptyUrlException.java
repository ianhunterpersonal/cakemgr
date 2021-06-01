package com.waracle.cakemgr.exceptions;

public class EmptyUrlException extends RuntimeException {

	private static final long serialVersionUID = 2721065165701643029L;

	public EmptyUrlException() {
		super(String.format("Cake must have an Image URL"));
	}

}
