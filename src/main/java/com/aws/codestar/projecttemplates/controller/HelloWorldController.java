package com.aws.codestar.projecttemplates.controller;

import java.util.Iterator;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

/**
 * Basic Spring web service controller that handles all GET requests.
 */
@RestController
@RequestMapping("/s3")
public class HelloWorldController {

    private static final String MESSAGE_FORMAT = "Hello %s!";
    
    private static final String BUCKET_NAME = "aws-codestar-us-east-1-690946705415-xinfacebiz-pipe";

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", path="/list")
    public ResponseEntity<String> helloWorldGet() {
    	StringBuilder result = new StringBuilder();
    	final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
    	try {
    	    System.out.println(" - removing objects from bucket");
    	    ObjectListing object_listing = s3.listObjects(BUCKET_NAME);
    	    object_listing.getObjectSummaries().stream().forEach(o->{
	            S3ObjectSummary summary = (S3ObjectSummary)o;
    	    	result.append(summary.getETag());
    	    });
//    	    while (true) {
//    	        for (Iterator<?> iterator =
//    	                object_listing.getObjectSummaries().iterator();
//    	                iterator.hasNext();) {
//    	            S3ObjectSummary summary = (S3ObjectSummary)iterator.next();
//    	            s3.deleteObject(BUCKET_NAME, summary.getKey());
//    	        }
//
//    	        // more object_listing to retrieve?
//    	        if (object_listing.isTruncated()) {
//    	            object_listing = s3.listNextBatchOfObjects(object_listing);
//    	        } else {
//    	            break;
//    	        }
//    	    };
    	} catch (AmazonServiceException e) {
    	    System.err.println(e.getErrorMessage());
    	}
    	
        return ResponseEntity.ok(result.toString());
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> helloWorldPost(@RequestParam(value = "name", defaultValue = "World") String name) {
        return ResponseEntity.ok(createResponse(name));
    }

    private String createResponse(String name) {
        return new JSONObject().put("Output", String.format(MESSAGE_FORMAT, name)).toString();
    }
}