package net.xssu.client.common;

import net.xssu.client.util.PropertiesUtil;

import java.util.Properties;

/**
 * Created by bonult on 2017/5/6.
 *
 * 加载一些系统参数，比如节点ID
 */
public class Constants {
	public static final String NODE_ID;
	public static final String MAIN_SERVER_URL;


	static {
		Properties configProp = getConfigProperties();
		String clientId = configProp.getProperty("client-id");
		NODE_ID=clientId;
		String server = configProp.getProperty("server-url");
		MAIN_SERVER_URL = server;
	}

	private static Properties RedisProperties;
	private static Properties RabbitmqProperties;
	private static Properties ConfigProperties;

	public static Properties getRedisProperties(){
		if(RedisProperties==null)
			RedisProperties=PropertiesUtil.loadProps("properties/redis.properties");
		return RedisProperties;
	}

	public static Properties getConfigProperties(){
		if(ConfigProperties==null)
			ConfigProperties=PropertiesUtil.loadProps("properties/config.properties");
		return ConfigProperties;
	}

	public static Properties getRabbitmqProperties(){
		if(RabbitmqProperties==null)
			RabbitmqProperties=PropertiesUtil.loadProps("properties/rabbitmq.properties");
		return RabbitmqProperties;
	}

	public static void setRedisPropertiesNull(){
		RedisProperties=null;
	}

	public static void setConfigPropertiesNull(){
		ConfigProperties=null;
	}

	public static void setRabbitmqPropertiesNull(){
		RabbitmqProperties=null;
	}
}
