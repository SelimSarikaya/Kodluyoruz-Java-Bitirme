package com.ticket.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.ticket.model.Role;
import com.ticket.model.User;
import com.ticket.model.enums.UserType;
import com.ticket.repository.RoleRepository;
import com.ticket.repository.UserRepository;

@Component
@Profile("!test")
public class DataLoad implements ApplicationRunner {

	@Autowired
	UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		User adminUser = new User();
		adminUser.setName("Admin");
		adminUser.setEmail("admin@gmail.com");
		adminUser.setPassword(PasswordUtilization.preparePasswordHash("1234"));
		adminUser.setType(UserType.INDIVIDUAL);
		userRepository.save(adminUser);

		Role adminRole = new Role();
		adminRole.setRoleName("ROLE_ADMIN");

		roleRepository.save(adminRole);

		List<Role> adminRoles = new ArrayList<>();
		adminRoles.add(adminRole);
		adminUser.setRoles(adminRoles);
		userRepository.save(adminUser);
		userRepository.flush();

	}

}
