package com.sharehub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShareHubApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(ShareHubApplication.class, args);
	}
}