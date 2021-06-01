package com.waracle.cakemgr.exceptions;

public class DuplicateTitleException extends RuntimeException {

	private static final long serialVersionUID = 1559972644154451906L;

	public DuplicateTitleException(String title) {
		super(String.format("Title for '%s' cake already exists in database.", title));
	}
}
