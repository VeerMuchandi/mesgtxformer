package org.acme.getting.started;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/")
public class GreetingResource {

    @Inject
    GreetingService service;

    @ConfigProperty(name = "deployment")
    String deployment;
    @ConfigProperty(name = "message")
    String defaultMessage;
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("greeting/{name}")
    public String greeting(@PathParam final String name) {
        return service.greeting(name);
    }

    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("")
    public String hello(){
        return getMessage(defaultMessage);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{message}")
    public String getMessage(@PathParam String message) {  
        return transform(message);
    }

    @POST
    @Consumes({MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.WILDCARD})
    @Path("")
    public void postMessage(String message) {  
         transform(message);
    }

    private String transform(String message) {
        String outMessage=message;
        
		switch(deployment) 
        { 
            case "wordcount": 
                outMessage=this.wordCount(message); 
                break; 
            case "titlecase": 
                outMessage=this.titleCase(message);
                break; 
            case "capitalize": 
                outMessage=this.capitalize(message);
                break; 
            default: 
                break; 
        } 
        System.out.println(java.time.LocalTime.now() + "IN "+ message + " OUT "+outMessage);
        return outMessage;
    }


    public String wordCount(@PathParam final String sentence) {
        return "MESSAGE WORD COUNT: "+ service.countWords(sentence) + " SENTENCE: \""+sentence+"\"";
    }
    

    public String titleCase(@PathParam final String sentence) {
        return "MESSAGE TITLE CASED : \""+service.upperCaseAllFirst(sentence)+"\"";
    }

 
    public String capitalize(@PathParam final String sentence) {
        return "MESSAGE CAPITALIZED : \""+sentence.toUpperCase()+"\"";
    }
}