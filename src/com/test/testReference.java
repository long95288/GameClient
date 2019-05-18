package com.test;

// 引用调用
class A {
    private int a = 99;
    A(){
    }
   public void add(int i){
        this.a += i;
    }
    public void print(){
        System.out.println(a);
    }
}

class B {
    B(){}
    public void addA(A a){
        a.add(1); // 引用a
    }
    public void addInt(int i2, int j){
        i2 = i2 + j;
    }
}
public class testReference {
    public static void main(String[] argv){
        A a = new  A();
        B b = new B();
        b.addA(a);
        a.print(); // 输出100
        A a1 = a; //
        b.addA(a1);
        a1.print(); // 预计是101

        int i=1;
        b.addInt(i,19);
        System.out.println(i); // 结果为1
    }
}
