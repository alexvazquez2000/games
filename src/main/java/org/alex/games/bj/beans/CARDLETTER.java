package org.alex.games.bj.beans;

public enum CARDLETTER {
	A(1), V2(2), V3(3), V4(4), V5(5), V6(6), V7(7), V8(8), V9(9), V10(10), J(10), Q(10), K(10);

	private int value;

	CARDLETTER(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
