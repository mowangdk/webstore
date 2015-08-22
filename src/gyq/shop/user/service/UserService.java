package gyq.shop.user.service;

import gyq.shop.user.bean.User;

//用户模块 业务逻辑层
public interface UserService {
	public void save(User user); 
	public User findByUsername(String username);
	public void sendMail(User user);
	void active(String code)throws Exception;
	public User login(User user);
	
	
}
