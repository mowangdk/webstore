package gyq.shop.user.dao;

import gyq.shop.user.bean.User;

//用户模块持久层
public interface UserDao {
	public void add(User user);
	public User findByUsername(String username);
	public User findByCode(String code);
	public void updateState(Integer uid, int i);
	public User login(User user);
	
}
