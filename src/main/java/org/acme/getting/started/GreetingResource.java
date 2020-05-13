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

        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_BLACK = "\u001B[30m";
        public static final String ANSI_RED = "\u001B[31m";
        public static final String ANSI_GREEN = "\u001B[32m";
        public static final String ANSI_YELLOW = "\u001B[33m";
        public static final String ANSI_BLUE = "\u001B[34m";
        public static final String ANSI_PURPLE = "\u001B[35m";
        public static final String ANSI_CYAN = "\u001B[36m";
        public static final String ANSI_WHITE = "\u001B[37m";

    @Inject
    GreetingService service;

    @ConfigProperty(name = "format")
    String format;
    @ConfigProperty(name = "message")
    String defaultMessage;
    @ConfigProperty(name = "color")
    String color;
    
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
        
		switch(format) 
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
        System.out.println(java.time.LocalTime.now() + " IN "+ message + " OUT "+outMessage);
        return colorCode(outMessage);
    }

    //For BlueGreen Testing
    private String colorCode(String message) {
        switch (color)
        {
            case "blue":
                message=ANSI_BLUE+message+ANSI_RESET;
                break;
            case "green":
                message=ANSI_GREEN+message+ANSI_RESET;
                break;
            default:
                break;
        }
        return message;
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