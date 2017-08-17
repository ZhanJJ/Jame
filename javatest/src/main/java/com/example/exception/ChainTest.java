package com.example.exception;

/**
 * Created by James on 2016/10/28.
 */

public class ChainTest {
    public static void main(String[] msg){
        ChainTest chainTest = new ChainTest();
        chainTest.test2();
    }

    public void test1() throws IDrunkException {
        throw new IDrunkException("喝车别开酒！");
    }

    public void test2(){
        try {
            test1();
        } catch (IDrunkException e) {
            RuntimeException newExp = new RuntimeException("司机一滴酒，亲人");
            newExp.initCause(e);
            throw newExp;
        }finally {
            System.out.println("天堂见上帝");
        }
    }
}
