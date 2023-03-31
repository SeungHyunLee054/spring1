package kr.mjc.lsh.spring.day1.class02;

/**
 * p.44 TVUser.java
 */
public class TVUser {
    public static void main(String[] args) {
        TV tv = new SamsungTV();
        tv.powerOn();
        tv.powerOff();
        tv.volumeUp();
        tv.volumeDown();
    }
}