package com.suhas;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.suhas.Server1Application;
import redis.clients.jedis.Jedis;

//@RestController
public class controller {
	public static String filename;
	//@RequestMapping("redisdata")
	public static String sayHello() throws Exception {
		Server1Application sa = new Server1Application();
        
		Properties prop = new Properties();
		InputStream input = controller.class.getClassLoader().getResourceAsStream("application.properties");
		prop.load(input);
		String data = new String(Files.readAllBytes(Paths.get(filename)));
		Jedis jedis = new Jedis("localhost", 6379);
		try {

			JSONObject obj = new JSONObject(data);
			JSONArray n = obj.getJSONArray("instance");
			//System.out.println(obj);
			

				String key = "10.37.56.1";
				//JSONObject value = n.getJSONObject(i);
				//System.out.println("key:" + key);
				//System.out.println("value:" + value);
				System.out.println("Connection to server sucessfully established "+jedis.ping());
				jedis.set(key, obj.toString());

			
			System.out.println("------------------------------------------------");
			
			System.out.println("Inserted Successfully");

		} finally {
			jedis.close();
		}
		return "INSERTED SUCCESSFULLY.";
	}
	public static void main(String[] args) throws IOException {
        int time=(int) Long.parseLong(args[0]);  
       filename=args[1];
		SpringApplication.run(Server1Application.class, args);
		ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
		ses.scheduleAtFixedRate(new Runnable() {
		    @Override
		    public void run() {
		       try {
		    	  sayHello();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    }
		}, 0, time, TimeUnit.MILLISECONDS);
        }
}
