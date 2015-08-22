package gyq.shop.user.service.impl;

import gyq.shop.index.util.UUIDutil;
import gyq.shop.user.bean.User;
import gyq.shop.user.dao.UserDao;
import gyq.shop.user.service.UserService;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.Session;

import org.springframework.stereotype.Service;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {
	private UserDao userdao;

	public UserDao getUserdao() {
		return userdao;
	}

	@Resource(name = "UserDaoImpl")
	public void setUserdao(UserDao userdao) {
		this.userdao = userdao;
	}

	public User findByUsername(String username) {
		User existuser = userdao.findByUsername(username);

		return existuser;

	}

	public void save(User user) {
		user.setState(0);// 代表用户没有激活
		String code = UUIDutil.getUUID() + UUIDutil.getUUID();
		user.setCode(code);
		userdao.add(user);
		sendMail(user);
	}

	@Override
	public void sendMail(User user) {
		Properties props = new Properties();
		try {
			props.load(this.getClass().getClassLoader()
					.getResourceAsStream("email_template.properties"));
			String host = props.getProperty("host");
			String uname = props.getProperty("uname");
			String pwd = props.getProperty("pwd");
			String from = props.getProperty("from");
			String to = user.getEmail();
			String subject = props.getProperty("subject");
			String content = props.getProperty("content");
			/*
			 * content = MessageFormat.format(content,
			 * user.getCode());//替换{0}占位符 Session session =
			 * MailUtils.createSession(host, uname, pwd); Mail mail = new
			 * Mail(from,to,subject,content); MailUtils.send(session, mail);
			 */
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void active(String code) throws Exception {
		User user = userdao.findByCode(code);
		if (user == null)
			throw new Exception("该用户不存在,激活失败");

		if (user.getState() == 1)
			throw new Exception("该用户已经激活,无需重新激活");

		userdao.updateState(user.getUid(), 1);
	}

	@Override
	public User login(User user) {
		User reuser = userdao.login(user);
		return reuser;
	}

}
