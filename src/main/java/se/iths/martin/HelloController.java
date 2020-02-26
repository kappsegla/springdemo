package se.iths.martin;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Qualifier("eurekaClient")
    @Autowired
    private EurekaClient eurekaClient;

    @GetMapping("/randomperson")
    public Greeting callPersons(){
        return restTemplate.getForObject("http://Persons-Service/hello",Greeting.class);
    }


    @GetMapping("/clients")
    public List<InstanceInfo> doRequest() {
        Application application
                = eurekaClient.getApplication("Persons-Service");
        return application.getInstances();

//        InstanceInfo instanceInfo = application.getInstances().get(0);
//        String hostname = instanceInfo.getHostName();
//        int port = instanceInfo.getPort();
//        // ...
    }


    @RequestMapping("/hello")
    public Greeting sayHello(){
        return new Greeting(1,"Hello från Martins service!");
    }

    // This method should only be accessed by users with role of 'admin'
    @GetMapping("/admin")
    public String homeAdmin() {
        return "This is the admin area";
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
