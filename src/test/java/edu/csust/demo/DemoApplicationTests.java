package edu.csust.demo;

import edu.csust.demo.identify.domain.model.RoleRepository;
import edu.csust.demo.identify.domain.model.UserRepository;
import edu.csust.demo.identify.domain.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired UserService userService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void serviceTests() {
	}

}
