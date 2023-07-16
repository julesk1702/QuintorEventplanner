package nl.han.oose.clipper.clipperapi.domain.event.application.dto;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class FeedbackId implements Serializable {

    private Long event_id;
    private Long user_id;

    public FeedbackId() {
    }

    public FeedbackId(Long event_id, Long user_id) {
        this.event_id = event_id;
        this.user_id = user_id;
    }

    public Long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Long event_id) {
        this.event_id = event_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
