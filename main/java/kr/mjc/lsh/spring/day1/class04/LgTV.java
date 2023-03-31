package kr.mjc.lsh.spring.day1.class04;

import kr.mjc.lsh.spring.day1.class02.TV;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LgTV implements TV {

    private Speaker speaker;

    /**
     * Default constructor
     */
    public LgTV() {
        log.debug("lgTV 인스턴스 생성됨.");
    }

    /**
     * speaker를 주입하는 setter
     */
    public void setSpeaker(Speaker speaker) {
        this.speaker = speaker;
        log.debug("setter로 speaker 주입함.");
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
        speaker.volumeUp();
    }

    @Override
    public void volumeDown() {
        speaker.volumeDown();
    }
}
