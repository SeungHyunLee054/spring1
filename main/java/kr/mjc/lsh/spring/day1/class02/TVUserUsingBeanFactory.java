package kr.mjc.lsh.spring.day1.class02;

import lombok.extern.slf4j.Slf4j;

/**
 * p.45 TVUser.java 수정
 */
@Slf4j
public class TVUserUsingBeanFactory {
    public static void main(String[] args) {
        // 빈들을 미리 생성해서 factory에 보관한다.
        BeanFactory factory = new BeanFactory();

        // factory에서 samsungTV 빈을 꺼낸다.
        TV samsungTV = (TV) factory.getBean("samsung");
        samsungTV.powerOn();
        samsungTV.powerOff();

        // factory에서 lgTV 빈을 꺼낸다.
        TV lgTV = (TV) factory.getBean("lg");
        lgTV.powerOn();
        lgTV.powerOff();
    }
}
