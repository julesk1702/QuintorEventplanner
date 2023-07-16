package nl.han.oose.clipper.clipperapi.domain.event.application.dto;

import jakarta.persistence.*;

@Entity
@Table(name = "event_feedback")
public class Feedback {

    @EmbeddedId
    private FeedbackId id;

    @Column(name = "feedback")
    private String feedback;

    public Feedback() {
    }

    public Feedback(FeedbackId feedbackId, String feedback) {
        this.feedback = feedback;
        this.id = feedbackId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public FeedbackId getId() {
        return id;
    }

    public void setId(FeedbackId id) {
        this.id = id;
    }
}
