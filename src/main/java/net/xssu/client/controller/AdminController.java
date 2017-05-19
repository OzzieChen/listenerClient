package net.xssu.client.controller;

import net.xssu.client.common.Constants;
import net.xssu.client.util.PropertiesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Controller
public class AdminController {

	@RequestMapping(value = "/editProp/{op}", method = RequestMethod.GET)
	public String opAutomataPages(HttpServletRequest request, Model model, @PathVariable("op") String op){
		if("redis".equals(op)){
			List<String[]> list = new ArrayList<>(2);
			list.add(new String[]{"hostname", Constants.getRedisProperties().getProperty("redis.hostname")});
			list.add(new String[]{"port", Constants.getRedisProperties().getProperty("redis.port")});
			model.addAttribute("myurl", "/editProp/redis");
			model.addAttribute("list", list);
			return "prop";
		}else if("rabbitmq".equals(op)){
			List<String[]> list = new ArrayList<>(4);
			list.add(new String[]{"host", Constants.getRabbitmqProperties().getProperty("rabbitmq.host")});
			list.add(new String[]{"port", Constants.getRabbitmqProperties().getProperty("rabbitmq.port")});
			list.add(new String[]{"username", Constants.getRabbitmqProperties().getProperty("rabbitmq.username")});
			list.add(new String[]{"password", Constants.getRabbitmqProperties().getProperty("rabbitmq.password")});
			model.addAttribute("myurl", "/editProp/rabbitmq");
			model.addAttribute("list", list);
			return "prop";
		}else if("config".equals(op)){
			List<String[]> list = new ArrayList<>(5);
			list.add(new String[]{"server-url", Constants.getConfigProperties().getProperty("server-url")});
			list.add(new String[]{"client-id", Constants.getConfigProperties().getProperty("client-id")});
			list.add(new String[]{"adapter-port", Constants.getConfigProperties().getProperty("adapter-port")});
			list.add(new String[]{"output-format", Constants.getConfigProperties().getProperty("output-format")});
			list.add(new String[]{"rate", Constants.getConfigProperties().getProperty("rate")});

			model.addAttribute("myurl", "/editProp/config");
			model.addAttribute("list", list);
			return "prop";
		}
		return null;
	}

	@RequestMapping(value = "/editProp/{op}", method = RequestMethod.POST)
	public String opAutomatas(HttpServletRequest request, Model model, @PathVariable("op") String op){
		String msg = "修改成功";
		String path = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
		if("redis".equals(op)){
			List<String[]> list = new ArrayList<>(2);
			Properties p = Constants.getRedisProperties();
			Constants.setRedisPropertiesNull();
			if(request.getParameter("hostname") == null){
				msg = "hostname不能为空";
			}else if(request.getParameter("port") == null){
				msg = "port不能为空";
			}else{
				p.setProperty("redis.hostname", request.getParameter("hostname"));
				p.setProperty("redis.port", request.getParameter("port"));
				try{
					PropertiesUtil.writeProperties(p, path+"properties/redis.properties");
				}catch(IOException e){
					msg = "修改失败";
				}
			}
			list.add(new String[]{"hostname", p.getProperty("redis.hostname")});
			list.add(new String[]{"port", p.getProperty("redis.port")});
			model.addAttribute("myurl", "/editProp/redis");
			model.addAttribute("list", list);
		}else if("rabbitmq".equals(op)){
			Properties p = Constants.getRabbitmqProperties();
			Constants.setRabbitmqPropertiesNull();
			if(request.getParameter("host") == null){
				msg = "host不能为空";
			}else if(request.getParameter("port") == null){
				msg = "port不能为空";
			}else if(request.getParameter("username") == null){
				msg = "username不能为空";
			}else if(request.getParameter("password") == null){
				msg = "password不能为空";
			}else{
				p.setProperty("rabbitmq.host", request.getParameter("host"));
				p.setProperty("rabbitmq.port", request.getParameter("port"));
				p.setProperty("rabbitmq.username", request.getParameter("username"));
				p.setProperty("rabbitmq.password", request.getParameter("password"));
				try{
					PropertiesUtil.writeProperties(p, path+"properties/rabbitmq.properties");
				}catch(IOException e){
					msg = "修改失败";
				}
			}
			List<String[]> list = new ArrayList<>(4);
			list.add(new String[]{"host", p.getProperty("rabbitmq.host")});
			list.add(new String[]{"port", p.getProperty("rabbitmq.port")});
			list.add(new String[]{"username", p.getProperty("rabbitmq.username")});
			list.add(new String[]{"password", p.getProperty("rabbitmq.password")});
			model.addAttribute("myurl", "/editProp/rabbitmq");
			model.addAttribute("list", list);
		}else if("config".equals(op)){
			Properties p = Constants.getConfigProperties();
			Constants.setConfigPropertiesNull();
			if(request.getParameter("server-url") == null){
				msg = "server-url不能为空";
			}else if(request.getParameter("client-id") == null){
				msg = "client-id不能为空";
			}else if(request.getParameter("adapter-port") == null){
				msg = "adapter-port不能为空";
			}else if(request.getParameter("output-format") == null){
				msg = "output-format不能为空";
			}else if(request.getParameter("rate") == null){
				msg = "rate不能为空";
			}else{
				p.setProperty("server-url", request.getParameter("server-url"));
				p.setProperty("client-id", request.getParameter("client-id"));
				p.setProperty("adapter-port", request.getParameter("adapter-port"));
				p.setProperty("output-format", request.getParameter("output-format"));
				p.setProperty("rate", request.getParameter("rate"));
				try{
					PropertiesUtil.writeProperties(p, path+"properties/config.properties");
				}catch(IOException e){
					msg = "修改失败";
				}
			}
			List<String[]> list = new ArrayList<>(5);
			list.add(new String[]{"server-url", p.getProperty("server-url")});
			list.add(new String[]{"client-id", p.getProperty("client-id")});
			list.add(new String[]{"adapter-port", p.getProperty("adapter-port")});
			list.add(new String[]{"output-format", p.getProperty("output-format")});
			list.add(new String[]{"rate", p.getProperty("rate")});

			model.addAttribute("myurl", "/editProp/config");
			model.addAttribute("list", list);
		}
		model.addAttribute("msg", msg);
		return "prop";
	}
}
