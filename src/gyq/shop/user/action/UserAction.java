package gyq.shop.user.action;

import gyq.shop.user.bean.User;
import gyq.shop.user.service.UserService;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;



public class UserAction extends ActionSupport implements ModelDriven<User> {
		private User user = new User();
		
		//手动接受验证码
		private String checkcode;
		
		public String getCheckcode() {
			return checkcode;
		}
		public void setCheckcode(String checkcode) {
			this.checkcode = checkcode;
		}
		// 注入UserService
		private UserService userService;
		
		public UserService getUserService() {
			return userService;
		}
		@Resource(name="UserServiceImpl")
		public void setUserService(UserService userService) {
			this.userService = userService;
		}

		//模型驱动使用的对象
		public User getModel() {
			// TODO Auto-generated method stub
			return user;
		}
		
		public String regist() throws Exception {
			//在进行注册之前先进行一些后台校验
				//在action所在的包下创建一个校验的xml
					//创建一个Action类名-validation.xml（针对所有方法进行校验,只要对regist方法进行校验 UserAction-regist）
			userService.save(user);
			ServletActionContext.getRequest().setAttribute("msg", "注册成功,请到邮箱内激活");
			return SUCCESS;
		}
		
		public String findByName(){
			// 调用service进行查询
			System.out.println(user);
			User existUser = userService.findByUsername(user.getUsername());
			//获取response对象，向页面输出
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=UTF-8");
			if(existUser != null){
				//查询到用户:用户名已经存在
				try {
					response.getWriter().print("<font color='red'>用户名已经存在</font>");
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}else{
				//没查询到用户：用户名可以使用
				try {
					response.getWriter().println("<font color='green'>用户名可以使用</font>");
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			
			return NONE;
		}
		
		public String active(){
			String code = ServletActionContext.getRequest().getParameter("code");
			try{
			userService.active(code);
			}catch(Exception e){
				ServletActionContext.getRequest().setAttribute("msg", e.getMessage());
			}
			return SUCCESS;
		}
		
		public String login(){
			String checkcode1 = (String) ServletActionContext.getRequest().getSession().getAttribute("checkcode");
			if(!checkcode.equalsIgnoreCase(checkcode1)){
				this.addActionError("验证码输入错误");
				return "checkcodefailure";
			}
			User existUser = userService.login(user);
			if(existUser == null){
				this.addActionError("登陆失败,用户名或者密码错误");
				return LOGIN;
			}else{
				//登陆成功
				//将用户的信息存入到session中
				ServletActionContext.getRequest().getSession().setAttribute("user", existUser);;
				return "success";
			}
			
		}
		public String logout(){
			//销毁Session
			ServletActionContext.getRequest().getSession().invalidate();
			return SUCCESS;
		}
}
