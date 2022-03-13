package com.example.dao.entities.transformers;

import java.sql.Date;

import com.example.dto.UserDTO;
import com.example.dao.entities.User;

public class UserTransformer {

	public User transform(UserDTO to) {
		return to == null ? null
				: User.builder().firstName(to.getFirstName()).lastName(to.getLastName())
						.birthDate(Date.valueOf(to.getBirthDate())).build();
	}

}
