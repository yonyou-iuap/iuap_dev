package com.toolkit.drools;

public abstract interface UserRule
{
  public abstract int getTaskCredit(int paramInt);

  public abstract double getExpressAmount(int paramInt1, int paramInt2, double paramDouble);

  public abstract int getUserRank(long paramLong);

  public abstract double getDiscountPrice(int paramInt, double paramDouble);
}