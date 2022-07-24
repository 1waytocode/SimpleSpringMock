package SimpleSpringMock.Controllers;

import SimpleSpringMock.Features.ResponseTimeBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
public class ExampleController {
    @Autowired
    ResponseTimeBean responseTimeBean;
    Logger logger = LoggerFactory.getLogger(ExampleController.class);

    @GetMapping(value = "/exampleGET")
    @ResponseStatus(HttpStatus.OK)
    public Object exampleGet() throws InterruptedException {
        if (responseTimeBean.isDelaySwitch()) {
            Thread.sleep(responseTimeBean.getLongDelayMap().get("exampleGET"));
        } else {
            Thread.sleep(responseTimeBean.getDelayMap().get("exampleGET"));
        }
        return "GET SUCCESS"; // ответ в формате content-type: text/plain;charset=UTF-8
    }

    @PostMapping(value = "/examplePOSTJSON", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Object examplePostJSON(@RequestBody Map<String, String> request) throws InterruptedException {
        // заменяем {"STATUS":"ERROR"} на {"STATUS":"SUCCESS"}
        request.replace("STATUS", "SUCCESS");
        if (responseTimeBean.isDelaySwitch()) {
            Thread.sleep(responseTimeBean.getLongDelayMap().get("examplePOST"));
        } else {
            Thread.sleep(responseTimeBean.getDelayMap().get("examplePOST"));
        }
        return request; // ответ c корреляцией в формате content-type: application/json
    }

    @PostMapping(value = "/examplePOSTXML", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String examplePostXML(@RequestBody String request) throws InterruptedException {
        // заменяем <REQUEST>
        //    <STATUS>ERROR</STATUS>
        //</REQUEST>
        // на <RESPONSE>
        //    <STATUS>SUCCESS</STATUS>
        //</RESPONSE>
        String response = request.replaceFirst("ERROR", "SUCCESS").replaceAll("REQUEST", "RESPONSE");
        if (responseTimeBean.isDelaySwitch()) {
            Thread.sleep(responseTimeBean.getLongDelayMap().get("examplePOST"));
        } else {
            Thread.sleep(responseTimeBean.getDelayMap().get("examplePOST"));
        }
        return response; // ответ c корреляцией в формате content-type: application/xml;charset=UTF-8
    }
}
