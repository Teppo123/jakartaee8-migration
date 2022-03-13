package com.example.dao;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

import com.example.dao.entities.User;

@Stateless
public class UserDAO {

	private static final String NOT_DEACTIVATED_SQL = "deactivated = false";

	private static final class SqlQueries {

		private SqlQueries() {
			// empty default constructor
		}

		private static final String FIND_USERS = "SELECT u FROM users u WHERE ";
		private static final String FIND_USERS_SQL = FIND_USERS + NOT_DEACTIVATED_SQL;
		private static final String FIND_USER_BY_ID = FIND_USERS + NOT_DEACTIVATED_SQL + " AND id = ";
		private static final String FIND_USERS_BY_DATE = FIND_USERS + NOT_DEACTIVATED_SQL + " AND birthDate > ?1";

	}

	private static final String getSqlQueryByName(String firstName, String lastName) {
		String sql = SqlQueries.FIND_USERS_SQL;
		if (StringUtils.isNotBlank(firstName)) {
			sql += " and firstName = '" + lastName + "'";
		}
		if (StringUtils.isNotBlank(lastName)) {
			sql += " and lastName = '" + lastName + "'";
		}
		return sql;
	}

	@PersistenceContext
	private EntityManager entityManager;

	public List<User> findUsers() {
		return this.entityManager.createQuery(SqlQueries.FIND_USERS_SQL, User.class).getResultList();
	}

	public User findUserById(long id) {
		return this.entityManager.createQuery(SqlQueries.FIND_USER_BY_ID + "'" + id + "'", User.class)
				.getSingleResult();
	}

	public List<User> findUsersByName(String firstName, String lastName) {
		return this.entityManager.createQuery(getSqlQueryByName(firstName, lastName), User.class).getResultList();
	}

	public List<User> findUsersBornBefore(@NotNull LocalDate localDate) {
		Objects.requireNonNull(localDate);
		TypedQuery<User> query = this.entityManager.createQuery(SqlQueries.FIND_USERS_BY_DATE,
//				+ DateTimeFormatter.ofPattern("YYYY-MM-dd").format(localDate), 
				User.class);
		query.setParameter(1, Date.valueOf(localDate));
		return query.getResultList();
	}

	public User saveUser(User user) {
		this.entityManager.persist(user);
		this.entityManager.flush();
		return user;
	}

	@Transactional
	public void deleteUserById(long userId) {
		Optional.ofNullable(this.entityManager.find(User.class, userId)).ifPresent(User::deactivate);
	}

}
