package kr.mjc.lsh.spring.day1.class04.componentscan;

import kr.mjc.lsh.spring.day1.class02.TV;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class TVUser {
    public static void main(String[] args) {
        try {
            ApplicationContext context =
                    new AnnotationConfigApplicationContext(AppConfig.class);

            TV samsungTV = context.getBean(SamsungTV.class);
            samsungTV.volumeUp();
            samsungTV.volumeDown();

            TV lgTV = context.getBean(LgTV.class);
            lgTV.volumeUp();
            lgTV.volumeDown();
        } catch (Exception e) {
            log.error(e.toString());
        }
    }
}
