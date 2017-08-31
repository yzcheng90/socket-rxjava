package com.sukeintel.iexhibition.mobile.app.works.mvp.base;


/**
 * @category app返回类
 * @author czx 
 * 2017-04-25
 */
public class AppBaseResult<T> {


	private boolean status;
	private String message;
	private int total;
	private T data;

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data =  data;
	}

}
