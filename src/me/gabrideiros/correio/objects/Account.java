package me.gabrideiros.correio.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Account {
	
	private UUID uuid;
	private String name;
	private List<Mail> mails;
	
	public Account(UUID uuid, String name) {
		this.uuid = uuid;
		this.name = name;
		this.mails = new ArrayList<>();
	}
	
	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Mail> getMails() {
		return mails;
	}

	public void setMails(List<Mail> mails) {
		this.mails = mails;
	}
	
	public void addMails(Mail mail) {
		this.mails.add(mail);
	}
}
