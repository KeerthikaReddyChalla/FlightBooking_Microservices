package com.flightapp.bookingservice.feign;

import com.flightapp.bookingservice.config.FeignConfig;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactivefeign.webclient.ReactiveWebClientBuilder;

@Component
public class FlightClient {

	 private final ReactiveWebClientBuilder builder;

	    public FlightClient(ReactiveWebClientBuilder builder) {
	        this.builder = builder;
	    }

	    public FlightServiceApi client(String url) {
	        return builder.target(FlightServiceApi.class, url);
	    }}
}

}
