package com.docencia.pgv.cliente;

import java.util.concurrent.atomic.AtomicInteger;

public final class GradeTracker {

  private static final AtomicInteger publicPoints = new AtomicInteger(0);   // max 4
  private static final AtomicInteger securedPoints = new AtomicInteger(0);  // max 6

  private GradeTracker() {}

  public static void addPublic(int pts) {
    publicPoints.addAndGet(pts);
  }

  public static void addSecured(int pts) {
    securedPoints.addAndGet(pts);
  }

  public static int getPublic() {
    return Math.min(publicPoints.get(), 4);
  }

  public static int getSecured() {
    return Math.min(securedPoints.get(), 6);
  }

  public static double total() {
    return getPublic() + getSecured();
  }

  public static void printFinalGrade() {
    System.out.println("--------------------------------------------------");
    System.out.printf("NOTA FINAL: %.1f/10 (public: %d/4, secured: %d/6)%n",
        total(), getPublic(), getSecured());
    System.out.println("--------------------------------------------------");
  }
}
