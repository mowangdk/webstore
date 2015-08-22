package gyq.shop.index.util;

import java.util.UUID;

public class UUIDutil {
	//获得一个随机的字符串,用于生成验证码
	public static String getUUID(){
		
		return UUID.randomUUID().toString().replace("-", "");
	}
}
