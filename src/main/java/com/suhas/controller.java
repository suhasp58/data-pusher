package com.suhas;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import redis.clients.jedis.Jedis;

@RestController
public class controller {

	@RequestMapping("redisdata")
	public String sayHello() throws Exception {
		Properties prop = new Properties();
		InputStream input = controller.class.getClassLoader().getResourceAsStream("application.properties");
		prop.load(input);
		System.out.println("Prop-->" + prop.getProperty("filename"));
		String data = new String(Files.readAllBytes(Paths.get(prop.getProperty("filename"))));
		Jedis jedis = new Jedis(prop.getProperty("ip"), 6379);
		try {

			JSONObject obj = new JSONObject(data);
			//JSONArray n = obj.getJSONArray("instance");
			System.out.println(obj);
			

				String key = "10.37.56.1";
				//JSONObject value = n.getJSONObject(i);
				//System.out.println("key:" + key);
				//System.out.println("value:" + value);
				System.out.println("Connection to server sucessfully established");
				jedis.set(key, obj.toString());

			
			System.out.println("Inserted Successfully");
		} finally {
			jedis.close();
		}
		return "INSERTED SUCCESSFULLY.";
	}
}
