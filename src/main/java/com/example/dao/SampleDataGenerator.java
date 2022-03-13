package com.example.dao;

import java.sql.Date;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.dao.entities.User;

@Startup
@Singleton
public class SampleDataGenerator {
	private static final Logger LOGGER = Logger.getLogger(SampleDataGenerator.class.getName());

	@Inject
	private UserDAO userDao;

	@PersistenceContext
	private EntityManager entityManager;

	@PostConstruct
	public void initialize() {
		LOGGER.log(Level.INFO, "start generating sample data...");

		// delete data
		int deleted = entityManager.createQuery("delete from users").executeUpdate();
		LOGGER.log(Level.INFO, "clear existing data, deleted purchase order: {0}", deleted);

		// insert initial data
		for (int i = 1; i < 6; i++) {
			this.userDao.saveUser(User.builder().firstName("etunimi" + i).lastName("sukunimi" + i)
					.birthDate(Date.valueOf(LocalDate.of(1980 + 1, i, i))).build());
		}

		this.userDao.findUsers()
				.forEach(purchaseOrder -> LOGGER.log(Level.INFO, "saved user: {0}", purchaseOrder));
	}
}
