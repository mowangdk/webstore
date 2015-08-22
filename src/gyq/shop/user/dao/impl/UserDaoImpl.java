package gyq.shop.user.dao.impl;


import gyq.shop.index.util.SuperDao;
import gyq.shop.user.bean.User;
import gyq.shop.user.dao.UserDao;

import java.util.List;

import org.springframework.stereotype.Component;
@Component("UserDaoImpl")
public class UserDaoImpl extends SuperDao implements UserDao {
	public void add(User user){
		this.getHibernateTemplate().save(user);
	}
	public User findByUsername(String username){
		String hql = "from User where username=?";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, username);
		if(list != null && list.size()>0){
			return list.get(0);
		
		}
		return null;
	}
	@Override
	public User findByCode(String code) {
		String hql = "from User where code=?";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, code);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	@Override
	public void updateState(Integer uid, int i) {
		String hql = "from User where uid=?";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, uid);
		if(list!=null && list.size()>0){
			User user = list.get(0);
			user.setState(i);
			this.getHibernateTemplate().update(user);
		}
	}
	@Override
	public User login(User user) {
		String hql = "from User where username=? and password=? and state=1";
	   List<User> list= (List<User>) this.getHibernateTemplate().find(hql, user.getUsername(),user.getPassword());
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
}
