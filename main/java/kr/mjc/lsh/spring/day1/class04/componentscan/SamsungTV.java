package kr.mjc.lsh.spring.day1.class04.componentscan;

import kr.mjc.lsh.spring.day1.class02.TV;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SamsungTV implements TV {

    private Speaker speaker;

    /**
     * speaker를 주입하는 constructor
     */
    @Autowired // 컨스트럭터가 1개만 있을 경우 생략 가능
    public SamsungTV(@Qualifier("sonySpeaker") Speaker speaker) {
        this.speaker = speaker;
        log.debug("samsungTV 인스턴스 생성됨. speaker 주입함.");
    }

    @Override
    public void powerOn() {
        log.debug("samsungTV power on.");
    }

    @Override
    public void powerOff() {
        log.debug("samsungTV power off.");
    }

    @Override
    public void volumeUp() {
        speaker.volumeUp();
    }

    @Override
    public void volumeDown() {
        speaker.volumeDown();
    }
}