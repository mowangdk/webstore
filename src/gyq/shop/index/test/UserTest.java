package gyq.shop.index.test;

import gyq.shop.user.bean.User;
import gyq.shop.user.service.UserService;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserTest {
	@Test
	public void save() {
		ApplicationContext cxt = new ClassPathXmlApplicationContext(
				"ApplicationContext.xml");
		UserService userService = cxt.getBean(UserService.class);
		User user = new User();
		user.setUsername("geyingqi");
		user.setPassword("123445");
		userService.save(user);
	}

	@Test
	public void fingByName() {
		ApplicationContext cxt = new ClassPathXmlApplicationContext(
				"ApplicationContext.xml");
		UserService userService = cxt.getBean(UserService.class);
		User user = userService.findByUsername("geyingqi");
		System.out.println(user);
	}
	@Test
	public void findByCode(){
		ApplicationContext cxt = new ClassPathXmlApplicationContext("ApplicationContext.xml");
		UserService userService= cxt.getBean(UserService.class);
		try {
			userService.active("6c3812e2b0d14f58a32c8cc73a1729efc74baadc35ad4886aa4b090823973da6");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
