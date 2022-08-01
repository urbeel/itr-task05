package by.urbel.task05.repository;

import by.urbel.task05.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByRecipientOrderByTimeDesc(String recipient);

    @Query("select distinct recipient from Message")
    List<String> findAllRecipients();
}
