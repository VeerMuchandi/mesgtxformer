package org.acme.getting.started;

import javax.inject.Inject;
import javax.ws.rs.GET;
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
    @Path("message")
    public String hello(){
        return hello(defaultMessage);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("message/{message}")
    public String hello(@PathParam String message) {
        

        switch(deployment) 
        { 
            case "wordcount": 
                message=this.wordCount(message); 
                break; 
            case "titlecase": 
                message=this.titleCase(message);
                break; 
            case "capitalize": 
                message=this.capitalize(message);
                break; 
            default: 
                break; 
        } 
        return message;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("wordcount/{sentence}")
    public String wordCount(@PathParam final String sentence) {
        return "MESSAGE WORD COUNT: "+ service.countWords(sentence) + " SENTENCE: \""+sentence+"\"";
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("titlecase/{sentence}")
    public String titleCase(@PathParam final String sentence) {
        return "MESSAGE TITLE CASED : \""+service.upperCaseAllFirst(sentence)+"\"";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("capitalize/{sentence}")
    public String capitalize(@PathParam final String sentence) {
        return "MESSAGE CAPITALIZED : \""+sentence.toUpperCase()+"\"";
    }
}