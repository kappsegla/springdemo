package se.iths.martin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@RestController
class HelloController {

    @Autowired
    RestTemplate restTemplate;
    HttpClient client;

    @RequestMapping("/hello")
    public Greeting sayHello(){
        return new Greeting(1,"Hello");
    }


    @RequestMapping("/remotecall")
    public String remote() throws IOException, InterruptedException {
     //Java 11 HttpClient, works both as synchron and asynchron client
        client = HttpClient.newHttpClient();
              HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://ron-swanson-quotes.herokuapp.com/v2/quotes"))
                .build();

        var json = client.send(request, HttpResponse.BodyHandlers.ofString());
        return json.body();

        //Spring blocking RestTemplate
       // var greeting = restTemplate.getForEntity("https://ron-swanson-quotes.herokuapp.com/v2/quotes",String[].class);
       // return greeting.getBody()[0];
    }
}
