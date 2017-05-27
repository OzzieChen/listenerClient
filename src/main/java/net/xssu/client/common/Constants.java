package net.xssu.client.common;

import net.xssu.client.util.PropertiesUtil;

import java.util.Properties;

/**
 * Created by bonult on 2017/5/6.
 *
 * 加载一些系统参数，比如节点ID
 */
public class Constants {
	private static String NODE_ID;
	private static String MAIN_SERVER_URL;

	public static String getClientId(){
		if(NODE_ID == null){
			Properties configProp = getConfigProperties();
			String clientId = configProp.getProperty("client-id");
			NODE_ID = clientId;
		}
		return NODE_ID;
	}

	public static String getMainServerUrl(){
		if(MAIN_SERVER_URL == null){
			Properties configProp = getConfigProperties();
			String server = configProp.getProperty("server-url");
			MAIN_SERVER_URL = server;
		}
		return MAIN_SERVER_URL;
	}

	private static Properties RedisProperties;
	private static Properties RabbitmqProperties;
	private static Properties ConfigProperties;

	public static Properties getRedisProperties(){
		if(RedisProperties == null)
			RedisProperties = PropertiesUtil.loadProps("properties/redis.properties");
		return RedisProperties;
	}

	public static Properties getConfigProperties(){
		if(ConfigProperties == null)
			ConfigProperties = PropertiesUtil.loadProps("properties/config.properties");
		return ConfigProperties;
	}

	public static Properties getRabbitmqProperties(){
		if(RabbitmqProperties == null)
			RabbitmqProperties = PropertiesUtil.loadProps("properties/rabbitmq.properties");
		return RabbitmqProperties;
	}

	public static void setRedisPropertiesNull(){
		RedisProperties = null;
	}

	public static void setConfigPropertiesNull(){
		ConfigProperties = null;
		MAIN_SERVER_URL = null;
		NODE_ID = null;
	}

	public static void setRabbitmqPropertiesNull(){
		RabbitmqProperties = null;
	}
}
