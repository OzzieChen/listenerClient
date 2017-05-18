package net.xssu.client.controller;

import net.xssu.client.common.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Controller
public class AdminController {

	@RequestMapping(value = "/editProp/{op}", method = RequestMethod.GET)
	public String opAutomataPages(HttpServletRequest request, Model model, @PathVariable("op") String op){
		System.out.println("ccccccccccc");
		if("redis".equals(op)){
			List<String[]> list = new ArrayList<>(2);
			list.add(new String[]{"hostname", Constants.getRedisProperties().getProperty("hostname")});
			list.add(new String[]{"port", Constants.getRedisProperties().getProperty("port")});

			model.addAttribute("myurl", "/editProp/redis");
			model.addAttribute("list", list);
			return "prop";
		}else if("rabbitmq".equals(op)){
			List<String[]> list = new ArrayList<>(2);
			list.add(new String[]{"host", Constants.getRedisProperties().getProperty("host")});
			list.add(new String[]{"port", Constants.getRedisProperties().getProperty("port")});
			list.add(new String[]{"username", Constants.getRedisProperties().getProperty("username")});
			list.add(new String[]{"password", Constants.getRedisProperties().getProperty("password")});

			model.addAttribute("myurl", "/editProp/rabbitmq");
			model.addAttribute("list", list);
			return "prop";
		}else if("config".equals(op)){
			List<String[]> list = new ArrayList<>(2);
			list.add(new String[]{"server-url", Constants.getRedisProperties().getProperty("server-url")});
			list.add(new String[]{"client-id", Constants.getRedisProperties().getProperty("client-id")});
			list.add(new String[]{"adapter-port", Constants.getRedisProperties().getProperty("adapter-port")});
			list.add(new String[]{"output-format", Constants.getRedisProperties().getProperty("output-format")});
			list.add(new String[]{"rate", Constants.getRedisProperties().getProperty("rate")});

			model.addAttribute("myurl", "/editProp/config");
			model.addAttribute("list", list);
			return "prop";
		}
		return null;
	}

	@RequestMapping(value = "/editProp/{op}", method = RequestMethod.POST)
	public String opAutomatas(HttpServletRequest request, Model model, @PathVariable("op") String op){
		String msg = null;
		if("rmv".equals(op)){

		}
		Properties prop = new Properties();
		try{
			FileOutputStream oFile = new FileOutputStream("b.properties", false);
			prop.setProperty("phone", "10086");
			prop.store(oFile, "The New properties file");
			oFile.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		model.addAttribute("msg", msg);
		return null;
	}
}
