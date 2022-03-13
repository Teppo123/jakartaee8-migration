package com.example;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.dto.UserDTO;

public abstract class AbstractIntegrationTestBase {

	public UserDTO getMockUser(int index) {
		return UserDTO.builder().firstName("etunimi" + index).lastName("sukunimi" + index).birthDate(LocalDate.now())
				.creationTime(LocalDateTime.now().minusDays(index)).build();
	}
	
}
