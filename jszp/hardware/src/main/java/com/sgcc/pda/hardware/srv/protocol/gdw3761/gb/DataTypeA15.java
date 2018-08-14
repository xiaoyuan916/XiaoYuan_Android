package com.sgcc.pda.hardware.srv.protocol.gdw3761.gb;

import java.util.Date;

/**
 * 
 * @author luxiaochung
 */
public class DataTypeA15 extends DataTypeDateBase {
	public DataTypeA15(Date date) {
		super();
		setDate(date);
	}

	public DataTypeA15(String dateStr, String format) {
		super(dateStr, format);
	}

	public DataTypeA15(byte[] array) {
		super();
		setArray(array, 0);
	}

	public void setArray(byte[] array) {
		setArray(array, 0);
	}

	public final void setArray(byte[] array, int beginPosition) {
		setArray(array, beginPosition, "MIHHDDMMYY");
	}

	public byte[] getArray() {
		return getArray("MIHHDDMMYY");
	}

	@Override
	public String toString() {
		if (this.isNull)
			return "";
		return this.getDate().toString();
	}
}
