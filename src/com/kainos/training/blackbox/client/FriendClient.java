package com.kainos.training.blackbox.client;

import java.util.List;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.kainos.training.blackbox_interface.model.Person;

public class FriendClient {

	private WebTarget target;

	public FriendClient() {
		target = ClientBuilder.newClient().target("http://localhost:8910")
				.path("person");
	}
	
	public Response addFriend(Person p) {
		String name=p.getName();
		List<Person> actualPeople=getFriendsList();
		Response response;
		for(int i=0;i<actualPeople.size();i++)
		{
			if(actualPeople.get(i).getName().equals(name)){
				System.out.println("Name already in use.");
				return Response.status(Status.CONFLICT).build();
			}
		}
		response = target.request().post(Entity.entity(p, MediaType.APPLICATION_JSON));
		return response;
	}
	
	public Response printListOfFriends()
	{
		List<Person> actualPeople=getFriendsList();
		for(int i=0;i<actualPeople.size();i++)
		{
			System.out.println(i+"\t"+actualPeople.get(i).getName());
		}
		return Response.ok().build();
	}

	public Response deleteFriend(Person p)
	{
		String name=p.getName();
		List<Person> actualPeople=getFriendsList();
		for(int i=0;i<actualPeople.size();i++)
		{
			if(actualPeople.get(i).getName().equals(name)){
				return target.path("" + i).request().delete();
			}
		}
		return Response.status(Status.NOT_FOUND).build();
	}
	
	public List<Person> getFriendsList() {	
		Response response = target.request().get();
		return response.readEntity(new GenericType<List<Person>>(){});	
	}
}
