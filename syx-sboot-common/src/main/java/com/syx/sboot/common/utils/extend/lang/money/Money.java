package com.syx.sboot.common.utils.extend.lang.money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

public class Money implements Serializable, Comparable<Object> {
	private static final long serialVersionUID = 6009335074727417445L;
	public static final String DEFAULT_CURRENCY_CODE = "CNY";
	public static final int DEFAULT_ROUNDING_MODE = 6;
	private static final int[] centFactors = { 1, 10, 100, 1000 };
	private long cent;
	private Currency currency;

	public Money() {
		this(0L);
	}

	public Money(long yuan) {
		this(yuan, 0, Currency.getInstance("CNY"));
	}

	public Money(long yuan, int cent) {
		this(yuan, cent, Currency.getInstance("CNY"));
	}

	public Money(long yuan, int cent, Currency currency) {
		this.currency = currency;

		this.cent = (yuan * getCentFactor() + cent % getCentFactor());
	}

	public Money(String amount) {
		this(amount == null ? "" : amount.replace(",", ""), Currency.getInstance("CNY"));
	}

	public Money(String amount, Currency currency) {
		this(new BigDecimal(amount == null ? "" : amount), currency);
	}

	public Money(String amount, Currency currency, int roundingMode) {
		this(new BigDecimal(amount == null ? "" : amount), currency, roundingMode);
	}

	public Money(double amount) {
		this(amount, Currency.getInstance("CNY"));
	}

	public Money(double amount, Currency currency) {
		this.currency = currency;
		this.cent = Math.round(amount * getCentFactor());
	}

	public Money(BigDecimal amount) {
		this(amount, Currency.getInstance("CNY"));
	}

	public Money(BigDecimal amount, int roundingMode) {
		this(amount, Currency.getInstance("CNY"), roundingMode);
	}

	public Money(BigDecimal amount, Currency currency) {
		this(amount, currency, 6);
	}

	public Money(BigDecimal amount, Currency currency, int roundingMode) {
		this.currency = currency;
		this.cent = rounding(amount.movePointRight(currency.getDefaultFractionDigits()), roundingMode);
	}

	public static Money zero() {
		return new Money();
	}

	public BigDecimal getAmount() {
		return BigDecimal.valueOf(this.cent, this.currency.getDefaultFractionDigits());
	}

	public void setAmount(BigDecimal amount) {
		if (amount != null)
			this.cent = rounding(amount.movePointRight(2), 6);
	}

	public static Money cent(long cent) {
		Money m = new Money();
		m.setCent(cent);
		return m;
	}

	public static Money yuan(long yuan) {
		return new Money(yuan);
	}

	public static Money amout(String amout) {
		return new Money(amout);
	}

	public long getCent() {
		return this.cent;
	}

	public Currency getCurrency() {
		return this.currency;
	}

	public int getCentFactor() {
		return centFactors[this.currency.getDefaultFractionDigits()];
	}

	public boolean equals(Object other) {
		return ((other instanceof Money)) && (equals((Money) other));
	}

	public boolean equals(Money other) {
		return (this.currency.equals(other.currency)) && (this.cent == other.cent);
	}

	public int hashCode() {
		return (int) (this.cent ^ this.cent >>> 32);
	}

	public int compareTo(Object other) {
		return compareTo((Money) other);
	}

	public int compareTo(Money other) {
		assertSameCurrencyAs(other);

		if (this.cent < other.cent)
			return -1;
		if (this.cent == other.cent) {
			return 0;
		}
		return 1;
	}

	public boolean greaterThan(Money other) {
		return compareTo(other) > 0;
	}

	public Money add(Money other) {
		assertSameCurrencyAs(other);

		return newMoneyWithSameCurrency(this.cent + other.cent);
	}

	public Money addTo(Money other) {
		assertSameCurrencyAs(other);

		this.cent += other.cent;

		return this;
	}

	public Money subtract(Money other) {
		assertSameCurrencyAs(other);

		return newMoneyWithSameCurrency(this.cent - other.cent);
	}

	public Money subtractFrom(Money other) {
		assertSameCurrencyAs(other);

		this.cent -= other.cent;

		return this;
	}

	public Money multiply(long val) {
		return newMoneyWithSameCurrency(this.cent * val);
	}

	public Money multiplyBy(long val) {
		this.cent *= val;

		return this;
	}

	public Money multiply(double val) {
		return newMoneyWithSameCurrency(Math.round(this.cent * val));
	}

	public Money multiplyBy(double val) {
		this.cent = Math.round(this.cent * val);

		return this;
	}

	public Money multiply(BigDecimal val) {
		return multiply(val, 6);
	}

	public Money multiplyBy(BigDecimal val) {
		return multiplyBy(val, 6);
	}

	public Money multiply(BigDecimal val, int roundingMode) {
		BigDecimal newCent = BigDecimal.valueOf(this.cent).multiply(val);

		return newMoneyWithSameCurrency(rounding(newCent, roundingMode));
	}

	public Money multiplyBy(BigDecimal val, int roundingMode) {
		BigDecimal newCent = BigDecimal.valueOf(this.cent).multiply(val);

		this.cent = rounding(newCent, roundingMode);

		return this;
	}

	public Money divide(double val) {
		return newMoneyWithSameCurrency(Math.round(this.cent / val));
	}

	public Money divideBy(double val) {
		this.cent = Math.round(this.cent / val);

		return this;
	}

	public Money divide(BigDecimal val) {
		return divide(val, 6);
	}

	public Money divide(BigDecimal val, int roundingMode) {
		BigDecimal newCent = BigDecimal.valueOf(this.cent).divide(val, roundingMode);

		return newMoneyWithSameCurrency(newCent.longValue());
	}

	public Money divideBy(BigDecimal val) {
		return divideBy(val, 6);
	}

	public Money divideBy(BigDecimal val, int roundingMode) {
		BigDecimal newCent = BigDecimal.valueOf(this.cent).divide(val, roundingMode);

		this.cent = newCent.longValue();

		return this;
	}

	public Money[] allocate(int targets) {
		Money[] results = new Money[targets];

		Money lowResult = newMoneyWithSameCurrency(this.cent / targets);
		Money highResult = newMoneyWithSameCurrency(lowResult.cent + 1L);

		int remainder = (int) this.cent % targets;

		for (int i = 0; i < remainder; i++) {
			results[i] = highResult;
		}

		for (int i = remainder; i < targets; i++) {
			results[i] = lowResult;
		}

		return results;
	}

	public Money[] allocate(long[] ratios) {
		Money[] results = new Money[ratios.length];

		long total = 0L;

		for (int i = 0; i < ratios.length; i++) {
			total += ratios[i];
		}

		long remainder = this.cent;

		for (int i = 0; i < results.length; i++) {
			results[i] = newMoneyWithSameCurrency(this.cent * ratios[i] / total);
			remainder -= results[i].cent;
		}

		for (int i = 0; i < remainder; i++) {
			results[i].cent += 1L;
		}

		return results;
	}

	public String toString() {
		return getAmount().toString();
	}

	protected void assertSameCurrencyAs(Money other) {
		if (!this.currency.equals(other.currency))
			throw new IllegalArgumentException("Money math currency mismatch.");
	}

	protected long rounding(BigDecimal val, int roundingMode) {
		return val.setScale(0, roundingMode).longValue();
	}

	protected Money newMoneyWithSameCurrency(long cent) {
		Money money = new Money(0.0D, this.currency);

		money.cent = cent;

		return money;
	}

	public String dump() {
		String lineSeparator = System.getProperty("line.separator");

		StringBuffer sb = new StringBuffer();

		sb.append("cent = ").append(this.cent).append(lineSeparator);
		sb.append("currency = ").append(this.currency);

		return sb.toString();
	}

	public void setCent(long l) {
		this.cent = l;
	}

	public String toCNString() {
		return MoneyUtil.getCHSNumber(this);
	}

	@Deprecated
	public String getStandardString() {
		return MoneyUtil.format(this);
	}

	public String toStandardString() {
		return MoneyUtil.format(this);
	}
}