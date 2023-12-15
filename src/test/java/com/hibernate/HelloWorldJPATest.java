package com.hibernate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.Test;

public class HelloWorldJPATest {

	@Test
	public void storLoadMessage() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hibernateSpringJPA");

		try {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();

			Message message = new Message();
			message.setText("Hello World");

			em.persist(message);
			em.getTransaction().commit();

			em.getTransaction().begin();

			List<Message> messages = em.createQuery("select m from Message m").getResultList();

			messages.get(messages.size() - 1).setText("Hello World from JPA");

			em.getTransaction().commit();

			assertAll(() -> assertEquals(1, messages.size()),
					() -> assertEquals("Hello World from JPA", messages.get(0).getText()));
			
			em.close();

		} finally {
			emf.close();
		}
	}

}
