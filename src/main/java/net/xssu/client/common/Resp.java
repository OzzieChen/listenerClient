package net.xssu.client.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Resp<T> implements Serializable {

	private static final long serialVersionUID = 3214556909344022680L;

	private int error_code;
	private String reason;
	private List<T> result;

	public Resp() {
		this.error_code = 1;
	}

	public void add(T t) {
		if(result == null)
			result = new ArrayList<T>(1);
		result.add(t);
	}

	public int getError_code() {
		return error_code;
	}

	public void setError_code(int error_code) {
		this.error_code = error_code;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}
}
