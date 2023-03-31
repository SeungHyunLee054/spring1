package kr.mjc.lsh.spring.day1.class03.componentscan;

import kr.mjc.lsh.spring.day1.class02.LgTV;
import kr.mjc.lsh.spring.day1.class02.SamsungTV;
import kr.mjc.lsh.spring.day1.class02.TV;
import kr.mjc.lsh.spring.day1.class03.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * p.53 TVUser.java 수정
 */
public class TVUser {
    public static void main(String[] args) {
        // AppConfig 클래스에서 구성한대로 빈을 구성한다.
        // 빈들을 보관한 영역을 applicationContext라 한다.
        ApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        // context에서 SamsungTV 타입의 빈을 꺼낸다.
        TV samsungTV = context.getBean(SamsungTV.class);
        samsungTV.powerOn();
        samsungTV.powerOff();

        // context에서 lgTV 이름의 빈을 꺼낸다.
        TV lgTV = (LgTV) context.getBean("lgTV");
        lgTV.powerOn();
        lgTV.powerOff();
    }
}
