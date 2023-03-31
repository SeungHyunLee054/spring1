package kr.mjc.lsh.spring.day1.class04.componentscan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SonySpeaker implements Speaker {
    public SonySpeaker() {
        log.debug("sonySpeaker 인스턴스 생성됨.");
    }

    @Override
    public void volumeUp() {
        log.debug("sonySpeaker volume up.");
    }

    @Override
    public void volumeDown() {
        log.debug("sonySpeaker volume down");
    }
}
