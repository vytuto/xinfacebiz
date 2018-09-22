package com.vytuto.mocmom.engine.controller;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloController {

	private Facebook facebook;
	private ConnectionRepository connectionRepository;

	public HelloController(Facebook facebook, ConnectionRepository connectionRepository) {
		this.facebook = facebook;
		this.connectionRepository = connectionRepository;
	}

	@GetMapping
	public String helloFacebook(Model model) {
		if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
			return "redirect:/connect/facebook";
		}
		String[] fields = { "id", "email", "first_name", "last_name" };
		User userProfile = facebook.fetchObject("me", User.class, fields);
		System.out.println(userProfile.getId());
//        facebook.friendOperations().getFr
		model.addAttribute("facebookProfile", userProfile);
//        PagedList<Post> feed = facebook.feedOperations().getFeed();
		PagedList<String> friendProfiles = facebook.friendOperations().getFriendIds();
		System.out.println(friendProfiles.size());
		model.addAttribute("feed", friendProfiles);

//        List<User> friends = facebook.friendOperations().getFriendProfiles();
//        
//        friends.stream().forEach(f->{
//        	System.out.println(f.getFirstName());
//        });

		return "hello";
	}

}