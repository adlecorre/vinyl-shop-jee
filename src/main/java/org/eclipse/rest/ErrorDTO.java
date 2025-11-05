package org.eclipse.rest;

public class ErrorDTO {
	
	private int code;
	private String message;
	private String path; // path de la ressource qui a provoqué l'erreur
	
	public ErrorDTO() {}

	public ErrorDTO(int code, String message, String path) {
		this.code = code;
		this.message = message;
		this.path = path;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
}
