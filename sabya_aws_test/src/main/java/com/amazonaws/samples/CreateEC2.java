package com.amazonaws.samples;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.Tag;


public class CreateEC2 {
	private static final AWSCredentials AWS_CREDENTIALS;
	static {
		AWS_CREDENTIALS = new BasicAWSCredentials("AKIAJ3JTL63H2PQHVI4A", "HDzocVOYUTXX20KpZCdmHYqpfpZ4eoH1Ws7LU5Bq");
	}
	public static void main(String[] args) {
		
		AmazonEC2 ec2Client = AmazonEC2ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(AWS_CREDENTIALS)).withRegion(Regions.US_EAST_1).build();
		RunInstancesRequest runInstancesRequest = new RunInstancesRequest().withImageId("ami-00ddb0e5626798373").withInstanceType("t2.micro")
				.withMinCount(1)
				.withMaxCount(1)
				.withKeyName("sabyaaws_keypairuseast1")
				.withSecurityGroupIds("sg-0f2e26cbba231aedc");
		RunInstancesResult instancesResult = ec2Client.runInstances(runInstancesRequest);
		Instance instance = instancesResult.getReservation().getInstances().get(0);
		String instaceIdString = instance.getInstanceId();
		CreateTagsRequest createTagsRequest = new CreateTagsRequest().withResources(instance.getInstanceId())
				.withTags(new Tag("Name","SabyaASU1"));
		ec2Client.createTags(createTagsRequest);
		StartInstancesRequest startInstancesRequest = new StartInstancesRequest().withInstanceIds(instaceIdString);
		ec2Client.startInstances(startInstancesRequest);
	}

}
