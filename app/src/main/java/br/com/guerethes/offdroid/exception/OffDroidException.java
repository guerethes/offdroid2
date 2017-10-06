package br.com.guerethes.offdroid.exception;

@SuppressWarnings("serial")
public class OffDroidException extends Exception {

	public OffDroidException(String msg) {
		super(msg);
	}
	
	public OffDroidException(String msg, Exception e) {
		super(msg, e);
	}
	
}