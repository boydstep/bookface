package com.kainos.training.blackbox.client;

import com.kainos.training.blackboxinterface.model.person.Person;

public class Runner {

	public static void main(String[] args) {
		FriendClient fc = new FriendClient();
		Person p = new Person();
		p.setName("Marc");
		fc.addFriend(p);
	}
}
