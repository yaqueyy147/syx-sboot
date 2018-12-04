package com.syx.sboot.common.utils.extend.lang.money;

import java.math.BigDecimal;
import java.util.Currency;

public class UnmodifiableMoney extends Money
{
  private static final long serialVersionUID = 6302446101953482760L;

  public UnmodifiableMoney()
  {
  }

  public UnmodifiableMoney(BigDecimal amount, Currency currency, int roundingMode)
  {
    super(amount, currency, roundingMode);
  }

  public UnmodifiableMoney(BigDecimal amount, Currency currency) {
    super(amount, currency);
  }

  public UnmodifiableMoney(BigDecimal amount, int roundingMode) {
    super(amount, roundingMode);
  }

  public UnmodifiableMoney(BigDecimal amount) {
    super(amount);
  }

  public UnmodifiableMoney(double amount, Currency currency) {
    super(amount, currency);
  }

  public UnmodifiableMoney(double amount) {
    super(amount);
  }

  public UnmodifiableMoney(long yuan, int cent, Currency currency) {
    super(yuan, cent, currency);
  }

  public UnmodifiableMoney(long yuan, int cent) {
    super(yuan, cent);
  }

  public UnmodifiableMoney(long yuan) {
    super(yuan);
  }

  public UnmodifiableMoney(String amount, Currency currency, int roundingMode) {
    super(amount, currency, roundingMode);
  }

  public UnmodifiableMoney(String amount, Currency currency) {
    super(amount, currency);
  }

  public UnmodifiableMoney(String amount) {
    super(amount);
  }

  public void setAmount(BigDecimal amount)
  {
    super.setAmount(amount);
  }

  public Money add(Money other)
  {
    throw new UnsupportedOperationException();
  }

  public Money addTo(Money other)
  {
    throw new UnsupportedOperationException();
  }

  public Money subtract(Money other)
  {
    throw new UnsupportedOperationException();
  }

  public Money subtractFrom(Money other)
  {
    throw new UnsupportedOperationException();
  }

  public Money multiply(long val)
  {
    throw new UnsupportedOperationException();
  }

  public Money multiplyBy(long val)
  {
    throw new UnsupportedOperationException();
  }

  public Money multiply(double val)
  {
    throw new UnsupportedOperationException();
  }

  public Money multiplyBy(double val)
  {
    throw new UnsupportedOperationException();
  }

  public Money multiply(BigDecimal val)
  {
    throw new UnsupportedOperationException();
  }

  public Money multiplyBy(BigDecimal val)
  {
    throw new UnsupportedOperationException();
  }

  public Money multiply(BigDecimal val, int roundingMode)
  {
    throw new UnsupportedOperationException();
  }

  public Money multiplyBy(BigDecimal val, int roundingMode)
  {
    throw new UnsupportedOperationException();
  }

  public Money divide(double val)
  {
    throw new UnsupportedOperationException();
  }

  public Money divideBy(double val)
  {
    throw new UnsupportedOperationException();
  }

  public Money divide(BigDecimal val)
  {
    throw new UnsupportedOperationException();
  }

  public Money divide(BigDecimal val, int roundingMode)
  {
    throw new UnsupportedOperationException();
  }

  public Money divideBy(BigDecimal val)
  {
    throw new UnsupportedOperationException();
  }

  public Money divideBy(BigDecimal val, int roundingMode)
  {
    throw new UnsupportedOperationException();
  }

  public void setCent(long l)
  {
    throw new UnsupportedOperationException();
  }
}