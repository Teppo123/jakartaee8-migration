package com.example.dto.transformers;

import com.example.dto.UserDTO;
import com.example.dao.entities.User;
import com.example.utils.CustomDateUtils;

public class UserDTOTransformer {

	public UserDTO transform(User entity) {
		return entity == null ? null
				: UserDTO.builder().id(entity.getId()).birthDate(CustomDateUtils.toLocalDate(entity.getBirthDate()))
						.firstName(entity.getFirstName()).lastName(entity.getLastName())
						.creationTime(CustomDateUtils.toLocalDateTime(entity.getCreatedAt())).build();
	}

}
