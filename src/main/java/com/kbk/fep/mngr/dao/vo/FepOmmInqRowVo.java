package com.kbk.fep.mngr.dao.vo;

public class FepOmmInqRowVo implements Cloneable {

	private int depth;
	private String dataType;
	private String attribute;
	private byte [] attributeExplain;
	private int length;
	private int decimal;
	private String doubleCharYn;
	private String align;
	private String padding;
	private String substd;

	public FepOmmInqRowVo(byte[] input) throws ArrayIndexOutOfBoundsException {

		if (input.length % 114 != 0) {
			throw new ArrayIndexOutOfBoundsException("적합한 길이가 아닙니다. [" + input.length + "]");
		}

		int pointer = 0;

		byte[] b_depth = new byte[1];
		byte[] b_dataType = new byte[10];
		byte[] b_attribute = new byte[30];
		byte[] b_attribute_s = new byte[30];
		byte[] b_length = new byte[5];
		byte[] b_decimal = new byte[1];
		byte[] b_doubleCharYn = new byte[1];
		byte[] b_align = new byte[5];
		byte[] b_padding = new byte[1];
		byte[] b_substd = new byte[30];

		System.arraycopy(input, pointer, b_depth, 0, b_depth.length);
		System.arraycopy(input, pointer += b_depth.length, b_dataType, 0, b_dataType.length);
		System.arraycopy(input, pointer += b_dataType.length, b_attribute, 0, b_attribute.length);
		System.arraycopy(input, pointer += b_attribute.length, b_attribute_s, 0, b_attribute_s.length);
		System.arraycopy(input, pointer += b_attribute_s.length, b_length, 0, b_length.length);
		System.arraycopy(input, pointer += b_length.length, b_decimal, 0, b_decimal.length);
		System.arraycopy(input, pointer += b_decimal.length, b_doubleCharYn, 0, b_doubleCharYn.length);
		System.arraycopy(input, pointer += b_doubleCharYn.length, b_align, 0, b_align.length);
		System.arraycopy(input, pointer += b_align.length, b_padding, 0, b_padding.length);
		System.arraycopy(input, pointer += b_padding.length, b_substd, 0, b_substd.length);

		this.depth = Integer.parseInt(new String(b_depth));
		this.dataType = new String(b_dataType);
		this.attribute = new String(b_attribute);
		this.attributeExplain = b_attribute_s;
		this.length = Integer.parseInt(new String(b_length));
		this.decimal = Integer.parseInt(new String(b_decimal));
		this.doubleCharYn = new String(b_doubleCharYn);
		this.align = new String(b_align);
		this.padding = new String(b_padding);
		this.substd = new String(b_substd);

	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public byte [] getAttributeExplain() {
		return attributeExplain;
	}

	public void setAttributeExplain(byte [] attributeExplain) {
		this.attributeExplain = attributeExplain;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getDecimal() {
		return decimal;
	}

	public void setDecimal(int decimal) {
		this.decimal = decimal;
	}

	public String getDoubleCharYn() {
		return doubleCharYn;
	}

	public void setDoubleCharYn(String doubleCharYn) {
		this.doubleCharYn = doubleCharYn;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getPadding() {
		return padding;
	}

	public void setPadding(String padding) {
		this.padding = padding;
	}

	public String getSubstd() {
		return substd;
	}

	public void setSubstd(String substd) {
		this.substd = substd;
	}

	@Override
	public String toString() {
		return "FepSimLayoutRowVo [depth=" + depth + ", dataType=" + dataType + ", attribute=" + attribute
				+ ", attributeExplain=" + attributeExplain + ", length=" + length + ", decimal=" + decimal
				+ ", doubleCharYn=" + doubleCharYn + ", align=" + align + ", padding=" + padding + ", substd=" + substd
				+ "]";
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}