package de.clashsoft.demo.angulargradle;

import spark.Service;

public class App
{
	public static void main(String[] args)
	{
		final Service service = Service.ignite();

		service.staticFileLocation("/");

		service.get("/hello", (req, res) -> "Hello from Spark Server");

		service.before((request, response) -> {
			response.header("Access-Control-Allow-Origin", "http://localhost:4200");
		});

		service.init();
	}
}
