package de.clashsoft.demo.angulargradle;

import spark.Service;

public class App
{
	public static void main(String[] args)
	{
		final Service service = Service.ignite();
		service.port(1234);

		service.staticFileLocation("/");

		service.get("/hello", (req, res) -> "Hello from Spark Server");

		service.init();
	}
}
