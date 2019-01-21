package com.java.zonov.novosoft.guestbook.repos;

import com.java.zonov.novosoft.guestbook.data.Msg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GBRepo extends JpaRepository<Msg, Long> {
}
