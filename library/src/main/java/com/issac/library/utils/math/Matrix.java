package com.issac.library.utils.math;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

/**
 * Created by Randolph on 2017/7/17.
 */

public class Matrix {
    private final static int AA = 50;
    private final static double n = 3.25;

    private double d1,d2,d3;
    private double x1,x2,x3,y1,y2,y3;

    private double[][] A;
    private double[] B;

    public Matrix(double x1, double y1, double x2, double y2, double x3, double y3){
        this.x1 = x1;this.x2 = x2;this.x3 = x3;
        this.y1 = y1;this.y2 = y2;this.y3 = y3;
        A = new double[2][2];
        A[0][0] = 2*(x1-x3);
        A[0][1] = 2*(y1-y3);
        A[1][0] = 2*(x2-x3);
        A[1][1] = 2*(y2-y3);
    }

    public void setdis(int[] a){
        d1 = pow(10, (abs(a[0])-AA)/(10*n));
        d2 = pow(10, (abs(a[1])-AA)/(10*n));
        d3 = pow(10, (abs(a[2])-AA)/(10*n));
        B = new double[2];
        B[0] = x1*x1-x3*x3+y1*y1-y3*y3+d3*d3-d1*d1;
        B[1] = x2*x2-x3*x3+y2*y2-y3*y3+d3*d3-d2*d2;
    }

    protected double[][]  Transport(double[][] X){
        double tmp = X[0][1];
        X[0][1] = X[1][0];
        X[1][0] = tmp;
        /*
        for(int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                Log.i("TransportMatrix", X[i][j]+" ");
            }
            Log.i("TransportMatrix", "\n");
        }
        Log.i("TransportMatrix", "\n");
        */
        return X;
    }

    protected double[][] mul(double[][] X, double[][] Y){
        double[][] Z = {{0,0},{0,0}};
        Z[0][0] = X[0][0] * Y[0][0] + X[0][1] * Y[1][0];
        Z[0][1] = X[0][0] * Y[0][1] + X[0][1] * Y[1][1];
        Z[1][0] = X[1][0] * Y[0][0] + X[1][1] * Y[1][0];
        Z[1][1] = X[1][0] * Y[0][1] + X[1][1] * Y[1][1];
        return Z;
    }

    protected double[] mul(double[][] X, double[] Y){
        double[] Z = {0,0};
        Z[0] = X[0][0] * Y[0] + X[0][1] * Y[1];
        Z[1] = X[1][0] * Y[0] + X[1][1] * Y[1];
        return Z;
    }

    protected double[][] Inverse(double[][] X){
        double[][] Z = {{0,0},{0,0}};
        double tmp = X[0][0] * X[1][1] - X[0][1] * X[1][0];
        Z[0][0] = X[1][1] / tmp;
        Z[0][1] = -X[1][0] / tmp;
        Z[1][0] = -X[0][1] / tmp;
        Z[1][1] = X[0][0] / tmp;
        return Z;
    }

    public double[] cal(){
        return mul(mul(Inverse(mul(Transport(A),A)),Transport(A)),B);
    }
}
