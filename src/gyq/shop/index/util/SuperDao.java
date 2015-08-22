package gyq.shop.index.util;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

/*
 * 继承hibernateDaoSupport的工具类
 * 
 * */
@Component
public class SuperDao extends HibernateDaoSupport{
	@Resource(name="mySessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory){
			super.setSessionFactory(sessionFactory);
	}
}
