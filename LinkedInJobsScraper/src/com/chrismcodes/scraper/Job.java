package com.chrismcodes.scraper;

import org.jsoup.nodes.Element;

public class Job {
	private Element title;
	private Element location;
	
	public Job(Element title, Element location) {
		this.title = title;
		this.location = location;
	}

	public Element getTitle() {
		return title;
	}

	public void setTitle(Element title) {
		this.title = title;
	}

	public Element getLocation() {
		return location;
	}

	public void setLocation(Element location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Job [title=" + title + ", location=" + location + ", getTitle()=" + getTitle() + ", getLocation()="
				+ getLocation() + "]";
	}
	
	
}
