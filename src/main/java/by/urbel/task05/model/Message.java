package by.urbel.task05.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Message {
    @Id
    @GeneratedValue()
    private Long id;
    @Column(nullable = false)
    private String sender;
    @Column(nullable = false)
    private String recipient;
    @Column(nullable = false)
    private LocalDateTime time;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String body;

    @PrePersist
    public void updateTime() {
        time = LocalDateTime.now();
    }
}
