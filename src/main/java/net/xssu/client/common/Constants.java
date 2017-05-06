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


	static {// TODO 需要从配置文件中加载
		Properties configProp = PropertiesUtil.loadProps("properties/config.properties");
		String clientId = configProp.getProperty("client-id");
		NODE_ID=clientId;
		String server = configProp.getProperty("server-url");
		MAIN_SERVER_URL = server;
	}
}
