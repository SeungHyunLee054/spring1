package kr.mjc.lsh.spring.day1.class03.componentscan;

import kr.mjc.lsh.spring.day1.class02.TV;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * p.43 LgTV.java
 */
@Component
@Slf4j
public class LgTV implements TV {

    /**
     * Default constructor
     */
    public LgTV() {
        log.debug("lgTV 인스턴스 생성됨.");
    }

    @Override
    public void powerOn() {
        log.debug("lgTV power on.");
    }

    @Override
    public void powerOff() {
        log.debug("lgTV power off.");
    }

    @Override
    public void volumeUp() {
        log.debug("lgTV volume up.");
    }

    @Override
    public void volumeDown() {
        log.debug("lgTV volume down.");
    }
}
